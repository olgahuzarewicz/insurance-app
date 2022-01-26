package com.insurance.app.repository;

import com.insurance.app.model.Car;
import com.insurance.app.model.Case;
import com.insurance.app.model.Damage;
import com.insurance.app.model.Insurance;
import org.joda.time.LocalDate;

import java.math.BigDecimal;
import java.util.Map;
import java.util.Optional;

public class CaseRepository {

    private static Map<Integer, Case> cases = Map.of(1, new Case(1, new Car("Audi A6", "ABC 1234"), new Insurance(new LocalDate(2021, 03, 01), BigDecimal.valueOf(40000)), new Damage(new LocalDate(2021, 06, 01), BigDecimal.valueOf(4500))),
            2, new Case(2, new Car("VW Passat", "DEF 567"), new Insurance(new LocalDate(2021, 01, 01), BigDecimal.valueOf(12000)), new Damage(new LocalDate(2021, 9, 01), BigDecimal.valueOf(7730))),
            3, new Case(3, new Car("Skoda Fabia", "GHI 8910"), new Insurance(new LocalDate(2020, 01, 01), BigDecimal.valueOf(25000)), new Damage(new LocalDate(2021, 5, 01), BigDecimal.valueOf(7000))));

    public Optional<Case> getCaseById(int id) {
        return Optional.ofNullable(cases.get(id));
    }
}
