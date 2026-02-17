package com.gabryel.api_funcionarios.dto.output;

import java.util.List;

public class FuncionarioOutputDTO {
    private Long id_funcionario;
    private String nome_funcionario;
    private boolean ativo_funcionario;
    private String cargo_funcionario;
    private String setor_funcionario;
    private List<EnderecoOutputDTO> enderecos_funcionario;
    private long date_create_funcionario;
    private long date_update_funcionario;


    //Contrutores
    public FuncionarioOutputDTO() {
    }

    public FuncionarioOutputDTO(Long id_funcionario, String nome_funcionario, boolean ativo_funcionario, String cargo_funcionario, String setor_funcionario, List<EnderecoOutputDTO> enderecos_funcionario, long date_create_funcionario, long date_update_funcionario) {
        this.id_funcionario = id_funcionario;
        this.nome_funcionario = nome_funcionario;
        this.ativo_funcionario = ativo_funcionario;
        this.cargo_funcionario = cargo_funcionario;
        this.setor_funcionario = setor_funcionario;
        this.enderecos_funcionario = enderecos_funcionario;
        this.date_create_funcionario = date_create_funcionario;
        this.date_update_funcionario = date_update_funcionario;
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

    public String getCargo_funcionario() {
        return cargo_funcionario;
    }

    public void setCargo_funcionario(String cargo_funcionario) {
        this.cargo_funcionario = cargo_funcionario;
    }

    public String getSetor_funcionario() {
        return setor_funcionario;
    }

    public void setSetor_funcionario(String setor_funcionario) {
        this.setor_funcionario = setor_funcionario;
    }

    public List<EnderecoOutputDTO> getEnderecos_funcionario() {
        return enderecos_funcionario;
    }

    public void setEnderecos_funcionario(List<EnderecoOutputDTO> enderecos_funcionario) {
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
