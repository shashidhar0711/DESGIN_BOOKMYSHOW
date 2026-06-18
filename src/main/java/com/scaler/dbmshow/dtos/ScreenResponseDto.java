package com.scaler.dbmshow.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.scaler.dbmshow.models.Screen;
import lombok.Data;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class ScreenResponseDto {
    private int id;
    private String name;

    private String errorMessage;
    private ResponseType responseType;

    public static ScreenResponseDto from(Screen screen) {

        ScreenResponseDto dto =
                new ScreenResponseDto();

        dto.setId(screen.getId());
        dto.setName(screen.getName());
        dto.setResponseType(ResponseType.SUCCESS);

        return dto;
    }

    public static ScreenResponseDto failure(
            String message) {

        ScreenResponseDto dto =
                new ScreenResponseDto();

        dto.setErrorMessage(message);
        dto.setResponseType(ResponseType.FAILURE);

        return dto;
    }
}
