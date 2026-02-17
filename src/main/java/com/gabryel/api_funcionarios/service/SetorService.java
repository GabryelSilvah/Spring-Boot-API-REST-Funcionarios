package com.gabryel.api_funcionarios.service;

import com.gabryel.api_funcionarios.dto.simple.SetorSimpleDTO;
import com.gabryel.api_funcionarios.exception.types.DuplicateNameException;
import com.gabryel.api_funcionarios.exception.types.NotFoundException;
import com.gabryel.api_funcionarios.model.Setor;
import com.gabryel.api_funcionarios.repository.SetorRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SetorService {

    SetorRepository setorRepository;

    public SetorService(SetorRepository setorRepository){
        this.setorRepository = setorRepository;
    }

    //---------------------------------------------ListAll--------------------------------------------------------------
    public List<Setor> listAll(int page) {
        //Buscando e convertendo cada registro para DTO
        Page<Setor> setoresEncontrados = setorRepository
                .findAll(PageRequest.of(page - 1, 10));


        if(setoresEncontrados == null){
            throw new NotFoundException("Nenhum setor encontrado.");
        }


        return setoresEncontrados
                .getContent();
    }

    //---------------------------------------------ListById-------------------------------------------------------------
    public Setor listById(Long id_setor) {
        //Validando se foi encontrado registro com o ID informado
        Optional<Setor> setorEncontrado = setorRepository.findById(id_setor);
        if (setorEncontrado.isEmpty()) {
            throw new NotFoundException("Setor de ID (" + id_setor + ") não foi encontrado.");
        }

        return setorEncontrado.get();
    }


    //---------------------------------------------Save-----------------------------------------------------------------
    public Setor save(SetorSimpleDTO setorRecebido) {
        //Validando se nome já existe
        Optional<Setor> setorEncontrado = setorRepository.findByName(setorRecebido.getNome_setor());
        if (setorEncontrado.isPresent()) {
            throw new DuplicateNameException("Setor de nome (" + setorRecebido.getNome_setor() + ") já existe na base de dados.");
        }

        //Salvando
        return setorRepository.save(setorRecebido.convertToModel());
    }

    //---------------------------------------------Update---------------------------------------------------------------
    public Setor update(SetorSimpleDTO setorRecebido, Long id_setor) {
        //Validando se existe registro com o ID informado
        Optional<Setor> setorEncontradoId = setorRepository.findById(id_setor);
        if (setorEncontradoId.isEmpty()) {
            throw new NotFoundException("Setor de ID (" + id_setor + ") não foi encontrado.");
        }


        //Validando se nome já existe e se não é do registro que será alterado
        Optional<Setor> setorEncontradoName = setorRepository.findByName(setorRecebido.getNome_setor());
        if (setorEncontradoName.isPresent() && !(setorEncontradoName.get().getId_setor().equals(id_setor))) {
            throw new DuplicateNameException("Setor de nome (" + setorRecebido.getNome_setor() + ") já existe na base de dados.");
        }


        //Realizando alterações
        setorEncontradoId.get().setNome_setor(setorRecebido.getNome_setor());

        //Alterando
        return setorRepository.save(setorEncontradoId.get());
    }

    //---------------------------------------------Delete---------------------------------------------------------------
    public void delete(Long id_setor) {
        //Usando método listById para buscar e validar registro com ID informado
        Setor cargoEncotrado = listById(id_setor);
        setorRepository.delete(cargoEncotrado);
    }
}
