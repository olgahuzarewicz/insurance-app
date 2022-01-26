package com.insurance.app.service;

import com.insurance.app.exception.CaseNotFoundException;
import com.insurance.app.model.Case;
import com.insurance.app.model.CaseResult;
import com.insurance.app.repository.CaseRepository;
import org.joda.time.Months;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Optional;

import org.joda.time.LocalDate;

public class InsuranceCalculationService {

    private CaseRepository caseRepository;

    public static final BigDecimal ONE_HUNDRED = new BigDecimal(100);

    public InsuranceCalculationService(CaseRepository caseRepository) {
        this.caseRepository = caseRepository;
    }

    public CaseResult calculateAmountToPay(int caseId) throws CaseNotFoundException {
        Case insCase = caseRepository.getCaseById(caseId).orElseThrow(CaseNotFoundException::new);

        LocalDate insuranceStartDate = insCase.insurance().startDate();
        LocalDate damageDate = insCase.damage().date();

        int monthDiff = Months.monthsBetween(insuranceStartDate, damageDate).getMonths();
        if (monthDiff >= 12) {
            return new CaseResult(insCase.car().plateNumber(), BigDecimal.ZERO);
        }

        BigDecimal initialCarValue = insCase.insurance().initialCarValue();
        BigDecimal currentCarValue = calculateCurrentCarValue(initialCarValue, monthDiff);
        BigDecimal damageValue = insCase.damage().value();

        BigDecimal totalLossValue = percentage(currentCarValue, BigDecimal.valueOf(70)).setScale(0, RoundingMode.DOWN);

        boolean isDamageBiggerThanTotalLossValue = damageValue.compareTo(totalLossValue) > 0;

        if (isDamageBiggerThanTotalLossValue) {
            return new CaseResult(insCase.car().plateNumber(), totalLossValue);
        }

        return new CaseResult(insCase.car().plateNumber(), damageValue);
    }

    private static BigDecimal calculateCurrentCarValue(BigDecimal carValue, int counter) {
        while (counter > 0) {
            carValue = percentage(carValue, BigDecimal.valueOf(99));
            counter--;
        }
        return carValue.setScale(0, RoundingMode.DOWN);
    }

    private static BigDecimal percentage(BigDecimal base, BigDecimal pct) {
        return base.multiply(pct).divide(ONE_HUNDRED);
    }
}
