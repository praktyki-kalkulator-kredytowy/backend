package com.praktyki.backend.business.value;

import java.math.BigDecimal;
import java.time.LocalDate;

public interface Payment {

    LocalDate getDate();

    BigDecimal getAmount();

}
