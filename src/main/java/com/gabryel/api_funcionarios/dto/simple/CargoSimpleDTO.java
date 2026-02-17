package com.gabryel.api_funcionarios.dto.simple;

import com.gabryel.api_funcionarios.model.Cargo;
import jakarta.validation.constraints.NotBlank;

public class CargoSimpleDTO {

    private Long id_cargo;
    @NotBlank(message = "Nome do cargo não foi informado.")
    private String nome_cargo;


    //Construtores

    public CargoSimpleDTO() {
    }

    public CargoSimpleDTO(Long id_cargo, String nome_cargo) {
        this.id_cargo = id_cargo;
        this.nome_cargo = nome_cargo;
    }

    public Cargo convertToModel() {
        return new Cargo(
                this.id_cargo,
                this.nome_cargo
        );
    }

    //Getters and Setters

    public Long getId_cargo() {
        return id_cargo;
    }

    public void setId_cargo(Long id_cargo) {
        this.id_cargo = id_cargo;
    }

    public String getNome_cargo() {
        return nome_cargo;
    }

    public void setNome_cargo(String nome_cargo) {
        this.nome_cargo = nome_cargo;
    }
}
