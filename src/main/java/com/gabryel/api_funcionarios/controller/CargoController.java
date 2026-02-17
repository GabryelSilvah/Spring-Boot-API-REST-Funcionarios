package com.gabryel.api_funcionarios.controller;

import com.gabryel.api_funcionarios.config.ResponseJson;
import com.gabryel.api_funcionarios.dto.simple.CargoSimpleDTO;
import com.gabryel.api_funcionarios.model.Cargo;
import com.gabryel.api_funcionarios.service.CargoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/cargos")
public class CargoController {

    @Autowired
    private CargoService cargoService;

    @GetMapping("/listar")
    public ResponseEntity<ResponseJson> listAll() {
        List<CargoSimpleDTO> cargosEncontrados = cargoService.listAll().stream().map(Cargo::convertToDto).toList();
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseJson(HttpStatus.OK, "Lista de todos os cargos.", cargosEncontrados, cargosEncontrados.size()));
    }

    @GetMapping("/listar/id/{id}")
    public ResponseEntity<ResponseJson> listById(@PathVariable("id") Long id_cargo) {
        CargoSimpleDTO cargoEncontrado = cargoService.listById(id_cargo).convertToDto();
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseJson(HttpStatus.OK, "Cargo encontrado.", cargoEncontrado, 1));
    }

    @PostMapping("/registrar")
    public ResponseEntity<ResponseJson> register(@Valid @RequestBody CargoSimpleDTO cargoDtoSimple) {
        CargoSimpleDTO cargoRegistrado = cargoService.save(cargoDtoSimple).convertToDto();
        return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseJson(HttpStatus.CREATED, "Cargo registrado com sucesso!", cargoRegistrado,1));
    }

    @PutMapping("/alterar/{id}")
    public ResponseEntity<ResponseJson> change(@Valid @RequestBody CargoSimpleDTO cargoDtoSimple, @PathVariable("id") Long id_cargo) {
        CargoSimpleDTO cargoAlterado = cargoService.update(cargoDtoSimple, id_cargo).convertToDto();
        return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseJson(HttpStatus.CREATED, "Cargo alterado com sucesso!", cargoAlterado,1));
    }

    @DeleteMapping("/excluir/{id}")
    public ResponseEntity<ResponseJson> delete(@PathVariable("id") Long id_cargo) {
        cargoService.delete(id_cargo);
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseJson(HttpStatus.OK, "Cargo excluído com sucesso!"));
    }

}
