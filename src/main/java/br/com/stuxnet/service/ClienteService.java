package br.com.stuxnet.service;

import br.com.stuxnet.dto.ClienteRequestDTO;
import br.com.stuxnet.dto.ClienteResponseDTO;
import br.com.stuxnet.model.Cliente;
import br.com.stuxnet.repository.ClienteRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.SecureRandom;
import java.util.Base64;

@Service
public class ClienteService {

    private final ClienteRepository clienteRepository;

    public ClienteService(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    @Transactional
    public ClienteResponseDTO cadastrar(ClienteRequestDTO dto) {
        String cpfLimpo = dto.cpf().replaceAll("\\D", "");
        String emailLimpo = dto.email().trim().toLowerCase();

        if (clienteRepository.existsByEmailOrCpf(emailLimpo, cpfLimpo)) {
            throw new IllegalArgumentException("Já existe um cliente cadastrado com este e-mail ou CPF.");
        }

        String senhaHash = gerarHashSenhaSeguro(dto.senha());

        Cliente novoCliente = new Cliente(
                null,
                dto.nome().trim(),
                emailLimpo,
                cpfLimpo,
                senhaHash
        );

        return ClienteResponseDTO.fromEntity(clienteRepository.save(novoCliente));
    }

    // Hashing de senha padrão OWASP com PBKDF2 + Salt de 16 bytes
    private String gerarHashSenhaSeguro(String senhaPura) {
        try {
            byte[] salt = new byte[16];
            new SecureRandom().nextBytes(salt);

            PBEKeySpec spec = new PBEKeySpec(senhaPura.toCharArray(), salt, 65536, 256);
            SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
            byte[] hash = factory.generateSecret(spec).getEncoded();

            return Base64.getEncoder().encodeToString(salt) + ":" + Base64.getEncoder().encodeToString(hash);
        } catch (Exception e) {
            throw new IllegalStateException("Erro ao criptografar credenciais do cliente.", e);
        }
    }
}