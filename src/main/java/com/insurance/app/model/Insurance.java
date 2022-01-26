package com.insurance.app.model;

import org.joda.time.LocalDate;
import java.math.BigDecimal;

public record Insurance (LocalDate startDate, BigDecimal initialCarValue){}
