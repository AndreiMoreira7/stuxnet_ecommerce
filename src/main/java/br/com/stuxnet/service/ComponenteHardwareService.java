package br.com.stuxnet.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.stuxnet.dto.ComponenteHardwareRequestDTO;
import br.com.stuxnet.dto.ComponenteHardwareResponseDTO;
import br.com.stuxnet.model.ComponenteHardware;
import br.com.stuxnet.repository.ComponenteHardwareRepository;
import br.com.stuxnet.service.strategy.CalculadoraPrecoStrategy;

@Service
public class ComponenteHardwareService {

    private final ComponenteHardwareRepository repository;
    private final Map<String, CalculadoraPrecoStrategy> estrategiasPreco;

    public ComponenteHardwareService(ComponenteHardwareRepository repository,
                                     Map<String, CalculadoraPrecoStrategy> estrategiasPreco) {
        this.repository = repository;
        this.estrategiasPreco = estrategiasPreco;
    }

    @Transactional
    public ComponenteHardwareResponseDTO cadastrar(ComponenteHardwareRequestDTO dto) {
        if (repository.existsBySku(dto.sku())) {
            throw new IllegalArgumentException("Já existe um componente cadastrado com o SKU: " + dto.sku());
        }

        // Uso do Design Pattern #1 (Builder) para instanciar a entidade com segurança
        ComponenteHardware novoComponente = new ComponenteHardware.Builder()
                .sku(dto.sku())
                .nome(dto.nome())
                .fabricante(dto.fabricante())
                .categoria(dto.categoria())
                .precoBase(dto.precoBase())
                .quantidadeEstoque(dto.quantidadeEstoque())
                .especificacoesTecnicas(dto.especificacoesTecnicas())
                .ativo(true)
                .build();

        ComponenteHardware salvo = repository.save(novoComponente);

        // Uso do Design Pattern #2 (Strategy) para calcular o valor no PIX
        BigDecimal precoPix = calcularPrecoPorModalidade(salvo.getPrecoBase(), "PIX");

        return ComponenteHardwareResponseDTO.fromEntity(salvo, precoPix);
    }

   @Transactional(readOnly = true)
    public List<ComponenteHardwareResponseDTO> listarTodos() {
        return repository.findByAtivoTrue().stream()
                .map(comp -> ComponenteHardwareResponseDTO.fromEntity(
                        comp,
                        calcularPrecoPorModalidade(comp.getPrecoBase(), "PIX")
                ))
                .toList();
    }

    @Transactional(readOnly = true)
    public List<ComponenteHardwareResponseDTO> listarPorCategoria( br.com.stuxnet.model.enums.CategoriaHardware categoria) {
        return repository.findByCategoriaAndAtivoTrue(categoria).stream()
                .map(comp -> ComponenteHardwareResponseDTO.fromEntity(
                        comp,
                        calcularPrecoPorModalidade(comp.getPrecoBase(), "PIX")
                ))
                .toList();
    }

    @Transactional
    public void desativarPorSku(String sku) {
        ComponenteHardware componente = repository.findBySku(sku.trim().toUpperCase())
                .orElseThrow(() -> new jakarta.persistence.EntityNotFoundException("Componente não encontrado com SKU: " + sku));
        componente.setAtivo(false);
        repository.save(componente);
    }