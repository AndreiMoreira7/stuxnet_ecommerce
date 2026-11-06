
package br.com.stuxnet.service.event;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record PedidoRealizadoEvent(
        String codigoPedido,
        String emailCliente,
        String modalidadePagamento,
        BigDecimal valorTotal,
        LocalDateTime dataHora
) {}
