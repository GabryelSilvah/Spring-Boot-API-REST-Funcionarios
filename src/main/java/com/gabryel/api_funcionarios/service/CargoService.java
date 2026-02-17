package com.gabryel.api_funcionarios.service;

import com.gabryel.api_funcionarios.dto.simple.CargoSimpleDTO;
import com.gabryel.api_funcionarios.exception.types.DuplicateNameException;
import com.gabryel.api_funcionarios.exception.types.NotFoundException;
import com.gabryel.api_funcionarios.model.Cargo;
import com.gabryel.api_funcionarios.repository.CargoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CargoService {


    private final CargoRepository cargoRepository;

    public CargoService(CargoRepository cargoRepository) {
        this.cargoRepository = cargoRepository;
    }

    //---------------------------------------------ListAll--------------------------------------------------------------
    public List<Cargo> listAll() {
        //Buscando e convertendo cada registro para DTO
        List<Cargo> cargosEncontrados = cargoRepository.findAll();
        if (cargosEncontrados.isEmpty()) {
            throw new NotFoundException("Nenhum cargo encontrado.");
        }

        return cargosEncontrados;
    }

    //---------------------------------------------ListByID-------------------------------------------------------------
    public Cargo listById(Long id_cargo) {
        //Validando se foi encontrado registro com o ID informado
        Optional<Cargo> cargoEncontrado = cargoRepository.findById(id_cargo);
        if (cargoEncontrado.isEmpty()) {
            throw new NotFoundException("Cargo de ID (" + id_cargo + ") não foi encontrado.");
        }

        return cargoEncontrado.get();
    }

    //---------------------------------------------Save-----------------------------------------------------------------
    public Cargo save(CargoSimpleDTO cargoRecebido) {
        //Validando se nome já existe
        Optional<Cargo> cargoEncontrado = cargoRepository.findByName(cargoRecebido.getNome_cargo());
        if (cargoEncontrado.isPresent()) {
            throw new DuplicateNameException("Cargo de nome (" + cargoRecebido.getNome_cargo() + ") já existe na base de dados.");
        }

        //Salvando
        return cargoRepository.save(cargoRecebido.convertToModel());
    }

    //---------------------------------------------Update---------------------------------------------------------------
    public Cargo update(CargoSimpleDTO cargoRecebido, Long id_cargo) {


        //Validando se existe registro com o ID informado
        Optional<Cargo> cargoEncontradoId = cargoRepository.findById(id_cargo);
        if (cargoEncontradoId.isEmpty()) {
            throw new NotFoundException("Cargo de ID (" + id_cargo + ") não foi encontrado.");
        }


        //Validando se nome já existe e se não é do registro que será alterado
        Optional<Cargo> cargoEncontradoName = cargoRepository.findByName(cargoRecebido.getNome_cargo());
        if (cargoEncontradoName.isPresent() && !(cargoEncontradoName.get().getId_cargo().equals(id_cargo))) {
            throw new DuplicateNameException("Cargo de nome (" + cargoRecebido.getNome_cargo() + ") já existe na base de dados.");
        }


        //Realizando alterações
        cargoEncontradoId.get().setNome_cargo(cargoRecebido.getNome_cargo());

        //Alterando
        return cargoRepository.save(cargoEncontradoId.get());
    }

    //---------------------------------------------Delete---------------------------------------------------------------
    public void delete(Long id_cargo) {
        //Usando método listById para buscar e validar registro com ID informado
        Cargo cargoEncotrado = listById(id_cargo);
        cargoRepository.delete(cargoEncotrado);
    }
}
