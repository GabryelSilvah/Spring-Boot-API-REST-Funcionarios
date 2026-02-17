package com.gabryel.api_funcionarios.model;

import com.gabryel.api_funcionarios.dto.simple.SetorSimpleDTO;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "setores")
@SequenceGenerator(name = "setor_sequence", sequenceName = "setor_sequence", allocationSize = 1)
public class Setor {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "setor_sequence")
    private Long id_setor;

    @Column(name = "nome_setor", length = 40, nullable = false)
    private String nome_setor = "";

    @OneToMany(mappedBy = "fk_setor_funcionario", fetch = FetchType.LAZY)
    private List<Funcionario> funcionarios_setor = new ArrayList<>();


    //Contrutores
    public Setor() {
    }

    public Setor(Long id_setor) {
        this.id_setor = id_setor;
    }

    public Setor(Long id_setor, String nome_setor) {
        this.id_setor = id_setor;
        this.nome_setor = nome_setor;
    }

    public SetorSimpleDTO convertToDto(){
        return new SetorSimpleDTO(
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

    public List<Funcionario> getFuncionarios_setor() {
        return funcionarios_setor;
    }

    public void setFuncionarios_setor(List<Funcionario> funcionarios_setor) {
        this.funcionarios_setor = funcionarios_setor;
    }
}
