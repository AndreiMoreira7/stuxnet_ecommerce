package br.com.stuxnet.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

// a viaCEP devolve mais campos do que a gente usa(ibge, gia, ddd, siafi etc), então mando o Jackson ignorar o que não existe aqui, senão dava erro
@JsonIgnoreProperties(ignoreUnknown = true)

public class ViaCepResponse {
    
    private String cep;
    private String logradouro;
    private String complemento;
    private String bairro;
    private String localidade; // aqui é a cidade, a viaCEP chama de "localidade"
    private String uf;
    private Boolean erro; // o Boolean aqui com B maiúsculo é de proposito, em um CEP válido esse campo nem vem no JSON, ai fica null. com boolean primitivo ele viraria false e ia dar no mesmo, mas assim dá para diferenciar "não veio" de "veio false", basicamente 
    
    public ViaCepResponse(){
        // esse campo aqui é vazio mesmo, por que no Jackson o construtor vazio é obrigatório, para conseguir montar o objeto  
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getLogradouro() {
        return logradouro;
    }

    public void setLogradouro(String logradouro) {
        this.logradouro = logradouro;
    }

    public String getComplemento() {
        return complemento;
    }

    public void setComplemento(String complemento) {
        this.complemento = complemento;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getLocalidade() {
        return localidade;
    }

    public void setLocalidade(String localidade) {
        this.localidade = localidade;
    }

    public String getUf() {
        return uf;
    }

    public void setUf(String uf) {
        this.uf = uf;
    }

    public Boolean getErro() {
        return erro;
    }

    public void setErro(Boolean erro) {
        this.erro = erro;
    }

    // para facilitar ver o objeto no log e nos testes
    @Override 
    public String toString(){
        return "ViaCepResponse{cep='" + cep + "', logradouro='" + logradouro + "', bairro='" + bairro + "', localidade= '" + localidade + "', uf='" + uf + "'}"; 
    }
}
