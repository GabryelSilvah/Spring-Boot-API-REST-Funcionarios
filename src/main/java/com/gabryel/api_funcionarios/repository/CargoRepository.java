package com.gabryel.api_funcionarios.repository;

import com.gabryel.api_funcionarios.model.Cargo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.NativeQuery;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CargoRepository extends JpaRepository<Cargo, Long> {

    @NativeQuery("SELECT * FROM cargos WHERE nome_cargo = :nome_cargo LIMIT 1;")
    Optional<Cargo> findByName(String nome_cargo);
}
