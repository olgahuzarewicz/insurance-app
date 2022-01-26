package com.insurance.app.controller;

import com.insurance.app.exception.CaseNotFoundException;
import com.insurance.app.model.CaseResult;
import com.insurance.app.service.InsuranceCalculationService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/case-result")
public class InsuranceController {

    InsuranceCalculationService insuranceCalculationService;

    public InsuranceController(InsuranceCalculationService insuranceCalculationService) {
        this.insuranceCalculationService = insuranceCalculationService;
    }

    @GetMapping("/{id}")
    public CaseResult getCaseResult(@PathVariable int id) throws CaseNotFoundException {
            return insuranceCalculationService.calculateAmountToPay(id);
    }
}