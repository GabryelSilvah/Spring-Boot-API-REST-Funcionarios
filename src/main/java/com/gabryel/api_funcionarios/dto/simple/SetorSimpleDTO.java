package com.gabryel.api_funcionarios.dto.simple;

import com.gabryel.api_funcionarios.model.Setor;
import jakarta.validation.constraints.NotBlank;

public class SetorSimpleDTO {


    private Long id_setor;
    @NotBlank(message = "Nome do setor não foi informado.")
    private String nome_setor;


    //Contrutores

    public SetorSimpleDTO() {
    }

    public SetorSimpleDTO(Long id_setor, String nome_setor) {
        this.id_setor = id_setor;
        this.nome_setor = nome_setor;
    }

    public Setor convertToModel() {
        return new Setor(
                this.id_setor,
                this.nome_setor
        );
    }


    //Getters and Setters
    public Long getId_setor() {
        return id_setor;
    }

    public void setId_setor(Long id_setor) {
        this.id_setor = id_setor;
    }

    public String getNome_setor() {
        return nome_setor;
    }

    public void setNome_setor(String nome_setor) {
        this.nome_setor = nome_setor;
    }
}
