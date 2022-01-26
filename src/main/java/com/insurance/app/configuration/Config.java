package com.insurance.app.configuration;

import com.insurance.app.repository.CaseRepository;
import com.insurance.app.service.InsuranceCalculationService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Config {

    @Bean
    public CaseRepository caseRepository(){
        return new CaseRepository();
    }

    @Bean
    public InsuranceCalculationService insuranceCalculationService(CaseRepository caseRepository) {
        return new InsuranceCalculationService(caseRepository);
    }
}
