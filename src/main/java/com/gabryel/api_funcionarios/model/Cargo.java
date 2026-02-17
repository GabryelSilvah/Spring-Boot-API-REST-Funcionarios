package com.gabryel.api_funcionarios.model;

import com.gabryel.api_funcionarios.dto.simple.CargoSimpleDTO;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "cargos")
@SequenceGenerator(name = "cargo_sequence", sequenceName = "cargo_sequence", allocationSize = 1)
public class Cargo {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "cargo_sequence")
    private Long id_cargo;

    @Column(name = "nome_cargo", length = 40, nullable = false)
    private String nome_cargo = "";

    @OneToMany(mappedBy = "fk_cargo_funcionario", fetch = FetchType.LAZY)
    private List<Funcionario> funcionarios_cargo = new ArrayList<>();


    //Construtores
    public Cargo() {
    }

    public Cargo(Long id_cargo) {
        this.id_cargo = id_cargo;
    }

    public Cargo(Long id_cargo, String nome_cargo) {
        this.id_cargo = id_cargo;
        this.nome_cargo = nome_cargo;
    }

    public CargoSimpleDTO convertToDto() {
        return new CargoSimpleDTO(
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

    public List<Funcionario> getFuncionarios_cargo() {
        return funcionarios_cargo;
    }

    public void setFuncionarios_cargo(List<Funcionario> funcionarios_cargo) {
        this.funcionarios_cargo = funcionarios_cargo;
    }
}
