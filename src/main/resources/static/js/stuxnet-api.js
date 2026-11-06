const API_BASE = '/api/v1';

// 1. Carrega o catálogo de hardwares na vitrine
async function carregarCatalogoHardware() {
    const response = await fetch(`${API_BASE}/componentes`);
    if (!response.ok) throw new Error('Falha ao carregar catálogo StuxNet');
    return await response.json();
}

// 2. Cadastra um novo componente de hardware
async function cadastrarHardware(dadosHardware) {
    const response = await fetch(`${API_BASE}/componentes`, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(dadosHardware)
    });
    const resultado = await response.json();
    if (!response.ok) throw resultado; // Captura o ProblemDetail do GlobalExceptionHandler
    return resultado;
}

// 3. Finaliza um pedido de compra (PIX ou CARTAO)
async function fecharPedido(clienteId, modalidadePagamento, itensCarrinho) {
    const payload = {
        clienteId: clienteId,
        modalidadePagamento: modalidadePagamento, // "PIX" ou "CARTAO"
        itens: itensCarrinho // Ex: [{ sku: "GPU-RTX4070S-ASUS", quantidade: 1 }]
    };

    const response = await fetch(`${API_BASE}/pedidos`, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(payload)
    });
    const resultado = await response.json();
    if (!response.ok) throw resultado;
    return resultado;
}