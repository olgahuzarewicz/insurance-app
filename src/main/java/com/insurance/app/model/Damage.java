package com.insurance.app.model;

import org.joda.time.LocalDate;
import java.math.BigDecimal;

public record Damage (LocalDate date, BigDecimal value){}
