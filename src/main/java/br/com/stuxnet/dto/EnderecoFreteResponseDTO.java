package br.com.stuxnet.dto;

import java.math.BigDecimal;

public record EnderecoFreteResponseDTO(
        EnderecoDTO endereco,
        BigDecimal valorFrete,
        Integer prazoDiasUteis
) {}