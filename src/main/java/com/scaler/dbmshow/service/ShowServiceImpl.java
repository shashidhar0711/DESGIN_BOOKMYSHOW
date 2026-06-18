package com.scaler.dbmshow.service;

import com.scaler.dbmshow.dtos.ShowRequestDto;
import com.scaler.dbmshow.models.*;
import com.scaler.dbmshow.repositories.*;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class ShowServiceImpl implements ShowService {

    private UserRepository userRepository;
    private MovieRepository movieRepository;
    private ScreenRepository screenRepository;
    private ShowRepository showRepository;
    private SeatRepository seatRepository;
    private ShowSeatRepository showSeatRepository;
    private ShowSeatTypeRepository showSeatTypeRepository;

    public ShowServiceImpl(UserRepository userRepository,
                           MovieRepository movieRepository,
                           ScreenRepository screenRepository,
                           ShowRepository showRepository,
                           SeatRepository seatRepository,
                           ShowSeatRepository showSeatRepository,
                           ShowSeatTypeRepository showSeatTypeRepository) {
        this.userRepository = userRepository;
        this.movieRepository = movieRepository;
        this.screenRepository = screenRepository;
        this.showRepository = showRepository;
        this.seatRepository = seatRepository;
        this.showSeatRepository = showSeatRepository;
        this.showSeatTypeRepository = showSeatTypeRepository;
    }

    @Override
    public Show createShow(ShowRequestDto showRequestDto) {
        // 1. Verify that the requested User exists and is authorized
        // 2. Confirm the Movie exists in the catalog
        // 3. Confirm the Physical Screen/Auditorium exists in the theatre
        // 4. Validate that the proposed show timing details do not conflict with existing schedules
        // 5. Initialize and persist the primary Show entry in the database
        // 6. Fetch the base seating layout mapped to the specified Screen ID
        // 7. Generate and link dynamic ShowSeat instances to this specific showtime
        // 8. Map custom tier pricing (e.g., VIP, Premium, Normal) as provided by the administrator
        // 9. Commit and save the finalized ticket pricing rules for this show
        User user = this.userRepository.findById(showRequestDto.getUserId())
                .orElseThrow(() -> new RuntimeException("User does not exist!"));

        Movie movie = this.movieRepository.findById(showRequestDto.getMovieId())
                .orElseThrow(() -> new RuntimeException("Movie not found"));

        Screen screen = this.screenRepository.findById(showRequestDto.getScreenId())
                .orElseThrow(() -> new RuntimeException("Screen does not exist!"));

        Date now = new Date();
        if(showRequestDto.getStartTime().before(now)) {
            throw new RuntimeException("Start time cannot be before current time");
        }
        if(showRequestDto.getEndTime().before(showRequestDto.getStartTime())) {
            throw new RuntimeException("End time cannot be before the start time");
        }

        Show show = new Show();
        show.setMovie(movie);
        show.setScreen(screen);
        show.setStartTime(showRequestDto.getStartTime());
        show.setEndTime(showRequestDto.getEndTime());
        show  = this.showRepository.save(show);

        List<Seat> allByScreenId = this.seatRepository.findAllByScreenId(showRequestDto.getScreenId());
        List<ShowSeat> showSeats = new ArrayList<>();
        for(Seat seat: allByScreenId) {
            ShowSeat showSeat = new ShowSeat();
            showSeat.setShow(show);
            showSeat.setSeat(seat);
            showSeat.setUser(user);
            showSeat.setSeatStatus(SeatStatus.AVAILABLE);
            showSeats.add(showSeat);
        }
        this.showSeatRepository.saveAll(showSeats);

        for(Pair<SeatType, Double> pair: showRequestDto.getPriceConfig()) {
            SeatType first = pair.getFirst();
            Double second = pair.getSecond();
            ShowSeatType showSeatType = new ShowSeatType();
            showSeatType.setShow(show);
            showSeatType.setAmount(second);
            showSeatType.setSeatType(first);

            this.showSeatTypeRepository.save(showSeatType);
        }
        return show;
    }
}
