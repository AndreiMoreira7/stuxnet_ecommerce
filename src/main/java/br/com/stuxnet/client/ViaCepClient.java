package br.com.stuxnet.client;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import br.com.stuxnet.dto.ViaCepResponse;


@Component 
public class ViaCepClient {
    // nessa parte aqui o "cep" é substituido pelo valor que a gente passa no getForObject
    private static final String URL = "https://viacep.com.br/ws/{cep}/json/";
    
    private final RestTemplate restTemplate = new RestTemplate();

    public ViaCepResponse buscarPorCep (String cep){
        // aqui é só para tirar o hífem e qualquer coisa que não seja números 
        String limpo = cep == null ? "" : cep.replaceAll("\\D", "");
        
        if (limpo.length() != 8){
            throw new IllegalArgumentException("CEP deve ter 8 digitos");
        }
        ViaCepResponse resposta = restTemplate.getForObject(URL, ViaCepResponse.class, limpo);

        // no postman o cep que não existe não da erro 404, ele volta 200 com {"erro": true}, essa parte serve para voltar um erro 
        if(resposta == null || Boolean.TRUE.equals(resposta.getErro())){
            throw new IllegalArgumentException("CEP não encontrado: " + limpo);
        }
        return resposta;
    }
}
