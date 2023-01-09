package com.javacore10.featur.currency;

import com.javacore10.featur.currency.dto.Currency;

import java.io.IOException;

public interface CurrencyService {
    double[] getRate(Currency currency) throws IOException;
    }
