package com.hlebon.produce8.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MortgageController {

    @GetMapping("/calculate")
    public @ResponseBody String calculate() {
        return "Das Work";
    }
}
