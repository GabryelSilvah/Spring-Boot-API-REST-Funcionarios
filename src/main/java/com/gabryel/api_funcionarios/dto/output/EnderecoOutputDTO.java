package com.gabryel.api_funcionarios.dto.output;

public class EnderecoOutputDTO {

    private Long id_endereco;
    private int cep_endereco;
    private String cidade_endereco;
    private String bairro_endereco;
    private String complemento_endereco;
    private String funcionario_endereco;


    //Contrutores
    public EnderecoOutputDTO() {
    }

    public EnderecoOutputDTO(Long id_endereco, int cep_endereco, String cidade_endereco, String bairro_endereco, String complemento_endereco, String funcionario_endereco) {
        this.id_endereco = id_endereco;
        this.cep_endereco = cep_endereco;
        this.cidade_endereco = cidade_endereco;
        this.bairro_endereco = bairro_endereco;
        this.complemento_endereco = complemento_endereco;
        this.funcionario_endereco = funcionario_endereco;
    }


    //Getters and Setters
    public Long getId_endereco() {
        return id_endereco;
    }

    public void setId_endereco(Long id_endereco) {
        this.id_endereco = id_endereco;
    }

    public int getCep_endereco() {
        return cep_endereco;
    }

    public void setCep_endereco(int cep_endereco) {
        this.cep_endereco = cep_endereco;
    }

    public String getCidade_endereco() {
        return cidade_endereco;
    }

    public void setCidade_endereco(String cidade_endereco) {
        this.cidade_endereco = cidade_endereco;
    }

    public String getBairro_endereco() {
        return bairro_endereco;
    }

    public void setBairro_endereco(String bairro_endereco) {
        this.bairro_endereco = bairro_endereco;
    }

    public String getComplemento_endereco() {
        return complemento_endereco;
    }

    public void setComplemento_endereco(String complemento_endereco) {
        this.complemento_endereco = complemento_endereco;
    }

    public String getFuncionario_endereco() {
        return funcionario_endereco;
    }

    public void setFuncionario_endereco(String funcionario_endereco) {
        this.funcionario_endereco = funcionario_endereco;
    }
}
