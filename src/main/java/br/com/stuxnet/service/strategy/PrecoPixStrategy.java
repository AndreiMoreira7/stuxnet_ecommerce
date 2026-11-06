package br.com.stuxnet.service.strategy;

import java.math.BigDecimal;
import java.math.RoundingMode;

import org.springframework.stereotype.Component;

@Component("PIX")
public class PrecoPixStrategy implements CalculadoraPrecoStrategy {

    // 15% de desconto à vista no PIX (padrão de lojas de hardware)
    private static final BigDecimal FATOR_DESCONTO_PIX = new BigDecimal("0.85");

    @Override
    public BigDecimal calcularPrecoFinal(BigDecimal precoBase) {
        return precoBase.multiply(FATOR_DESCONTO_PIX).setScale(2, RoundingMode.HALF_EVEN);
    }
}