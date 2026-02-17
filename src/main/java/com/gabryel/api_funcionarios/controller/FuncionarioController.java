package com.gabryel.api_funcionarios.controller;

import com.gabryel.api_funcionarios.config.ResponseJson;
import com.gabryel.api_funcionarios.dto.input.FuncionarioInputDTO;
import com.gabryel.api_funcionarios.dto.output.FuncionarioOutputDTO;
import com.gabryel.api_funcionarios.model.Funcionario;
import com.gabryel.api_funcionarios.service.FuncionarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/funcionarios")
public class FuncionarioController {

    @Autowired
    private FuncionarioService funcionarioService;

    @GetMapping("/listar/pagina/{id}")
    public ResponseEntity<ResponseJson> listAll(@PathVariable("id") int id_pagina) {
        List<FuncionarioOutputDTO> funcionariosEncontrados = funcionarioService.listAll(id_pagina).stream().map(Funcionario::convertToDto_output).toList();
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseJson(HttpStatus.OK, "Lista de todos os funcionários.", funcionariosEncontrados, funcionariosEncontrados.size()));
    }

    @GetMapping("/listar/id/{id}")
    public ResponseEntity<ResponseJson> listById(@PathVariable("id") Long id_funcionario) {
        FuncionarioOutputDTO funcionarioEncontrado = funcionarioService.listById(id_funcionario).convertToDto_output();
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseJson(HttpStatus.OK, "Funcionário encontrado.", funcionarioEncontrado, 1));
    }

    @PostMapping("/registrar")
    public ResponseEntity<ResponseJson> register(@Valid @RequestBody FuncionarioInputDTO funcionarioDTOinput) {
        FuncionarioOutputDTO funcionarioRegistrado = funcionarioService.save(funcionarioDTOinput).convertToDto_output();
        return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseJson(HttpStatus.CREATED, "Funcionário registrado com sucesso!", funcionarioRegistrado, 1));
    }

    @PutMapping("/alterar/{id}")
    public ResponseEntity<ResponseJson> change(@Valid @RequestBody FuncionarioInputDTO funcionarioDTOinput, @PathVariable("id") Long id_funcionario) {
        FuncionarioOutputDTO funcionarioAlterado = funcionarioService.update(funcionarioDTOinput, id_funcionario).convertToDto_output();
        return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseJson(HttpStatus.CREATED, "Funcionário alterado com sucesso!", funcionarioAlterado, 1));
    }

    @PutMapping("/alterar/{id}/status/{valor_status}")
    public ResponseEntity<ResponseJson> changeStatus(@PathVariable("id") Long id_funcionario, @PathVariable("valor_status") String valor_status) {
        FuncionarioOutputDTO funcionarioStatusAlterado = funcionarioService.updateStatus(id_funcionario, Boolean.parseBoolean(valor_status)).convertToDto_output();
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseJson(HttpStatus.OK, "Status do funcionário alterado com sucesso!", funcionarioStatusAlterado, 1));
    }

    @DeleteMapping("/excluir/{id}")
    public ResponseEntity<ResponseJson> delete(@PathVariable("id") Long id_funcionario) {
        funcionarioService.delete(id_funcionario);
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseJson(HttpStatus.OK, "Funcionário excluído com sucesso!"));
    }


}
