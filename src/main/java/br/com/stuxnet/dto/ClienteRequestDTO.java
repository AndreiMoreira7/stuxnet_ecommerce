package br.com.stuxnet.dto;

public class ClienteRequestDTO {
    package br.com.stuxnet.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record ClienteRequestDTO(
        @NotBlank(message = "O nome é obrigatório.")
        @Size(min = 3, max = 120, message = "O nome deve ter entre 3 e 120 caracteres.")
        String nome,

        @NotBlank(message = "O e-mail é obrigatório.")
        @Email(message = "Formato de e-mail inválido.")
        String email,

        @NotBlank(message = "O CPF é obrigatório.")
        @Pattern(regexp = "^\\d{3}\\.?\\d{3}\\.?\\d{3}-?\\d{2}$", message = "CPF inválido. Use o formato 000.000.000-00 ou apenas 11 dígitos.")
        String cpf,

        @NotBlank(message = "A senha é obrigatória.")
        @Size(min = 8, max = 64, message = "A senha deve ter no mínimo 8 caracteres.")
        String senha
) {}
}
