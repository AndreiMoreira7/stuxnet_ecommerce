package br.com.stuxnet.dto;

import java.time.LocalDateTime;

import br.com.stuxnet.model.Cliente;

public record ClienteResponseDTO(
        Long id,
        String nome,
        String email,
        String cpf,
        LocalDateTime dataCadastro
) {
    public static ClienteResponseDTO fromEntity(Cliente cliente) {
        return new ClienteResponseDTO(
                cliente.getId(),
                cliente.getNome(),
                cliente.getEmail(),
                cliente.getCpf(),
                cliente.getDataCadastro()
        );
    }
}