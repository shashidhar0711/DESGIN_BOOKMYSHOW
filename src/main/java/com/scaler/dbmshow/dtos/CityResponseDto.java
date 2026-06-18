package com.scaler.dbmshow.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.scaler.dbmshow.models.City;
import lombok.Data;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class CityResponseDto {
    private int id;
    private String name;

    private String errorMessage;
    private ResponseType responseType;

    public static CityResponseDto from(City city) {

        CityResponseDto dto = new CityResponseDto();

        dto.setId(city.getId());
        dto.setName(city.getName());
        dto.setResponseType(ResponseType.SUCCESS);

        return dto;
    }

    public static CityResponseDto failure(String message) {

        CityResponseDto dto = new CityResponseDto();

        dto.setErrorMessage(message);
        dto.setResponseType(ResponseType.FAILURE);

        return dto;
    }
}