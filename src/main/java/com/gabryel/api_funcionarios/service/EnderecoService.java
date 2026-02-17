package com.gabryel.api_funcionarios.service;

import com.gabryel.api_funcionarios.dto.input.EnderecoInputDTO;
import com.gabryel.api_funcionarios.exception.types.BadRequestException;
import com.gabryel.api_funcionarios.exception.types.NotFoundException;
import com.gabryel.api_funcionarios.exception.types.UnprocessableEntityException;
import com.gabryel.api_funcionarios.model.Cidade;
import com.gabryel.api_funcionarios.model.Endereco;
import com.gabryel.api_funcionarios.model.Funcionario;
import com.gabryel.api_funcionarios.repository.CidadeRepository;
import com.gabryel.api_funcionarios.repository.EnderecoRepository;
import com.gabryel.api_funcionarios.repository.FuncionarioRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EnderecoService {

    EnderecoRepository enderecoRepository;

    CidadeRepository cidadeRepository;

    FuncionarioRepository funcionarioRepository;

    public EnderecoService(EnderecoRepository enderecoRepository, CidadeRepository cidadeRepository, FuncionarioRepository funcionarioRepository) {
        this.enderecoRepository = enderecoRepository;
        this.cidadeRepository = cidadeRepository;
        this.funcionarioRepository = funcionarioRepository;
    }

    //---------------------------------------------ListAll--------------------------------------------------------------
    public List<Endereco> listAll(int page) {
        //Buscando e convertendo cada registro para DTO
        Page<Endereco> enderecoEncontrados = enderecoRepository.findAll(PageRequest.of(page - 1, 10));

        if (enderecoEncontrados == null) {
            throw new NotFoundException("Nenhum endereço encontrado.");
        }

        return enderecoEncontrados.getContent();
    }

    //---------------------------------------------ListById-------------------------------------------------------------
    public Endereco listById(Long id_endereco) {
        //Validando se foi encontrado registro com o ID informado
        Optional<Endereco> enderecoEncontrado = enderecoRepository.findById(id_endereco);
        if (enderecoEncontrado.isEmpty()) {
            throw new NotFoundException("Endereço de ID (" + id_endereco + ") não foi encontrado.");
        }

        return enderecoEncontrado.get();
    }

    //---------------------------------------------ListByFuncionario----------------------------------------------------
    public List<Endereco> listByFk_funcionario(Long fk_funcionario) {
        return enderecoRepository.findByFk_funcionario(fk_funcionario);
    }

    //---------------------------------------------Save-----------------------------------------------------------------
    public Endereco save(EnderecoInputDTO enderecoRecebido) {
        //Validando se CEP informado tem 8 dígitos
        if (String.valueOf(enderecoRecebido.getCep_endereco()).length() != 8) {
            throw new BadRequestException("O CEP informado é inválido, pois não possui 8 dígitos.");
        }


        //Validando se cidade informada existe
        Optional<Cidade> cidadeEncontrada = cidadeRepository.findById(enderecoRecebido.getId_cidade_endereco());
        if (cidadeEncontrada.isEmpty()) {
            throw new NotFoundException("Cidade informada com ID (" + enderecoRecebido.getId_cidade_endereco() + ") não foi encontrada.");
        }


        //Validando se funcionário informado existe
        Optional<Funcionario> funcionarioEncontrado = funcionarioRepository.findById(enderecoRecebido.getId_funcionario_endereco());
        if (funcionarioEncontrado.isEmpty()) {
            throw new NotFoundException("Funcionário informado com ID (" + enderecoRecebido.getId_funcionario_endereco() + ") não foi encontrado.");
        }


        //Salvando
        return enderecoRepository.save(enderecoRecebido.convertToModel());
    }

    //---------------------------------------------Update---------------------------------------------------------------
    public Endereco update(EnderecoInputDTO enderecoRecebido, Long id_endereco) {

        //Validando se o existe endereço informado tem vínculo ao funcionário que foi informado
        Optional<Endereco> enderecoEncontrado = enderecoRepository.findByID_FK_funcionario(id_endereco, enderecoRecebido.getId_funcionario_endereco());
        if (enderecoEncontrado.isEmpty()) {
            throw new NotFoundException("O endereço de ID (" + id_endereco + ") não foi encontrado vinculado ao funcionário de ID(" + enderecoRecebido.getId_funcionario_endereco() + ").");
        }


        //Validando se CEP informado tem 8 dígitos
        if (String.valueOf(enderecoRecebido.getCep_endereco()).length() != 8) {
            throw new BadRequestException("O CEP informado é inválido, pois não possui 8 dígitos.");
        }


        //Validando se cidade informada existe
        Optional<Cidade> cidadeEncontrada = cidadeRepository.findById(enderecoRecebido.getId_cidade_endereco());
        if (cidadeEncontrada.isEmpty()) {
            throw new NotFoundException("Cidade informada com ID (" + enderecoRecebido.getId_cidade_endereco() + ") não foi encontrada.");
        }


        //Validando se funcionário informado existe
        Optional<Funcionario> funcionarioEncontrado = funcionarioRepository.findById(enderecoRecebido.getId_funcionario_endereco());
        if (funcionarioEncontrado.isEmpty()) {
            throw new NotFoundException("Funcionário informado com ID (" + enderecoRecebido.getId_funcionario_endereco() + ") não foi encontrado.");
        }

        //Setando CEP
        enderecoEncontrado.get()
                .setCep_endereco(enderecoRecebido
                        .getCep_endereco());

        //Setando cidade
        enderecoEncontrado.get()
                .setFk_cidade_endereco(cidadeEncontrada.get());

        //Setando bairro
        enderecoEncontrado.get()
                .setBairro_endereco(enderecoRecebido
                        .getBairro_endereco());

        //Setando complemento
        enderecoEncontrado.get()
                .setComplemento_endereco(enderecoRecebido
                        .getComplemento_endereco());

        //Setando funcionário
        enderecoEncontrado.get()
                .setFk_funcionario_endereco(funcionarioEncontrado.get());


        //Alterando
        return enderecoRepository.save(enderecoEncontrado.get());
    }

    //---------------------------------------------Delete---------------------------------------------------------------
    public void delete(Long id_endereco) {
        //Buscando endereço
        Endereco enderecoEncotrado = listById(id_endereco);

        //Buscando outros endereços vinculados com funcionário do endereço já encontrado
        List<Endereco> enderecosVinculadosFuncionario = enderecoRepository
                .findByFk_funcionario(enderecoEncotrado
                        .getFk_funcionario_endereco()
                        .getId_funcionario());


        //Validando se esse é o único endereço vinculado ao funcionário
        if (enderecosVinculadosFuncionario.size() == 1) {
            throw new UnprocessableEntityException(
                    "Esse endereço não pode ser excluído, pois é o único vinculado ao funcionário de ID("
                            + enderecoEncotrado.getFk_funcionario_endereco().getId_funcionario() + "). " +
                            "Tente adicionar outro endereço antes de excluir esse.");
        }

        enderecoRepository.delete(enderecoEncotrado);
    }

    //---------------------------------------------DeleteByFuncionario--------------------------------------------------
    public void deleteByFk_funcionario(Long fk_funcionario) {
        //Deletar todos os endereços vinculados ao funcionário (recomendado usar quando o funcionário também for excluído)
        List<Endereco> enderecosEncontrados = enderecoRepository.findByFk_funcionario(fk_funcionario);
        if (enderecosEncontrados.isEmpty()) {
            throw new NotFoundException("Nenhum endereço vinculado ao funcionário de ID (" + fk_funcionario + ") foi encontrado.");
        }
        enderecoRepository.deleteByFk_funcionario(fk_funcionario);
    }
}
