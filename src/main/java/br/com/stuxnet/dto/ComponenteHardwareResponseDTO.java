package br.com.stuxnet.dto;

import java.math.BigDecimal;

import br.com.stuxnet.model.ComponenteHardware;
import br.com.stuxnet.model.enums.CategoriaHardware;

public record ComponenteHardwareResponseDTO(
        Long id,
        String sku,
        String nome,
        String fabricante,
        CategoriaHardware categoria,
        BigDecimal precoBase,
        BigDecimal precoPixComDesconto,
        Integer quantidadeEstoque,
        String especificacoesTecnicas
) {
    public static ComponenteHardwareResponseDTO fromEntity(ComponenteHardware entity, BigDecimal precoPix) {
        return new ComponenteHardwareResponseDTO(
                entity.getId(),
                entity.getSku(),
                entity.getNome(),
                entity.getFabricante(),
                entity.getCategoria(),
                entity.getPrecoBase(),
                precoPix,
                entity.getQuantidadeEstoque(),
                entity.getEspecificacoesTecnicas()
        );
    }
}