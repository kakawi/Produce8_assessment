package com.hlebon.produce8.controller;

import com.hlebon.produce8.controller.dto.ErrorDto;
import com.hlebon.produce8.controller.dto.MortgageRequestDto;
import com.hlebon.produce8.controller.dto.MortgageResponseDto;
import com.hlebon.produce8.mapper.MortgageMapper;
import com.hlebon.produce8.service.MortgageService;
import com.hlebon.produce8.service.response.MortgageResponse;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class MortgageController {

    private final MortgageRequestValidator validator;
    private final ExceptionMapper exceptionMapper;
    private final MortgageMapper mapper;
    private final MortgageService mortgageService;

    public MortgageController(
            MortgageRequestValidator validator,
            ExceptionMapper exceptionMapper,
            MortgageMapper mapper,
            MortgageService mortgageService
    ) {
        this.validator = validator;
        this.exceptionMapper = exceptionMapper;
        this.mapper = mapper;
        this.mortgageService = mortgageService;
    }

    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            content = {
                                    @Content(
                                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                                            schema = @Schema(implementation = MortgageResponseDto.class))
                            }),
                    @ApiResponse(
                            responseCode = "400",
                            content = {
                                    @Content(
                                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                                            schema = @Schema(implementation = ErrorDto.class))
                            })
            })
    @PostMapping("/calculate")
    public ResponseEntity<?> calculate(
            @RequestBody MortgageRequestDto mortgageRequest
    ) {
        try {
            validator.validate(mortgageRequest);
            MortgageResponse response = mortgageService.calculateMortgagePayment(mapper.fromDto(mortgageRequest));
            return ResponseEntity.ok().body(mapper.toDto(response));
        } catch (ValidationException e) {
            return ResponseEntity.badRequest()
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(exceptionMapper.map(e));
        } catch (Exception e) {
            log.error("Internal Exception during mortgage calculation", e);
            return ResponseEntity.internalServerError()
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(exceptionMapper.map(e));
        }
    }
}
