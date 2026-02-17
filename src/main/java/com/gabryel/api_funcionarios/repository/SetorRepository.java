package com.gabryel.api_funcionarios.repository;

import com.gabryel.api_funcionarios.model.Setor;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.NativeQuery;
import org.springframework.stereotype.Repository;

import javax.swing.text.html.Option;
import java.util.Optional;

@Repository
public interface SetorRepository extends JpaRepository<Setor, Long> {

    @Override
    @NotNull
    Page<Setor> findAll(@NotNull Pageable pageable);


    @NativeQuery("SELECT * FROM setores WHERE nome_setor = :nome_setor LIMIT 1;")
    Optional<Setor> findByName(String nome_setor);
}
