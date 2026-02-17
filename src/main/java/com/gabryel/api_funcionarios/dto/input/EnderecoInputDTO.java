package com.gabryel.api_funcionarios.dto.input;

import com.gabryel.api_funcionarios.model.Cidade;
import com.gabryel.api_funcionarios.model.Endereco;
import com.gabryel.api_funcionarios.model.Funcionario;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class EnderecoInputDTO {

    private Long id_endereco;
    @NotNull(message = "O número de CEP não foi informado.")
    private int cep_endereco;
    @NotNull(message = "A chave estrangeira da cidade não foi informada.")
    private Long id_cidade_endereco;
    @NotBlank(message = "O bairro não foi informado.")
    private String bairro_endereco;
    private String complemento_endereco;
    private Long id_funcionario_endereco = 0L;


    //Contrutores
    public EnderecoInputDTO() {
    }


    public EnderecoInputDTO(Long id_endereco, int cep_endereco, Long id_cidade_endereco, String bairro_endereco, String complemento_endereco, Long id_funcionario_endereco) {
        this.id_endereco = id_endereco;
        this.cep_endereco = cep_endereco;
        this.id_cidade_endereco = id_cidade_endereco;
        this.bairro_endereco = bairro_endereco;
        this.complemento_endereco = complemento_endereco;
        this.id_funcionario_endereco = id_funcionario_endereco;
    }


    public Endereco convertToModel() {
        return new Endereco(
                this.id_endereco,
                this.cep_endereco,
                new Cidade(this.id_cidade_endereco),
                this.bairro_endereco,
                this.complemento_endereco,
                new Funcionario(this.id_funcionario_endereco)
        );
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

    public Long getId_cidade_endereco() {
        return id_cidade_endereco;
    }

    public void setId_cidade_endereco(Long id_cidade_endereco) {
        this.id_cidade_endereco = id_cidade_endereco;
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

    public Long getId_funcionario_endereco() {
        return id_funcionario_endereco;
    }

    public void setId_funcionario_endereco(Long id_funcionario_endereco) {
        this.id_funcionario_endereco = id_funcionario_endereco;
    }
}
