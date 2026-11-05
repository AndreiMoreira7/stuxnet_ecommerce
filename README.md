# Stuxnet - E-commerce de Hardware

## Descrição do Problema
O **Stuxnet** é uma API REST para um e-commerce de hardware (peças e periféricos). O sistema resolve a dificuldade de gerenciar de forma consistente o catálogo técnico, o controle de estoque em tempo real e a precisão das entregas. Ao invés de digitação manual propensa a erros, a API automatiza a validação de endereços e centraliza o fluxo de compras, garantindo que o cliente finalize o pedido com estoque reservado e endereço correto.

## Integrantes da Equipe
* Andrei Santos Moreira
* Marcio Cajaíba Monteiro Junior
* Paulo Victor Nunes Lucas dos Santos


## Integração com API Externa
O backend consome a API pública do **ViaCEP** (`viacep.com.br/ws/`). Ao receber um CEP no cadastro do endereço, o Stuxnet consulta essa API para obter logradouro, bairro, cidade e UF já normalizados. Esses dados são utilizados pelo sistema tanto para estruturar o perfil do cliente quanto para a regra de negócio que calcula o frete do pedido.

## Requisitos Funcionais Principais (Prioridade Alta / Must)
* **RF-001 — Cadastro de usuário:** Visitantes criam conta informando nome, e-mail, CPF e senha.
* **RF-002 — Login e emissão de token:** Autenticação que devolve um token JWT para navegação segura.
* **RF-005 — Consulta de CEP via ViaCEP:** Retorna dados de endereço normalizados a partir do CEP.
* **RF-006 — Cadastro de endereço de entrega:** Salva o endereço vinculado ao cliente utilizando os dados do ViaCEP.
* **RF-007 — Tratamento de falha da ViaCEP:** Trata indisponibilidades da API externa com timeouts e permite entrada manual.
* **RF-009 — Gerenciamento de categorias:** O administrador cria, edita e lista categorias de hardware.
* **RF-010 — Gerenciamento de produtos:** O administrador cadastra itens com SKUs, preços e controle de estoque.
* **RF-011 — Listagem, busca e filtros do catálogo:** Busca por nome/marca e filtros por categoria e preço.
* **RF-012 — Detalhe do produto:** Exibe especificações técnicas e disponibilidade de estoque.
* **RF-014 — Adicionar produto ao carrinho:** Cliente insere itens e o sistema valida a quantidade em estoque.
* **RF-015 — Alterar e remover itens do carrinho:** Atualiza quantidades e recalcula o subtotal dinamicamente.
* **RF-016 — Cálculo de frete:** Estima valor e prazo usando a UF devolvida pela ViaCEP e regras de peso.
* **RF-017 — Finalizar pedido (checkout):** Valida estoque, cria o pedido, congela preços e limpa o carrinho.
* **RF-019 — Consulta e histórico de pedidos:** Clientes acessam os detalhes e status das próprias compras.
* **RF-021 — Atualização de status do pedido:** Administrador avança o fluxo do pedido (ex: Pago para Enviado) e informa rastreio.
