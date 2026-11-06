package br.com.stuxnet.service.event;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class AuditoriaLogisticaListener {

    private static final Logger log = LoggerFactory.getLogger(AuditoriaLogisticaListener.class);

    @EventListener
    public void aoRealizarPedido(PedidoRealizadoEvent event) {
        log.info("[AUDITORIA STUXNET] Novo pedido confirmado! Código: {} | Cliente: {} | Pagamento: {} | Total: R$ {}",
                event.codigoPedido(),
                event.emailCliente(),
                event.modalidadePagamento(),
                event.valorTotal());
    }
}