package com.gabryel.api_funcionarios.model;

import com.gabryel.api_funcionarios.dto.output.EnderecoOutputDTO;
import jakarta.persistence.*;

@Entity
@Table(name = "enderecos")
@SequenceGenerator(name = "endereco_sequence", sequenceName = "endereco_sequence", allocationSize = 1)
public class Endereco {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "endereco_sequence")
    private Long id_endereco;

    @Column(name = "cep_endereco", length = 8, nullable = false)
    private int cep_endereco = 0;

    @ManyToOne
    @JoinColumn(name = "fk_cidade_endereco", referencedColumnName = "id_cidade", nullable = false)
    private Cidade fk_cidade_endereco = new Cidade();

    @Column(name = "bairro_endereco", length = 40, nullable = false)
    private String bairro_endereco = "";

    @Column(name = "complemento_endereco", length = 40, nullable = false)
    private String complemento_endereco = "";

    @ManyToOne
    @JoinColumn(name = "fk_funcionario_endereco", referencedColumnName = "id_funcionario", nullable = false)
    private Funcionario fk_funcionario_endereco = new Funcionario();


    //Contrutores
    public Endereco() {
    }

    public Endereco(Long id_endereco) {
        this.id_endereco = id_endereco;
    }

    public Endereco(Long id_endereco, int cep_endereco, Cidade fk_cidade_endereco, String bairro_endereco, String complemento_endereco, Funcionario fk_funcionario_endereco) {
        this.id_endereco = id_endereco;
        this.cep_endereco = cep_endereco;
        this.fk_cidade_endereco = fk_cidade_endereco;
        this.bairro_endereco = bairro_endereco;
        this.complemento_endereco = complemento_endereco;
        this.fk_funcionario_endereco = fk_funcionario_endereco;
    }


    public EnderecoOutputDTO convertToOutputDTO() {
        return new EnderecoOutputDTO(
                this.id_endereco,
                this.cep_endereco,
                this.fk_cidade_endereco.getNome_cidade(),
                this.bairro_endereco,
                this.complemento_endereco,
                this.fk_funcionario_endereco.getNome_funcionario()
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

    public Cidade getFk_cidade_endereco() {
        return fk_cidade_endereco;
    }

    public void setFk_cidade_endereco(Cidade fk_cidade_endereco) {
        this.fk_cidade_endereco = fk_cidade_endereco;
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

    public Funcionario getFk_funcionario_endereco() {
        return fk_funcionario_endereco;
    }

    public void setFk_funcionario_endereco(Funcionario fk_funcionario_endereco) {
        this.fk_funcionario_endereco = fk_funcionario_endereco;
    }
}
