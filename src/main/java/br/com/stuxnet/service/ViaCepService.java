package br.com.stuxnet.service;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import br.com.stuxnet.dto.EnderecoDTO;


@Component 
public class ViaCepService {
    // nessa parte aqui o "cep" é substituido pelo valor que a gente passa no getForObject
    private static final String URL = "https://viacep.com.br/ws/{cep}/json/";
    
    private final RestTemplate restTemplate = new RestTemplate();

    public EnderecoDTO consultarCEP (String cep){
        // aqui é só para tirar o hífem e qualquer coisa que não seja números 
        String limpo = cep == null ? "" : cep.replaceAll("\\D", "");
        
        if (limpo.length() != 8){
            throw new IllegalArgumentException("CEP deve ter 8 digitos");
        }
        EnderecoDTO resposta = restTemplate.getForObject(URL, EnderecoDTO.class, limpo);

        // no postman o cep que não existe não da erro 404, ele volta 200 com {"erro": true}, essa parte serve para voltar um erro 
        if(resposta == null || Boolean.TRUE.equals(resposta.getErro())){
            throw new IllegalArgumentException("CEP não encontrado: " + limpo);
        }
        return resposta;
    }
}
