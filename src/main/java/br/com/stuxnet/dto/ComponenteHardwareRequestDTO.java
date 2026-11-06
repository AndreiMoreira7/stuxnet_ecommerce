package br.com.stuxnet.dto;

import java.math.BigDecimal;

import br.com.stuxnet.model.enums.CategoriaHardware;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record ComponenteHardwareRequestDTO(

        @NotBlank(message = "O SKU é obrigatório.")
        @Pattern(regexp = "^[A-Z0-9\\-]{4,40}$", message = "O SKU deve conter apenas letras maiúsculas, números e hífens (4 a 40 caracteres).")
        String sku,

        @NotBlank(message = "O nome do componente é obrigatório.")
        @Size(max = 150, message = "O nome deve ter no máximo 150 caracteres.")
        String nome,

        @NotBlank(message = "O fabricante é obrigatório.")
        @Size(max = 80, message = "O fabricante deve ter no máximo 80 caracteres.")
        String fabricante,

        @NotNull(message = "A categoria de hardware é obrigatória.")
        CategoriaHardware categoria,

        @NotNull(message = "O preço base é obrigatório.")
        @DecimalMin(value = "0.01", message = "O preço base deve ser maior que zero.")
        @Digits(integer = 10, fraction = 2, message = "Formato monetário inválido.")
        BigDecimal precoBase,

        @NotNull(message = "A quantidade em estoque é obrigatória.")
        @Min(value = 0, message = "O estoque não pode ser negativo.")
        Integer quantidadeEstoque,

        @Size(max = 1000, message = "As especificações técnicas não podem exceder 1000 caracteres.")
        String especificacoesTecnicas
) {}