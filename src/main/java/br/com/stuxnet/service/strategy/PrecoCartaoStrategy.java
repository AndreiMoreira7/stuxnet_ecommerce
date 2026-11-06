package br.com.stuxnet.service.strategy;

import java.math.BigDecimal;
import java.math.RoundingMode;

import org.springframework.stereotype.Component;

@Component("CARTAO")
public class PrecoCartaoStrategy implements CalculadoraPrecoStrategy {

    @Override
    public BigDecimal calcularPrecoFinal(BigDecimal precoBase) {
        return precoBase.setScale(2, RoundingMode.HALF_EVEN);
    }
}