package com.scaler.dbmshow.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.scaler.dbmshow.models.City;
import com.scaler.dbmshow.models.Theatre;
import lombok.Data;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class TheatreResponseDto {
    private int id;
    private String name;
    private String address;
    private String cityName;
    private String errorMessage;
    private ResponseType responseType;

    public static TheatreResponseDto from(Theatre theatre) {
        TheatreResponseDto dto = new TheatreResponseDto();
        dto.setId(theatre.getId());
        dto.setName(theatre.getName());
        dto.setAddress(theatre.getAddress());
        dto.setCityName(theatre.getCity().getName());
        dto.setResponseType(ResponseType.SUCCESS);

        return dto;
    }

    public static TheatreResponseDto failure(String errorMessage) {
        TheatreResponseDto dto = new TheatreResponseDto();

        dto.setResponseType(ResponseType.FAILURE);
        dto.setErrorMessage(errorMessage);

        return dto;
    }
}
