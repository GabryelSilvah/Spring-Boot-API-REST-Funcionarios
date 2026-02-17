package com.gabryel.api_funcionarios.repository;

import com.gabryel.api_funcionarios.model.Endereco;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.NativeQuery;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EnderecoRepository extends JpaRepository<Endereco, Long> {
    @NativeQuery("SELECT * FROM enderecos WHERE nome_endereco = :nome_ender;")
    Optional<Endereco> findByName(String nome_ender);

    @NativeQuery("SELECT * FROM enderecos WHERE fk_funcionario_endereco = :fk_funcionario;")
    List<Endereco> findByFk_funcionario(Long fk_funcionario);
    @Modifying
    @NativeQuery("DELETE FROM enderecos WHERE fk_funcionario_endereco = :fk_funcionario")
    void deleteByFk_funcionario(Long fk_funcionario);

    @NativeQuery("SELECT * FROM enderecos WHERE id_endereco = :id_endereco and fk_funcionario_endereco = :fk_funcionario;")
    Optional<Endereco> findByID_FK_funcionario(Long id_endereco, Long fk_funcionario);
}
