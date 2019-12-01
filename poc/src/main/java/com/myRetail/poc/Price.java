package com.myRetail.poc;

import java.io.Serializable;
import java.math.BigDecimal;

public class Price implements Serializable {
    private BigDecimal value;
    private String currencyCode;

    public Price() {

    }

    public Price(BigDecimal value, String currencyCode) {
        this.value = value;
        this.currencyCode = currencyCode;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }



}
