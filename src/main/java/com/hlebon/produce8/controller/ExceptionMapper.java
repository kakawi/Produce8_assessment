package com.hlebon.produce8.controller;

import com.hlebon.produce8.controller.dto.ErrorDto;
import org.springframework.stereotype.Component;

@Component
public class ExceptionMapper {
    public ErrorDto map(Exception e) {
        return ErrorDto.of(e.getMessage());
    }
}
