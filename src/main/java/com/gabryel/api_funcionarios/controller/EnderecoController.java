package com.gabryel.api_funcionarios.controller;

import com.gabryel.api_funcionarios.config.ResponseJson;
import com.gabryel.api_funcionarios.dto.input.EnderecoInputDTO;
import com.gabryel.api_funcionarios.dto.output.EnderecoOutputDTO;
import com.gabryel.api_funcionarios.model.Endereco;
import com.gabryel.api_funcionarios.service.EnderecoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/enderecos")
public class EnderecoController {



    @Autowired
    private EnderecoService enderecoService;

    @GetMapping("/listar/pagina/{id}")
    public ResponseEntity<ResponseJson> listAll(@PathVariable("id") int id_pagina) {
        List<EnderecoOutputDTO> enderecosEncontrados = enderecoService
                .listAll(id_pagina).stream()
                .map(Endereco::convertToOutputDTO)
                .toList();

        return ResponseEntity.status(HttpStatus.OK).body(new ResponseJson(HttpStatus.OK, "Lista de todos os endereços.", enderecosEncontrados, enderecosEncontrados.size()));
    }

    @GetMapping("/listar/id/{id}")
    public ResponseEntity<ResponseJson> listById(@PathVariable("id") Long id_endereco) {
        EnderecoOutputDTO enderecoEncontrado = enderecoService.listById(id_endereco).convertToOutputDTO();
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseJson(HttpStatus.OK, "Endereço encontrado.", enderecoEncontrado, 1));
    }

    @PostMapping("/registrar")
    public ResponseEntity<ResponseJson> register(@Valid @RequestBody EnderecoInputDTO enderecoDTOinput) {
        EnderecoOutputDTO enderecoRegistrado = enderecoService.save(enderecoDTOinput).convertToOutputDTO();
        return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseJson(HttpStatus.CREATED, "Endereço registrado com sucesso!", enderecoRegistrado,1));
    }

    @PutMapping("/alterar/{id}")
    public ResponseEntity<ResponseJson> change(@Valid @RequestBody EnderecoInputDTO enderecoDTOinput, @PathVariable("id") Long id_endereco) {
        EnderecoOutputDTO enderecoAlterado = enderecoService.update(enderecoDTOinput, id_endereco).convertToOutputDTO();
        return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseJson(HttpStatus.CREATED, "Endereço alterado com sucesso!", enderecoAlterado,1));
    }

    @DeleteMapping("/excluir/{id}")
    public ResponseEntity<ResponseJson> delete(@PathVariable("id") Long id_endereco) {
        enderecoService.delete(id_endereco);
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseJson(HttpStatus.OK, "Endereço excluído com sucesso!"));
    }

}
