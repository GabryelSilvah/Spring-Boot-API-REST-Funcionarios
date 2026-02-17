package com.gabryel.api_funcionarios.service;

import com.gabryel.api_funcionarios.dto.simple.CidadeSimpleDTO;
import com.gabryel.api_funcionarios.exception.types.DuplicateNameException;
import com.gabryel.api_funcionarios.exception.types.NotFoundException;
import com.gabryel.api_funcionarios.model.Cidade;
import com.gabryel.api_funcionarios.repository.CidadeRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CidadeService {

    CidadeRepository cidadeRepository;
    public CidadeService(CidadeRepository cidadeRepository){
        this.cidadeRepository = cidadeRepository;
    }


    public List<Cidade> listAll(int page) {
        //Buscando e convertendo cada registro para DTO
        Page<Cidade> cidadesEncontradas = cidadeRepository
                .findAll(PageRequest.of(page - 1, 10));


        if(cidadesEncontradas == null){
            throw new NotFoundException("Nenhuma cidade encontrada.");
        }


        return cidadesEncontradas.getContent();
    }

    public Cidade listById(Long id_cidade) {
        //Validando se foi encontrado registro com o ID informado
        Optional<Cidade> cidadeEncontrado = cidadeRepository.findById(id_cidade);
        if (cidadeEncontrado.isEmpty()) {
            throw new NotFoundException("Cidade de ID (" + id_cidade + ") não foi encontrada.");
        }

        return cidadeEncontrado.get();
    }

    public Cidade listByName(String nome_cidade) {
        //Validando se foi encontrado registro com o nome informado
        Optional<Cidade> cidadeEncontrado = cidadeRepository.findByName(nome_cidade);
        if (cidadeEncontrado.isEmpty()) {
            throw new NotFoundException("Cidade de nome (" + nome_cidade + ") não foi encontrada.");
        }

        return cidadeEncontrado.get();
    }

    public Cidade save(CidadeSimpleDTO cidadeRecebida) {
        //Validando se nome já existe
        Optional<Cidade> cidadeEncontrado = cidadeRepository.findByName(cidadeRecebida.getNome_cidade());
        if (cidadeEncontrado.isPresent()) {
            throw new DuplicateNameException("Cidade de nome (" + cidadeRecebida.getNome_cidade() + ") já existe na base de dados.");
        }

        //Salvando
        return cidadeRepository.save(cidadeRecebida.convertToModel());
    }

    public Cidade update(CidadeSimpleDTO cidadeRecebida, Long id_cidade) {


        //Validando se existe registro com o ID informado
        Optional<Cidade> cidadeEncontradoId = cidadeRepository.findById(id_cidade);
        if (cidadeEncontradoId.isEmpty()) {
            throw new NotFoundException("Cidade de ID (" + id_cidade + ") não foi encontrado.");
        }


        //Validando se nome já existe e se não é do registro que será alterado
        Optional<Cidade> cidadeEncontradoName = cidadeRepository.findByName(cidadeRecebida.getNome_cidade());
        if (cidadeEncontradoName.isPresent() && !(cidadeEncontradoName.get().getId_cidade().equals(id_cidade))) {
            throw new DuplicateNameException("Cidade de nome (" + cidadeRecebida.getNome_cidade() + ") já existe na base de dados.");
        }


        //Realizando alterações
        cidadeEncontradoId.get().setNome_cidade(cidadeRecebida.getNome_cidade());

        //Alterando
        return cidadeRepository.save(cidadeEncontradoId.get());
    }

    public void delete(Long id_cidade) {
        //Usando método listById para buscar e validar registro com ID informado
        Cidade cidadeEncotrado = listById(id_cidade);
        cidadeRepository.delete(cidadeEncotrado);
    }
}
