package com.gabryel.api_funcionarios.repository;

import com.gabryel.api_funcionarios.model.Cidade;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.NativeQuery;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CidadeRepository extends JpaRepository<Cidade, Long> {

    @Override
    @NotNull
    Page<Cidade> findAll(@NotNull Pageable pageable);


    @NativeQuery("SELECT * FROM cidades WHERE nome_cidade = :nome_cidade;")
    Optional<Cidade> findByName(String nome_cidade);
}
