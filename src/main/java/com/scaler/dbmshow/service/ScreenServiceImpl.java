package com.scaler.dbmshow.service;

import com.scaler.dbmshow.dtos.ScreenRequestDto;
import com.scaler.dbmshow.exceptions.ResourceNotFoundException;
import com.scaler.dbmshow.models.Screen;
import com.scaler.dbmshow.models.Theatre;
import com.scaler.dbmshow.repositories.ScreenRepository;
import com.scaler.dbmshow.repositories.TheatreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ScreenServiceImpl implements ScreenService{

    @Autowired
    private ScreenRepository screenRepository;
    private TheatreRepository theatreRepository;

    public ScreenServiceImpl(ScreenRepository screenRepository,
                             TheatreRepository theatreRepository) {
        this.screenRepository = screenRepository;
        this.theatreRepository = theatreRepository;
    }

    @Override
    public Screen create(ScreenRequestDto request) throws ResourceNotFoundException {
        Theatre theatre = theatreRepository.findById(request.getTheatreId())
                .orElseThrow(() -> new ResourceNotFoundException("Theatre not found"));
        Screen screen = new Screen();
        screen.setName(request.getName());
        screen.setTheatre(theatre);
        return screenRepository.save(screen);
    }

    @Override
    public Screen getById(int id) throws ResourceNotFoundException {
        return screenRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Screen not found"));
    }

    @Override
    public List<Screen> getAll() {
        return screenRepository.findAll();
    }

    @Override
    public Screen update(int id, ScreenRequestDto request) throws ResourceNotFoundException {
        Screen screen = getById(id);
        screen.setName(request.getName());

        Theatre theatre = theatreRepository.findById(request.getTheatreId())
                .orElseThrow(() -> new RuntimeException("Theatre not found"));

        screen.setTheatre(theatre);

        return screenRepository.save(screen);
    }

    @Override
    public void delete(int id) throws ResourceNotFoundException {
        Screen screen = getById(id);
        screenRepository.delete(screen);
    }
}
