package com.gabryel.api_funcionarios.dto.input;

import com.gabryel.api_funcionarios.model.Cargo;
import com.gabryel.api_funcionarios.model.Funcionario;
import com.gabryel.api_funcionarios.model.Setor;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public class FuncionarioInputDTO {
    private Long id_funcionario;
    @NotBlank(message = "Nome do funcionário não foi informado.")
    private String nome_funcionario;
    @NotNull(message = "Status do funcionário não foi informado.")
    private Boolean ativo_funcionario;
    @NotNull(message = "Cargo do funcionário não foi informado.")
    private Long id_cargo_funcionario;
    @NotNull(message = "Setor do funcionário não foi informado.")
    private Long id_setor_funcionario;
    @NotNull(message = "Endereços do funcionário não foram informados.")
    @Valid
    private List<EnderecoInputDTO> enderecos_funcionario;
    private long date_create_funcionario;
    private long date_update_funcionario;


    //Contrutores
    public FuncionarioInputDTO() {
    }

    public FuncionarioInputDTO(Long id_funcionario, String nome_funcionario, boolean ativo_funcionario, Long id_cargo_funcionario, Long id_setor_funcionario, List<EnderecoInputDTO> enderecos_funcionario) {
        this.id_funcionario = id_funcionario;
        this.nome_funcionario = nome_funcionario;
        this.ativo_funcionario = ativo_funcionario;
        this.id_cargo_funcionario = id_cargo_funcionario;
        this.id_setor_funcionario = id_setor_funcionario;
        this.enderecos_funcionario = enderecos_funcionario;
    }

    public Funcionario convertToModel() {
        return new Funcionario(
                this.id_funcionario,
                this.nome_funcionario,
                this.ativo_funcionario,
                new Cargo(this.id_cargo_funcionario),
                new Setor(this.id_setor_funcionario),
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

    public boolean isAtivo_funcionario() {
        return ativo_funcionario;
    }

    public void setAtivo_funcionario(boolean ativo_funcionario) {
        this.ativo_funcionario = ativo_funcionario;
    }

    public String getNome_funcionario() {
        return nome_funcionario;
    }

    public void setNome_funcionario(String nome_funcionario) {
        this.nome_funcionario = nome_funcionario;
    }

    public Long getId_cargo_funcionario() {
        return id_cargo_funcionario;
    }

    public void setId_cargo_funcionario(Long id_cargo_funcionario) {
        this.id_cargo_funcionario = id_cargo_funcionario;
    }

    public Long getId_setor_funcionario() {
        return id_setor_funcionario;
    }

    public void setId_setor_funcionario(Long id_setor_funcionario) {
        this.id_setor_funcionario = id_setor_funcionario;
    }

    public List<EnderecoInputDTO> getEnderecos_funcionario() {
        return enderecos_funcionario;
    }

    public void setEnderecos_funcionario(List<EnderecoInputDTO> enderecos_funcionario) {
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
