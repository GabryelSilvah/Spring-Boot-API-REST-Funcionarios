package com.gabryel.api_funcionarios.model;

import com.gabryel.api_funcionarios.dto.simple.CidadeSimpleDTO;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "cidades")
@SequenceGenerator(name = "cidade_sequence", sequenceName = "cidade_sequence", allocationSize = 1)
public class Cidade {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "cidade_sequence")
    private Long id_cidade;

    @Column(name = "nome_cidade", length = 40, nullable = false)
    private String nome_cidade = "";

    @OneToMany(mappedBy = "fk_cidade_endereco", fetch = FetchType.LAZY)
    private List<Endereco> enderecos_cidades = new ArrayList<>();


    //Contrutores
    public Cidade() {
    }

    public Cidade(Long id_cidade) {
        this.id_cidade = id_cidade;
    }

    public Cidade(Long id_cidade, String nome_cidade) {
        this.id_cidade = id_cidade;
        this.nome_cidade = nome_cidade;
    }

    public CidadeSimpleDTO convertToDto_output() {
        return new CidadeSimpleDTO(
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

    public List<Endereco> getEnderecos_cidades() {
        return enderecos_cidades;
    }

    public void setEnderecos_cidades(List<Endereco> enderecos_cidades) {
        this.enderecos_cidades = enderecos_cidades;
    }
}
