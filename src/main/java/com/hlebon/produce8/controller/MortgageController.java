package com.hlebon.produce8.controller;

import com.hlebon.produce8.controller.dto.MortgageRequestDto;
import com.hlebon.produce8.mapper.MortgageMapper;
import com.hlebon.produce8.service.MortgageService;
import com.hlebon.produce8.service.response.MortgageResponse;
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

    @PostMapping("/calculate")
    public ResponseEntity<?> calculate(
            @RequestBody MortgageRequestDto mortgageRequest
    ) {
        try {
            validator.validate(mortgageRequest);
            MortgageResponse response = mortgageService.calculateMortgagePayment(mapper.fromDto(mortgageRequest));
            return ResponseEntity.ok().body(mapper.fromEntity(response));
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
