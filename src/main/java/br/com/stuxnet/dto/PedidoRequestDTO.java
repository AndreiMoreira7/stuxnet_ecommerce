package br.com.stuxnet.dto;

import java.util.List;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record PedidoRequestDTO(
        @NotNull(message = "O ID do cliente é obrigatório.")
        Long clienteId,

        @NotBlank(message = "A modalidade de pagamento é obrigatória.")
        @Pattern(regexp = "^(PIX|CARTAO)$", message = "A modalidade deve ser PIX ou CARTAO.")
        String modalidadePagamento,

        @NotEmpty(message = "O pedido deve conter pelo menos 1 item.")
        @Valid
        List<ItemPedidoRequestDTO> itens
) {}