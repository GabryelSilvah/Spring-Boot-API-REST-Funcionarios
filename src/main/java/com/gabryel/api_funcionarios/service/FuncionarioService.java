package com.gabryel.api_funcionarios.service;

import com.gabryel.api_funcionarios.dto.input.EnderecoInputDTO;
import com.gabryel.api_funcionarios.dto.input.FuncionarioInputDTO;
import com.gabryel.api_funcionarios.exception.types.BadRequestException;
import com.gabryel.api_funcionarios.exception.types.DuplicateNameException;
import com.gabryel.api_funcionarios.exception.types.NotFoundException;
import com.gabryel.api_funcionarios.model.Cargo;
import com.gabryel.api_funcionarios.model.Funcionario;
import com.gabryel.api_funcionarios.model.Setor;
import com.gabryel.api_funcionarios.repository.CargoRepository;
import com.gabryel.api_funcionarios.repository.FuncionarioRepository;
import com.gabryel.api_funcionarios.repository.SetorRepository;
import jakarta.transaction.Transactional;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;


import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class FuncionarioService {


    FuncionarioRepository funcionarioRepository;
    CargoRepository cargoRepository;
    SetorRepository setorRepository;
    EnderecoService enderecoService;

    public FuncionarioService(
            FuncionarioRepository funcionarioRepository,
            CargoRepository cargoRepository,
            SetorRepository setorRepository,
            EnderecoService enderecoService
    ) {
        this.funcionarioRepository = funcionarioRepository;
        this.cargoRepository = cargoRepository;
        this.setorRepository = setorRepository;
        this.enderecoService = enderecoService;
    }

    //---------------------------------------------ListAll--------------------------------------------------------------
    public List<Funcionario> listAll(int page) {
        //Buscando e convertendo cada registro para DTO
        Page<Funcionario> funcionariosEncontrados = funcionarioRepository.findAll(PageRequest.of(page - 1, 10));

        if (funcionariosEncontrados == null) {
            throw new NotFoundException("Nenhum funcionário encontrado.");
        }

        return funcionariosEncontrados.getContent();
    }

    //---------------------------------------------ListById-------------------------------------------------------------
    public Funcionario listById(Long id_funcionario) {
        //Validando se foi encontrado registro com o ID informado
        Optional<Funcionario> funcionarioEncontrado = funcionarioRepository.findById(id_funcionario);
        if (funcionarioEncontrado.isEmpty()) {
            throw new NotFoundException("Funcionário de ID (" + id_funcionario + ") não foi encontrado.");
        }

        return funcionarioEncontrado.get();
    }

    //---------------------------------------------Save-----------------------------------------------------------------
    @Transactional
    public Funcionario save(FuncionarioInputDTO funcionarioRecebido) {

        //Validando se nome já existe na base de dados
        //Desconsiderando que possam existir funcionários legítimos com os mesmos nomes
        Optional<Funcionario> funcionarioEncontrado = funcionarioRepository.findByName(funcionarioRecebido.getNome_funcionario());
        if (funcionarioEncontrado.isPresent()) {
            throw new DuplicateNameException("Funcionário de nome (" + funcionarioRecebido.getNome_funcionario() + ") já existe na base de dados.");
        }

        //Valida se cargo existe
        Optional<Cargo> cargoEncontrado = cargoRepository.findById(funcionarioRecebido.getId_cargo_funcionario());
        if (cargoEncontrado.isEmpty()) {
            throw new NotFoundException("Cargo informado com ID (" + funcionarioRecebido.getId_cargo_funcionario() + ") não foi encontrado.");
        }

        //Validando se setor existe
        Optional<Setor> setorEncontrado = setorRepository.findById(funcionarioRecebido.getId_setor_funcionario());
        if (setorEncontrado.isEmpty()) {
            throw new NotFoundException("Setor informado com ID (" + funcionarioRecebido.getId_cargo_funcionario() + ") não foi encontrado.");
        }


        //Setando datas
        funcionarioRecebido.setDate_create_funcionario(new Date().getTime() / 1000L);
        funcionarioRecebido.setDate_update_funcionario(new Date().getTime() / 1000L);


        //Salvando o funcionário
        Funcionario funcionarioSalvo = funcionarioRepository.save(funcionarioRecebido.convertToModel());


        //Validando se algum endereço foi informado
        if (funcionarioRecebido.getEnderecos_funcionario().isEmpty()) {
            throw new BadRequestException("Nenhum endereço foi adicionado");
        }


        //Pegando cada endereço e enviando para o service validar e salvar
        for (EnderecoInputDTO endereco : funcionarioRecebido.getEnderecos_funcionario()) {
            endereco.setId_funcionario_endereco(funcionarioSalvo.getId_funcionario());
            enderecoService.save(endereco);
        }


        //Buscando endereços relacionados ao funcionários salvos
        funcionarioSalvo.setEnderecos_funcionario(enderecoService.listByFk_funcionario(funcionarioSalvo.getId_funcionario()));

        return funcionarioSalvo;
    }

    //---------------------------------------------Update---------------------------------------------------------------
    @Transactional
    public Funcionario update(FuncionarioInputDTO funcionarioRecebido, Long id_funcionario) {

        //Validando se ID de funcionário informado existe
        Optional<Funcionario> funcionarioEncontradoId = funcionarioRepository.findById(id_funcionario);
        if (funcionarioEncontradoId.isEmpty()) {
            throw new NotFoundException("Funcionário de ID (" + id_funcionario + ") não foi encontrado.");
        }


        //Validando se nome já existe na base de dados
        //Desconsiderando que possam existir funcionários legítimos com os mesmos nomes
        Optional<Funcionario> funcionarioEncontradoNome = funcionarioRepository.findByName(funcionarioRecebido.getNome_funcionario());
        if (funcionarioEncontradoNome.isPresent() && !(funcionarioEncontradoNome.get().getId_funcionario().equals(id_funcionario))) {
            throw new DuplicateNameException("Funcionário de nome (" + funcionarioRecebido.getNome_funcionario() + ") já existe na base de dados.");
        }

        //Valida se cargo existe
        Optional<Cargo> cargoEncontrado = cargoRepository.findById(funcionarioRecebido.getId_cargo_funcionario());
        if (cargoEncontrado.isEmpty()) {
            throw new NotFoundException("Cargo informado com ID (" + funcionarioRecebido.getId_cargo_funcionario() + ") não foi encontrado.");
        }

        //Validando se setor existe
        Optional<Setor> setorEncontrado = setorRepository.findById(funcionarioRecebido.getId_setor_funcionario());
        if (setorEncontrado.isEmpty()) {
            throw new NotFoundException("Setor informado com ID (" + funcionarioRecebido.getId_cargo_funcionario() + ") não foi encontrado.");
        }

        //Validando se algum endereço foi informado
        if (funcionarioRecebido.getEnderecos_funcionario().isEmpty()) {
            throw new BadRequestException("Nenhum endereço foi adicionado");
        }

        //Setando nome funcionário
        funcionarioEncontradoId.get()
                .setNome_funcionario(funcionarioRecebido
                        .getNome_funcionario());

        //Setando cargo funcionário
        funcionarioEncontradoId.get()
                .setFk_cargo_funcionario(cargoEncontrado.get());


        //Setando setor funcionário
        funcionarioEncontradoId.get()
                .setFk_setor_funcionario(setorEncontrado.get());


        //Setando data original de criação
        funcionarioEncontradoId.get()
                .setDate_create_funcionario(funcionarioEncontradoId.get()
                        .getDate_create_funcionario());//Testar

        //Setando nova data de alteração
        funcionarioEncontradoId.get()
                .setDate_update_funcionario(new Date().getTime() / 1000L);


        //Salvando o funcionário
        Funcionario funcionarioAlterado = funcionarioRepository.save(funcionarioEncontradoId.get());


        //Pegando cada endereço e enviando para o service validar e salvar
        for (EnderecoInputDTO endereco : funcionarioRecebido.getEnderecos_funcionario()) {
            endereco.setId_funcionario_endereco(funcionarioAlterado.getId_funcionario());
            enderecoService.update(endereco, endereco.getId_endereco());
        }

        return funcionarioAlterado;
    }

    //---------------------------------------------Update--Status-------------------------------------------------------
    @CacheEvict(value = "funcionario", allEntries = true)
    public Funcionario updateStatus(Long id_funcionario, Boolean status_funcionario) {
        //Buscando funcionário, com método que já valida se foi encontrado
        Funcionario funcionarioEncotrado = listById(id_funcionario);

        //Alterando estatus
        funcionarioRepository.changeStatus(funcionarioEncotrado.getId_funcionario(), status_funcionario);
        return listById(id_funcionario);
    }

    //---------------------------------------------Delete---------------------------------------------------------------
    @Transactional
    public void delete(Long id_funcionario) {
        //Usando método listById para buscar e validar registro com ID informado
        Funcionario funcionarioEncotrado = listById(id_funcionario);
        enderecoService.deleteByFk_funcionario(funcionarioEncotrado.getId_funcionario());
        funcionarioRepository.delete(funcionarioEncotrado);
    }
}
