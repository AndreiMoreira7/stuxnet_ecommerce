package br.com.stuxnet.service.strategy;

import java.math.BigDecimal;

public interface CalculadoraPrecoStrategy {
    BigDecimal calcularPrecoFinal(BigDecimal precoBase);
}