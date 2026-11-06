package br.com.stuxnet.controller;

import br.com.stuxnet.dto.EnderecoDTO;
import br.com.stuxnet.dto.EnderecoFreteResponseDTO;
import br.com.stuxnet.service.ViaCepService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api/v1/enderecos")
public class EnderecoController {

    private final ViaCepService viaCepService;

    public EnderecoController(ViaCepService viaCepService) {
        this.viaCepService = viaCepService;
    }

    @GetMapping("/{cep}/frete")
    public ResponseEntity<EnderecoFreteResponseDTO> consultarCepEFrete(@PathVariable String cep) {
        EnderecoDTO endereco = viaCepService.buscarEnderecoPorCep(cep);

        // Regra de cálculo de frete de hardware por estado (UF)
        BigDecimal frete = calcularFretePorUf(endereco.uf());
        int prazo = calcularPrazoPorUf(endereco.uf());

        return ResponseEntity.ok(new EnderecoFreteResponseDTO(endereco, frete, prazo));
    }

    private BigDecimal calcularFretePorUf(String uf) {
        if (uf == null) return new BigDecimal("45.00");
        return switch (uf.toUpperCase()) {
            case "BA", "SE", "PE", "AL", "PB", "RN", "CE", "PI", "MA" -> new BigDecimal("22.90");
            case "SP", "RJ", "MG", "ES" -> new BigDecimal("34.90");
            default -> new BigDecimal("49.90");
        };
    }

    private int calcularPrazoPorUf(String uf) {
        if (uf == null) return 10;
        return switch (uf.toUpperCase()) {
            case "BA" -> 2;
            case "SE", "PE", "AL" -> 4;
            case "SP", "RJ", "MG", "ES" -> 6;
            default -> 9;
        };
    }
}