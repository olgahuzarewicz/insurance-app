package com.insurance.app.service;

import com.insurance.app.exception.CaseNotFoundException;
import com.insurance.app.model.*;
import com.insurance.app.repository.CaseRepository;
import org.joda.time.LocalDate;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyInt;

@ExtendWith(MockitoExtension.class)
public class InsuranceCalculationServiceTest {

    @Mock
    CaseRepository caseRepository;

    @InjectMocks
    InsuranceCalculationService service;

    @ParameterizedTest
    @MethodSource("provideCases")
    void testCalculateAmountToPay(Case input, CaseResult expected) throws CaseNotFoundException {
        Mockito.when(caseRepository.getCaseById(anyInt())).thenReturn(Optional.ofNullable(input));
        CaseResult insCase = this.service.calculateAmountToPay(new Random().nextInt());

        assertEquals(expected, insCase);
    }

    @ParameterizedTest
    @MethodSource("provideInvalidCases")
    void caseNotExistsShouldThrowException(Case input, Class expected) {
        Mockito.when(caseRepository.getCaseById(anyInt())).thenReturn(Optional.ofNullable(input));

        assertThrows(expected, () -> {
            this.service.calculateAmountToPay(new Random().nextInt());
        });
    }

    private static Stream<Arguments> provideCases() {
        return Stream.of(
                Arguments.of(new Case(1, new Car("Audi A6", "ABC 1234"), new Insurance(new LocalDate(2021, 02, 01), BigDecimal.valueOf(30000)), new Damage(new LocalDate(2021, 03, 01), BigDecimal.valueOf(30000))),
                        new CaseResult("ABC 1234", BigDecimal.valueOf(20790))),
                Arguments.of(new Case(2, new Car("VW Passat", "DEF 567"), new Insurance(new LocalDate(2021, 01, 01), BigDecimal.valueOf(12000)), new Damage(new LocalDate(2021, 02, 01), BigDecimal.valueOf(1000))),
                        new CaseResult("DEF 567", BigDecimal.valueOf(1000))),
                Arguments.of(new Case(3, new Car("Skoda Fabia", "GHI 8910"), new Insurance(new LocalDate(2021, 01, 01), BigDecimal.valueOf(7000)), new Damage(new LocalDate(2021, 01, 01), BigDecimal.valueOf(4900))),
                        new CaseResult("GHI 8910", BigDecimal.valueOf(4900))),
                Arguments.of(new Case(4, new Car("Audi A6", "JKL 1112"), new Insurance(new LocalDate(2019, 03, 01), BigDecimal.valueOf(40000)), new Damage(new LocalDate(2021, 06, 01), BigDecimal.valueOf(10000))),
                        new CaseResult("JKL 1112", BigDecimal.valueOf(0)))
        );
    }

    private static Stream<Arguments> provideInvalidCases() {
        return Stream.of(
                Arguments.of(null, CaseNotFoundException.class)
        );
    }
}