package com.hlebon.produce8.controller.dto;

import lombok.Getter;

@Getter
public class ErrorDto {
    private final String error;

    private ErrorDto(String error) {
        this.error = error;
    }

    public static ErrorDto of(String error) {
        return new ErrorDto(error);
    }
}
