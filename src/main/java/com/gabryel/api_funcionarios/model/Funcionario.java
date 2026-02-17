package com.gabryel.api_funcionarios.model;

import com.gabryel.api_funcionarios.dto.output.FuncionarioOutputDTO;
import jakarta.persistence.*;
import org.hibernate.annotations.ColumnDefault;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "funcionarios")
@SequenceGenerator(name = "funcionario_sequence", sequenceName = "funcionario_sequence", allocationSize = 1)
public class Funcionario {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "funcionario_sequence")
    private Long id_funcionario;

    @Column(name = "nome_funcionario", length = 90, nullable = false)
    private String nome_funcionario = "";

    @Column(name = "ativo_funcionario", nullable = false)
    @ColumnDefault("false")
    private boolean ativo_funcionario;

    @ManyToOne
    @JoinColumn(name = "fk_cargo_funcionario", referencedColumnName = "id_cargo", nullable = false)
    private Cargo fk_cargo_funcionario = new Cargo();

    @ManyToOne
    @JoinColumn(name = "fk_setor_funcionario", referencedColumnName = "id_setor", nullable = false)
    private Setor fk_setor_funcionario = new Setor();

    @OneToMany(mappedBy = "fk_funcionario_endereco", fetch = FetchType.LAZY)
    private List<Endereco> enderecos_funcionario = new ArrayList<>();

    @Column(name = "date_create_funcionario", nullable = false)
    private long date_create_funcionario = 0L;

    @Column(name = "date_update_funcionario", nullable = false)
    private long date_update_funcionario = 0L;


    //Construtores
    public Funcionario() {
    }

    public Funcionario(Long id_funcionario) {
        this.id_funcionario = id_funcionario;
    }

    public Funcionario(Long id_funcionario, String nome_funcionario, boolean ativo_funcionario, Cargo fk_cargo_funcionario, Setor fk_setor_funcionario, long date_create_funcionario, long date_update_funcionario) {
        this.id_funcionario = id_funcionario;
        this.nome_funcionario = nome_funcionario;
        this.ativo_funcionario = ativo_funcionario;
        this.fk_cargo_funcionario = fk_cargo_funcionario;
        this.fk_setor_funcionario = fk_setor_funcionario;
        this.date_create_funcionario = date_create_funcionario;
        this.date_update_funcionario = date_update_funcionario;
    }

    public FuncionarioOutputDTO convertToDto_output() {
        return new FuncionarioOutputDTO(
                this.id_funcionario,
                this.nome_funcionario,
                this.ativo_funcionario,
                this.fk_cargo_funcionario.getNome_cargo(),
                this.fk_setor_funcionario.getNome_setor(),
                this.enderecos_funcionario.stream().map(Endereco::convertToOutputDTO).toList(),
                this.date_create_funcionario,
                this.date_update_funcionario
        );
    }


    //Getters and Setters
    public Long getId_funcionario() {
        return id_funcionario;
    }

    public void setId_funcionario(Long id_funcionario) {
        this.id_funcionario = id_funcionario;
    }

    public String getNome_funcionario() {
        return nome_funcionario;
    }

    public void setNome_funcionario(String nome_funcionario) {
        this.nome_funcionario = nome_funcionario;
    }

    public boolean isAtivo_funcionario() {
        return ativo_funcionario;
    }

    public void setAtivo_funcionario(boolean ativo_funcionario) {
        this.ativo_funcionario = ativo_funcionario;
    }

    public Cargo getFk_cargo_funcionario() {
        return fk_cargo_funcionario;
    }

    public void setFk_cargo_funcionario(Cargo fk_cargo_funcionario) {
        this.fk_cargo_funcionario = fk_cargo_funcionario;
    }

    public Setor getFk_setor_funcionario() {
        return fk_setor_funcionario;
    }

    public void setFk_setor_funcionario(Setor fk_setor_funcionario) {
        this.fk_setor_funcionario = fk_setor_funcionario;
    }

    public List<Endereco> getEnderecos_funcionario() {
        return enderecos_funcionario;
    }

    public void setEnderecos_funcionario(List<Endereco> enderecos_funcionario) {
        this.enderecos_funcionario = enderecos_funcionario;
    }

    public long getDate_create_funcionario() {
        return date_create_funcionario;
    }

    public void setDate_create_funcionario(long date_create_funcionario) {
        this.date_create_funcionario = date_create_funcionario;
    }

    public long getDate_update_funcionario() {
        return date_update_funcionario;
    }

    public void setDate_update_funcionario(long date_update_funcionario) {
        this.date_update_funcionario = date_update_funcionario;
    }
}
