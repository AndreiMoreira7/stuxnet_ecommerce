package br.com.stuxnet.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import br.com.stuxnet.model.Pedido;
import br.com.stuxnet.model.enums.StatusPedido;

public record PedidoResponseDTO(
        String codigoPedido,
        StatusPedido status,
        String modalidadePagamento,
        BigDecimal valorTotal,
        LocalDateTime dataCriacao,
        List<ItemResumoDTO> itens
) {
    public record ItemResumoDTO(String sku, String nome, Integer quantidade, BigDecimal precoUnitario, BigDecimal subtotal) {}

    public static PedidoResponseDTO fromEntity(Pedido pedido) {
        List<ItemResumoDTO> itensResumo = pedido.getItens().stream()
                .map(i -> new ItemResumoDTO(
                        i.getComponente().getSku(),
                        i.getComponente().getNome(),
                        i.getQuantidade(),
                        i.getPrecoUnitario(),
                        i.getSubtotal()
                ))
                .toList();

        return new PedidoResponseDTO(
                pedido.getCodigoPedido(),
                pedido.getStatus(),
                pedido.getModalidadePagamento(),
                pedido.getValorTotal(),
                pedido.getDataCriacao(),
                itensResumo
        );
    }
}