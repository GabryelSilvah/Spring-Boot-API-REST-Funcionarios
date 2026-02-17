package com.gabryel.api_funcionarios.dto.simple;

import com.gabryel.api_funcionarios.model.Cidade;
import jakarta.validation.constraints.NotBlank;

public class CidadeSimpleDTO {

    private Long id_cidade;
    @NotBlank(message = "Nome da cidade não foi informada.")
    private String nome_cidade;


    //Contrutores
    public CidadeSimpleDTO() {
    }
    public CidadeSimpleDTO(Long id_cidade, String nome_cidade) {
        this.id_cidade = id_cidade;
        this.nome_cidade = nome_cidade;
    }

    public Cidade convertToModel() {
        return new Cidade(
                this.id_cidade,
                this.nome_cidade
        );
    }

    //Getters and Setters
    public Long getId_cidade() {
        return id_cidade;
    }

    public void setId_cidade(Long id_cidade) {
        this.id_cidade = id_cidade;
    }

    public String getNome_cidade() {
        return nome_cidade;
    }

    public void setNome_cidade(String nome_cidade) {
        this.nome_cidade = nome_cidade;
    }
}
