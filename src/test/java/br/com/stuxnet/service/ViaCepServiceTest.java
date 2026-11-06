package br.com.stuxnet.service;

import br.com.stuxnet.dto.EnderecoDTO;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

// esses testes realmente chamam a ViaCEP, então é necessário o acesso à intenet
public class ViaCepServiceTest {
    private final ViaCepService client = new ViaCepService();

    @Test
    void testBuscarCepValido(){
        // 01001-000 é a Praça da Sé, em SP, testei mesmo CEP no Postman e uso com hífen de propósito pra garantir que a limpeza do CEP funciona
        EnderecoDTO r = client.consultarCEP("01001-000");
        assertEquals("SP", r.getUf());
        assertEquals("São Paulo", r.getLocalidade());
    }

    @Test 
    void testBuscarCepInesistente(){
        // 99999999 tem 8 digitos mas não existe, a ViaCep responde {"erro": true}
        assertThrows(IllegalArgumentException.class, () -> client.consultarCEP("99999999"));
    }

    @Test
    void testBuscarCepInvalido(){
        // 123 nem chega a ser enviado por API, o client barra antes
        assertThrows(IllegalArgumentException.class, () -> client.consultarCEP("123"));
    }
}
