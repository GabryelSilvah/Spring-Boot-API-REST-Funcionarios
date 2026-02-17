package com.gabryel.api_funcionarios.service;

import com.gabryel.api_funcionarios.dto.input.EnderecoInputDTO;
import com.gabryel.api_funcionarios.exception.types.BadRequestException;
import com.gabryel.api_funcionarios.exception.types.NotFoundException;
import com.gabryel.api_funcionarios.exception.types.UnprocessableEntityException;
import com.gabryel.api_funcionarios.model.*;
import com.gabryel.api_funcionarios.repository.CidadeRepository;
import com.gabryel.api_funcionarios.repository.EnderecoRepository;
import com.gabryel.api_funcionarios.repository.FuncionarioRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class EnderecoServiceTest {

    private final static Long ID_ENDERECO = 1L;
    private final static int CEP_ENDERECO = 12345678;
    private final static Long FK_CIDADE = 1L;
    private final static String BAIRRO_ENDERECO = "Núcleo Bandeirante";
    private final static String COMPLEMENTO_ENDERECO = "Rual 12, conj z, casa 774";
    private final static Long FK_FUNCIONARIO = 1L;
    private Endereco enderecoModel;
    private Cidade cidadeModel;
    private Funcionario funcionarioModel;
    private EnderecoInputDTO enderecoDTO;
    @Mock
    private EnderecoRepository enderecoRepository;
    @Mock
    private CidadeRepository cidadeRepository;
    @Mock
    private FuncionarioRepository funcionarioRepository;
    @InjectMocks
    private EnderecoService enderecoService;

    //Carregar dados necessários para os testes
    private EnderecoServiceTest() {
        loadDate();
    }

    private void loadDate() {
        //EndereçoModel
        this.enderecoModel = new Endereco(
                ID_ENDERECO,
                CEP_ENDERECO,
                new Cidade(FK_CIDADE),
                BAIRRO_ENDERECO,
                COMPLEMENTO_ENDERECO,
                new Funcionario(FK_FUNCIONARIO)
        );

        //EndereçoDTO
        this.enderecoDTO = new EnderecoInputDTO(
                ID_ENDERECO,
                CEP_ENDERECO,
                FK_CIDADE,
                BAIRRO_ENDERECO,
                COMPLEMENTO_ENDERECO,
                FK_FUNCIONARIO
        );

        //CidadeModel
        this.cidadeModel = new Cidade(FK_CIDADE, "Brasília");

        //FuncionárioModel
        this.funcionarioModel = new Funcionario(
                1L,
                "Gabriel",
                false,
                new Cargo(1L),
                new Setor(1L),
                new Date().getTime() / 1000,
                new Date().getTime() / 1000
        );
    }

    //-----------------------------------------Validação--dados--retornados---------------------------------------------
    private void validation(Endereco endereco) {
        //Validar campos retornados pelo service de endereço
        Assertions.assertNotNull(endereco);
        Assertions.assertEquals(Endereco.class, endereco.getClass());
        Assertions.assertEquals(1, endereco.getId_endereco());
        Assertions.assertEquals(CEP_ENDERECO, endereco.getCep_endereco());
        Assertions.assertEquals(FK_CIDADE, endereco.getFk_cidade_endereco().getId_cidade());
        Assertions.assertEquals(BAIRRO_ENDERECO, endereco.getBairro_endereco());
        Assertions.assertEquals(COMPLEMENTO_ENDERECO, endereco.getComplemento_endereco());
        Assertions.assertEquals(FK_FUNCIONARIO, endereco.getFk_funcionario_endereco().getId_funcionario());
    }

    //---------------------------------------------ListAll--Success-----------------------------------------------------
    @Test
    @DisplayName("Buscar todos os endereços existentes")
    void listAllSuccessEnderecos() {
        //Criando dados de teste
        List<Endereco> novosEnderecos = new ArrayList<>();
        novosEnderecos.add(enderecoModel);
        Page<Endereco> page = new PageImpl<>(novosEnderecos, PageRequest.of(0, 10), 1);

        //Simulando dados
        Mockito.when(enderecoRepository.findAll(PageRequest.of(0, 10))).thenReturn(page);

        //Listando todos os endereços encontrados
        List<Endereco> enderecosEncontrados = enderecoService.listAll(1);

        //Teste
        validation(enderecosEncontrados.get(0));
        Assertions.assertEquals(1, enderecosEncontrados.size());
    }

    //---------------------------------------------ListByID--Success----------------------------------------------------
    @Test
    @DisplayName("Buscar um único endereço pelo ID")
    void listByIdSuccessEndereco() {
        //Simulando dados
        Mockito.when(enderecoRepository.findById(ID_ENDERECO)).thenReturn(Optional.of(enderecoModel));

        //Buscando endereço peço ID
        Endereco enderecoEncontrado = enderecoService.listById(ID_ENDERECO);

        //Teste
        validation(enderecoEncontrado);
    }

    //---------------------------------------------Save--Success--------------------------------------------------------
    @Test
    @DisplayName("Salvando novo endereço")
    void saveSuccessEndereco() {
        //Simulando dados
        Mockito.when(cidadeRepository.findById(FK_CIDADE)).thenReturn(Optional.of(cidadeModel));
        Mockito.when(funcionarioRepository.findById(FK_FUNCIONARIO)).thenReturn(Optional.of(funcionarioModel));
        Mockito.when(enderecoRepository.save(Mockito.any(Endereco.class))).thenReturn(enderecoModel);

        //Salvando novo endereço
        Endereco enderecoSalvo = enderecoService.save(enderecoDTO);

        //Validando teste
        validation(enderecoSalvo);
    }

    //---------------------------------------------Update--Success------------------------------------------------------
    @Test
    @DisplayName("Alterando dados do endereço pelo ID vinculado com funcionário")
    void updateSuccessEndereco() {
        //Simulando dados
        Mockito.when(enderecoRepository.findByID_FK_funcionario(ID_ENDERECO, FK_FUNCIONARIO)).thenReturn(Optional.of(enderecoModel));
        Mockito.when(cidadeRepository.findById(FK_CIDADE)).thenReturn(Optional.of(cidadeModel));
        Mockito.when(funcionarioRepository.findById(FK_FUNCIONARIO)).thenReturn(Optional.of(funcionarioModel));
        Mockito.when(enderecoRepository.save(enderecoModel)).thenReturn(enderecoModel);

        //Alterando endereço
        Endereco enderecoAlterado = enderecoService.update(enderecoDTO, ID_ENDERECO);

        //Validando teste
        validation(enderecoAlterado);
    }

    //---------------------------------------------Delete--Success------------------------------------------------------
    @Test
    @DisplayName("Excluindo endereço pelo ID")
    void deleteSuccessEndereco() {
        //Simulando dados
        Mockito.when(enderecoRepository.findById(ID_ENDERECO)).thenReturn(Optional.of(enderecoModel));
        Mockito.doNothing().when(enderecoRepository).delete(enderecoModel);

        //Excluindo endereço
        enderecoService.delete(ID_ENDERECO);

        //Validando teste
        Mockito.verify(enderecoRepository, Mockito.times(1)).delete(enderecoModel);
    }


    //Teste de exceptions
    //---------------------------------------------ListAll--NotFoundEndereços-------------------------------------------
    @Test
    @DisplayName("Exception notFound ao listar todos os endereços")
    void listAllNotFoundExceptionEnderecos() {
        //Capturando notFound do endereços não encontrados
        NotFoundException exception = Assertions.assertThrows(NotFoundException.class, () -> {
            enderecoService.listAll(1);
        });


        //Validando teste
        Assertions.assertEquals(NotFoundException.class, exception.getClass());
        Assertions.assertEquals("Nenhum endereço encontrado.", exception.getMessage());
    }

    //---------------------------------------------ListByID--NotFoundEndereço-------------------------------------------
    @Test
    @DisplayName("Exception notFound ao listar endereço por ID")
    void listByIdNotFoundExceptionEndereco() {

        //Capturando notFound do endereço não encontrado
        NotFoundException exception = Assertions.assertThrows(NotFoundException.class, () -> {
            enderecoService.listById(1L);
        });


        //Validando teste
        Assertions.assertEquals(NotFoundException.class, exception.getClass());
        Assertions.assertEquals("Endereço de ID (" + ID_ENDERECO + ") não foi encontrado.", exception.getMessage());
    }

    //---------------------------------------------Save--BadRequestCEP--------------------------------------------------
    @Test
    @DisplayName("Exception badRequest ao tentar salvar novo endereço com CEP no formato inválido")
    void saveBadRequestExceptionCEP() {

        //Capturando badRequest do CEP não encontrado
        BadRequestException exception = Assertions.assertThrows(BadRequestException.class, () -> {
            enderecoDTO.setCep_endereco(1234567);
            enderecoService.save(enderecoDTO);
        });


        //Validando teste
        Assertions.assertEquals(BadRequestException.class, exception.getClass());
        Assertions.assertEquals("O CEP informado é inválido, pois não possui 8 dígitos.", exception.getMessage());
    }

    //---------------------------------------------Save--BadRequestCidade-----------------------------------------------
    @Test
    @DisplayName("Exception notFound ao tentar salvar novo endereço com cidade inexistente")
    void saveNotFoundExceptionCidade() {
        //Capturando notFound da cidade não encontrada
        NotFoundException exception = Assertions.assertThrows(NotFoundException.class, () -> {
            enderecoService.save(enderecoDTO);
        });


        //Validando teste
        Assertions.assertEquals(NotFoundException.class, exception.getClass());
        Assertions.assertEquals("Cidade informada com ID (" + FK_CIDADE + ") não foi encontrada.", exception.getMessage());
    }

    //---------------------------------------------Save--NotFoundFuncionario--------------------------------------------
    @Test
    @DisplayName("Exception notFound ao tentar salvar novo endereço com funcionário informado inexistente")
    void saveNotFoundExceptionFuncionario() {
        //Simulando os dados
        Mockito.when(cidadeRepository.findById(FK_CIDADE)).thenReturn(Optional.of(cidadeModel));


        //Capturando notFound do funcionário não encontrado
        NotFoundException exception = Assertions.assertThrows(NotFoundException.class, () -> {
            enderecoService.save(enderecoDTO);
        });


        //Validando teste
        Assertions.assertEquals(NotFoundException.class, exception.getClass());
        Assertions.assertEquals("Funcionário informado com ID (" + FK_FUNCIONARIO + ") não foi encontrado.", exception.getMessage());
    }

    //---------------------------------------------Update--NotFoundEndereço---------------------------------------------
    @Test
    @DisplayName("Exception notFound ao tentar alterar endereço que não possue vínculo com funcionário informado")
    void updateNotFoundExceptionEndereco() {
        //Capturando notFound do endereço não encontrado
        NotFoundException exception = Assertions.assertThrows(NotFoundException.class, () -> {
            enderecoService.update(enderecoDTO, ID_ENDERECO);
        });


        //Validando teste
        Assertions.assertEquals(NotFoundException.class, exception.getClass());
        Assertions.assertEquals("O endereço de ID (" + ID_ENDERECO + ") não foi encontrado vinculado ao funcionário de ID(" + FK_FUNCIONARIO + ").", exception.getMessage());
    }

    //---------------------------------------------Update--BadRequestCEP------------------------------------------------
    @Test
    @DisplayName("Exception badRequest ao tentar alterar endereço com CEP inválido")
    void updateBadRequestExceptionCEP() {
        //Simulando os dados
        Mockito.when(enderecoRepository.findByID_FK_funcionario(ID_ENDERECO, FK_FUNCIONARIO)).thenReturn(Optional.of(enderecoModel));


        //Capturando badRequest do CEP inválido
        BadRequestException exception = Assertions.assertThrows(BadRequestException.class, () -> {
            enderecoDTO.setCep_endereco(1234567);
            enderecoService.update(enderecoDTO, ID_ENDERECO);
        });


        //Validando teste
        Assertions.assertEquals(BadRequestException.class, exception.getClass());
        Assertions.assertEquals("O CEP informado é inválido, pois não possui 8 dígitos.", exception.getMessage());
    }

    //---------------------------------------------Update--NotFoundCidade-----------------------------------------------
    @Test
    @DisplayName("Exception notFound ao tentar alterar endereço com cidade inexistente")
    void updateNotFoundExceptionCidade() {

        //Simulando os dados
        Mockito.when(enderecoRepository.findByID_FK_funcionario(ID_ENDERECO, FK_FUNCIONARIO)).thenReturn(Optional.of(enderecoModel));


        //Capturando notFound da cidade não encontrada
        NotFoundException exception = Assertions.assertThrows(NotFoundException.class, () -> {
            enderecoService.update(enderecoDTO, ID_ENDERECO);
        });


        //Validando teste
        Assertions.assertEquals(NotFoundException.class, exception.getClass());
        Assertions.assertEquals("Cidade informada com ID (" + FK_CIDADE + ") não foi encontrada.", exception.getMessage());
    }

    //---------------------------------------------Update--NotFoundFuncionario------------------------------------------
    @Test
    @DisplayName("Exception notFound ao tentar alterar endereço com funcinário informado inexistente")
    void updateNotFoundExceptionFuncionario() {

        //Simulando os dados
        Mockito.when(enderecoRepository.findByID_FK_funcionario(ID_ENDERECO, FK_FUNCIONARIO)).thenReturn(Optional.of(enderecoModel));
        Mockito.when(cidadeRepository.findById(FK_CIDADE)).thenReturn(Optional.of(cidadeModel));


        //Capturando notFound do funcionário não encontrado
        NotFoundException exception = Assertions.assertThrows(NotFoundException.class, () -> {
            enderecoService.update(enderecoDTO, ID_ENDERECO);
        });


        //Validando teste
        Assertions.assertEquals(NotFoundException.class, exception.getClass());
        Assertions.assertEquals("Funcionário informado com ID (" + FK_FUNCIONARIO + ") não foi encontrado.", exception.getMessage());
    }

    //---------------------------------------------Delete--NotFoundEndereco---------------------------------------------
    @Test
    @DisplayName("Exception notFound ao tentar excluir um endereço inexistente")
    void deleteNotFoundExceptionEndereco() {
        //Capturando notFound do endereço não encontrado
        NotFoundException exception = Assertions.assertThrows(NotFoundException.class, () -> {
            enderecoService.delete(ID_ENDERECO);
        });

        //Validando
        Assertions.assertEquals(NotFoundException.class, exception.getClass());
        Assertions.assertEquals("Endereço de ID (" + ID_ENDERECO + ") não foi encontrado.", exception.getMessage());
    }


    //---------------------------------------------Delete--UnprocessableEntityEndereço----------------------------------
    @Test
    @DisplayName("Exception UnprocessableEntity ao tentar excluir o único endereço vinculado ao funcionário")
    void deleteUnprocessableEntityExceptionEndereco() {
        //Simulando dados
        Mockito.when(enderecoRepository.findById(ID_ENDERECO)).thenReturn(Optional.of(enderecoModel));
        Mockito.when(enderecoRepository.findByFk_funcionario(ID_ENDERECO)).thenReturn(List.of(enderecoModel));


        //Capturando UnprocessableEntity do endereço único
        UnprocessableEntityException exception = Assertions.assertThrows(UnprocessableEntityException.class, () -> {
            enderecoService.delete(ID_ENDERECO);
        });


        //Validando
        Assertions.assertEquals(UnprocessableEntityException.class, exception.getClass());
        Assertions.assertEquals("Esse endereço não pode ser excluído, pois é o único vinculado ao funcionário de ID(" + ID_ENDERECO + "). " +
                "Tente adicionar outro endereço antes de excluir esse.", exception.getMessage());
    }

    //---------------------------------------------Delete--NotFoundFuncioná---------------------------------------------
    @Test
    @DisplayName("Exception notFound ao tentar excluir um endereço pelo funcionário inexistente")
    void deleteByFuncionarioNotFoundExceptionEndereco() {
        //Capturando notFound, endereço vinculado ao funcionário informado não encontrado
        NotFoundException exception = Assertions.assertThrows(NotFoundException.class, () -> {
            enderecoService.deleteByFk_funcionario(enderecoDTO.getId_funcionario_endereco());
        });

        //Validando
        Assertions.assertEquals(NotFoundException.class, exception.getClass());
        Assertions.assertEquals("Nenhum endereço vinculado ao funcionário de ID (" + enderecoDTO.getId_funcionario_endereco() + ") foi encontrado.", exception.getMessage());
    }


}