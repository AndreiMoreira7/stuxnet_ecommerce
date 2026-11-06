package br.com.stuxnet.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ItemPedidoRequestDTO(
        @NotBlank(message = "O SKU do componente é obrigatório.")
        String sku,

        @NotNull(message = "A quantidade é obrigatória.")
        @Min(value = 1, message = "A quantidade mínima de compra é 1 unidade.")
        Integer quantidade
) {}