package com.gabryel.api_funcionarios.controller;

import com.gabryel.api_funcionarios.config.ResponseJson;
import com.gabryel.api_funcionarios.dto.simple.CidadeSimpleDTO;
import com.gabryel.api_funcionarios.model.Cidade;
import com.gabryel.api_funcionarios.service.CidadeService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/cidades")
public class CidadesController {

    @Autowired
    private CidadeService cidadeService;

    @GetMapping("/listar/pagina/{id}")
    public ResponseEntity<ResponseJson> listAll(@PathVariable("id") int id_pagina) {
        List<CidadeSimpleDTO> cidadesEncontrados = cidadeService.listAll(id_pagina) .stream()
                .map(Cidade::convertToDto_output)
                .toList();
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseJson(HttpStatus.OK, "Lista de todas as cidades.", cidadesEncontrados, cidadesEncontrados.size()));
    }

    @GetMapping("/listar/id/{id}")
    public ResponseEntity<ResponseJson> listById(@PathVariable("id") Long id_cidade) {
        CidadeSimpleDTO cidadeEncontrado = cidadeService.listById(id_cidade).convertToDto_output();
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseJson(HttpStatus.OK, "Cidade encontrada.", cidadeEncontrado, 1));
    }

    @PostMapping("/registrar")
    public ResponseEntity<ResponseJson> register(@Valid @RequestBody CidadeSimpleDTO cidadeDtoSimple) {
        CidadeSimpleDTO cidadeRegistrado = cidadeService.save(cidadeDtoSimple).convertToDto_output();
        return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseJson(HttpStatus.CREATED, "Cidade registrada com sucesso!", cidadeRegistrado,1));
    }

    @PutMapping("/alterar/{id}")
    public ResponseEntity<ResponseJson> change(@Valid @RequestBody CidadeSimpleDTO cidadeSimpleDto_, @PathVariable("id") Long id_cidade) {
        CidadeSimpleDTO cidadeAlterado = cidadeService.update(cidadeSimpleDto_, id_cidade).convertToDto_output();
        return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseJson(HttpStatus.CREATED, "Cidade alterada com sucesso!", cidadeAlterado,1));
    }

    @DeleteMapping("/excluir/{id}")
    public ResponseEntity<ResponseJson> delete(@PathVariable("id") Long id_cidade) {
        cidadeService.delete(id_cidade);
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseJson(HttpStatus.OK, "Cidade excluída com sucesso!"));
    }

}
