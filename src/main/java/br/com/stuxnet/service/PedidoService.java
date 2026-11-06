package br.com.stuxnet.service;

import java.math.BigDecimal;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.stuxnet.dto.ItemPedidoRequestDTO;
import br.com.stuxnet.dto.PedidoRequestDTO;
import br.com.stuxnet.dto.PedidoResponseDTO;
import br.com.stuxnet.model.Cliente;
import br.com.stuxnet.model.ComponenteHardware;
import br.com.stuxnet.model.ItemPedido;
import br.com.stuxnet.model.Pedido;
import br.com.stuxnet.repository.ClienteRepository;
import br.com.stuxnet.repository.ComponenteHardwareRepository;
import br.com.stuxnet.repository.PedidoRepository;
import jakarta.persistence.EntityNotFoundException;

@Service
public class PedidoService {

    private final PedidoRepository pedidoRepository;
    private final ClienteRepository clienteRepository;
    private final ComponenteHardwareRepository componenteRepository;
    private final ComponenteHardwareService componenteService;

    public PedidoService(PedidoRepository pedidoRepository,
                         ClienteRepository clienteRepository,
                         ComponenteHardwareRepository componenteRepository,
                         ComponenteHardwareService componenteService) {
        this.pedidoRepository = pedidoRepository;
        this.clienteRepository = clienteRepository;
        this.componenteRepository = componenteRepository;
        this.componenteService = componenteService;
    }

    @Transactional
    public PedidoResponseDTO realizarPedido(PedidoRequestDTO dto) {
        Cliente cliente = clienteRepository.findById(dto.clienteId())
                .orElseThrow(() -> new EntityNotFoundException("Cliente não encontrado com ID: " + dto.clienteId()));

        Pedido novoPedido = new Pedido(cliente, dto.modalidadePagamento());

        for (ItemPedidoRequestDTO itemDto : dto.itens()) {
            ComponenteHardware hardware = componenteRepository.findBySku(itemDto.sku())
                    .orElseThrow(() -> new EntityNotFoundException("Hardware não encontrado para o SKU: " + itemDto.sku()));

            if (!hardware.isAtivo()) {
                throw new IllegalStateException("O componente " + hardware.getSku() + " está indisponível para venda.");
            }

            if (hardware.getQuantidadeEstoque() < itemDto.quantidade()) {
                throw new IllegalStateException(String.format(
                        "Estoque insuficiente para '%s'. Disponível: %d, Solicitado: %d",
                        hardware.getNome(), hardware.getQuantidadeEstoque(), itemDto.quantidade()
                ));
            }

            // Baixa atômica de estoque
            hardware.setQuantidadeEstoque(hardware.getQuantidadeEstoque() - itemDto.quantidade());

            // Aplica o Design Pattern Strategy para definir o preço unitário conforme PIX ou CARTAO
            BigDecimal precoAplicado = componenteService.calcularPrecoPorModalidade(
                    hardware.getPrecoBase(),
                    dto.modalidadePagamento()
            );

            ItemPedido itemPedido = new ItemPedido(hardware, itemDto.quantidade(), precoAplicado);
            novoPedido.adicionarItem(itemPedido);
        }

        Pedido pedidoSalvo = pedidoRepository.save(novoPedido);
        return PedidoResponseDTO.fromEntity(pedidoSalvo);
    }
}