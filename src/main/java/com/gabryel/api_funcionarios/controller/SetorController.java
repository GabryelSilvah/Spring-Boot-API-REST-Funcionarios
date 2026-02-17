package com.gabryel.api_funcionarios.controller;

import com.gabryel.api_funcionarios.config.ResponseJson;
import com.gabryel.api_funcionarios.dto.simple.SetorSimpleDTO;
import com.gabryel.api_funcionarios.model.Setor;
import com.gabryel.api_funcionarios.service.SetorService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/setores")
public class SetorController {

    @Autowired
    private SetorService setorService;

    @GetMapping("/listar/pagina/{id}")
    public ResponseEntity<ResponseJson> listAll(@PathVariable("id") int id_pagina) {
        List<SetorSimpleDTO> SetorsEncontrados = setorService.listAll(id_pagina).stream()
                .map(Setor::convertToDto)
                .toList();
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseJson(HttpStatus.OK, "Lista de todos os setors.", SetorsEncontrados, SetorsEncontrados.size()));
    }

    @GetMapping("/listar/id/{id}")
    public ResponseEntity<ResponseJson> listById(@PathVariable("id") Long id_setor) {
        SetorSimpleDTO SetorEncontrado = setorService.listById(id_setor).convertToDto();
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseJson(HttpStatus.OK, "Setor encontrado.", SetorEncontrado, 1));
    }

    @PostMapping("/registrar")
    public ResponseEntity<ResponseJson> register(@Valid @RequestBody SetorSimpleDTO setorSimpleDto_) {
        SetorSimpleDTO SetorRegistrado = setorService.save(setorSimpleDto_).convertToDto();
        return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseJson(HttpStatus.CREATED, "Setor registrado com sucesso!", SetorRegistrado,1));
    }

    @PutMapping("/alterar/{id}")
    public ResponseEntity<ResponseJson> change(@Valid @RequestBody SetorSimpleDTO setorSimpleDto_, @PathVariable("id") Long id_Setor) {
        SetorSimpleDTO SetorAlterado = setorService.update(setorSimpleDto_, id_Setor).convertToDto();
        return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseJson(HttpStatus.CREATED, "Setor alterado com sucesso!", SetorAlterado,1));
    }

    @DeleteMapping("/excluir/{id}")
    public ResponseEntity<ResponseJson> delete(@PathVariable("id") Long id_setor) {
        setorService.delete(id_setor);
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseJson(HttpStatus.OK, "Setor excluído com sucesso!"));
    }
}
