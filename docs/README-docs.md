# Documentação e Modelagem - Stuxnet

Esta pasta (/docs) contém a documentação arquitetural, os diagramas de modelagem UML/ER e a coleção de requisições (Postman) usada para testar a integração com a API externa do projeto Stuxnet.

---

## Estrutura dos Arquivos da Pasta /docs

| Arquivo | Descrição |
| :--- | :--- |
| README-docs.md | Este arquivo: guia do conteúdo da pasta. |
| diagrama classes.pdf | Diagrama de Classes: mapeamento das classes de domínio (POJOs), atributos e relacionamentos da aplicação (Cliente, Produto, Pedido, etc.). |
| diagrama entidades.pdf | DER (Diagrama Entidade-Relacionamento): estrutura conceitual do banco de dados, com tabelas, chaves e cardinalidades. |
| diagramas componentes.pdf | Diagrama de Componentes: camadas da arquitetura (Controller, Service, Repository, DTO) e a integração com o serviço externo. |
| stuxnet.postman_collection.json | Coleção Postman: requisições de exemplo para consultar a API ViaCEP. |

---

## Coleção do Postman (stuxnet.postman_collection.json)

A coleção reúne três requisições GET à API pública ViaCEP, que mostram os três comportamentos que o backend precisa tratar. Elas não têm scripts de teste automatizado: servem para conferir manualmente a resposta da API. A verificação automatizada está nos testes Java (`ViaCepServiceTest`).

### Requisições da coleção

1. **ViaCEP - CEP válido**
   - **Método:** GET
   - **URL:** https://viacep.com.br/ws/01001000/json/
   - **Resposta esperada:** 200 OK, com logradouro, bairro, localidade e UF.

2. **ViaCEP - CEP inexistente**
   - **Método:** GET
   - **URL:** https://viacep.com.br/ws/99999999/json/
   - **Resposta esperada:** 200 OK com o campo `"erro": "true"` (o valor vem como texto). O CEP tem 8 dígitos, mas não existe na base dos Correios.

3. **ViaCEP - CEP inválido**
   - **Método:** GET
   - **URL:** https://viacep.com.br/ws/123/json/
   - **Resposta esperada:** 400 Bad Request (a ViaCEP devolve uma página HTML de erro, não JSON), porque o CEP não tem 8 dígitos.
   
---

## Como importar a coleção no Postman

1. Abra o [Postman](https://www.postman.com/).
2. Clique em **Import** (canto superior esquerdo) ou use Ctrl + O.
3. Selecione o arquivo `stuxnet.postman_collection.json` desta pasta.
4. A coleção **Stuxnet** aparece na aba lateral **Collections**.
5. Abra cada requisição e clique em **Send** para ver a resposta.