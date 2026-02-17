package com.gabryel.api_funcionarios.repository;

import com.gabryel.api_funcionarios.model.Funcionario;
import jakarta.annotation.Nullable;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.NativeQuery;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FuncionarioRepository extends JpaRepository<Funcionario, Long> {

    @Override
    @NotNull
    Page<Funcionario> findAll(@Nullable Pageable pageable);


    @NativeQuery("SELECT * FROM funcionarios WHERE nome_funcionario = :nome_funcionario LIMIT 1;")
    Optional<Funcionario> findByName(String nome_funcionario);

    @Transactional
    @Modifying
    @NativeQuery("UPDATE funcionarios SET ativo_funcionario = :ativo_funcionario WHERE id_funcionario = :id_funcionario;")
    void changeStatus(Long id_funcionario, Boolean ativo_funcionario);
}
