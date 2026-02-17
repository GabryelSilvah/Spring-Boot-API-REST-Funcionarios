package com.gabryel.api_funcionarios.service;

import com.gabryel.api_funcionarios.dto.input.EnderecoInputDTO;
import com.gabryel.api_funcionarios.dto.input.FuncionarioInputDTO;
import com.gabryel.api_funcionarios.exception.types.BadRequestException;
import com.gabryel.api_funcionarios.exception.types.DuplicateNameException;
import com.gabryel.api_funcionarios.exception.types.NotFoundException;
import com.gabryel.api_funcionarios.model.*;
import com.gabryel.api_funcionarios.repository.*;
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
class FuncionarioServiceTest {
    private final static Long ID_FUNCIONARIO = 1L;
    private final static String NOME_FUNCIONARIO = "Alice";
    private final static boolean ATIVO_FUNCIONARIO = false;
    private final static Long FK_CARGO = 1L;
    private final static Long FK_SETOR = 1L;
    private Funcionario funcionarioModel;
    private FuncionarioInputDTO funcionarioDTO;
    private Cargo cargoModel;
    private Setor setorModel;
    private Endereco enderecoModel;
    private EnderecoInputDTO enderecoDTO;
    @Mock
    private FuncionarioRepository funcionarioRepository;
    @Mock
    private CargoRepository cargoRepository;
    @Mock
    private SetorRepository setorRepository;
    @Mock
    private EnderecoService enderecoService;
    @InjectMocks
    private FuncionarioService funcionarioService;

    //-------------------Carregar dados necessários para os testes------------------------------------------------------
    private FuncionarioServiceTest() {
        loadDate();
    }


    private void loadDate() {

        //.............................................Endereço.........................................................
        //EndereçoModel
        this.enderecoModel = new Endereco(
                1L,
                12345678,
                new Cidade(1L),
                "Núcleo Bandeirante",
                "Rua z, conj 05, casa 775",
                null
        );

        //EndereçoDTO
        this.enderecoDTO = new EnderecoInputDTO(
                1L,
                12345678,
                1L,
                "Núcleo Bandeirante",
                "Rua z, conj 05, casa 775",
                null
        );

        List<EnderecoInputDTO> enderecosDTO = new ArrayList<>();
        enderecosDTO.add(this.enderecoDTO);

        //.............................................Funcionário......................................................
        //FuncionarioDto
        this.funcionarioDTO = new FuncionarioInputDTO(
                ID_FUNCIONARIO,
                NOME_FUNCIONARIO,
                ATIVO_FUNCIONARIO,
                FK_CARGO,
                FK_SETOR,
                enderecosDTO
        );


        //FuncionarioModel
        this.funcionarioModel = new Funcionario(
                ID_FUNCIONARIO,
                NOME_FUNCIONARIO,
                ATIVO_FUNCIONARIO,
                new Cargo(FK_CARGO),
                new Setor(FK_SETOR),
                new Date().getTime() / 1000,
                new Date().getTime() / 1000
        );

        List<Endereco> enderecosModel = new ArrayList<>();
        enderecosModel.add(this.enderecoModel);

        this.funcionarioModel.setEnderecos_funcionario(enderecosModel);

        //..........................................Outros..............................................................
        //CargoModel
        this.cargoModel = new Cargo(1L, "Técnico");

        //SetorModel
        this.setorModel = new Setor(1L, "Pesquisa e Desenvolvimento");
    }

    //-----------------------------------------Validação--dados--retornados---------------------------------------------
    private void validation(Funcionario funcionario) {
        //...............................Validando--dados--de--funcionário..............................................
        Assertions.assertNotNull(funcionario);
        Assertions.assertEquals(Funcionario.class, funcionario.getClass());
        Assertions.assertEquals(ID_FUNCIONARIO, funcionario.getId_funcionario());
        Assertions.assertEquals(NOME_FUNCIONARIO, funcionario.getNome_funcionario());
        Assertions.assertEquals(ATIVO_FUNCIONARIO, funcionario.isAtivo_funcionario());
        Assertions.assertEquals(FK_CARGO, funcionario.getFk_cargo_funcionario().getId_cargo());
        Assertions.assertEquals(FK_SETOR, funcionario.getFk_setor_funcionario().getId_setor());


        //...............................Validando--dados--de--endereço.................................................
        Assertions.assertEquals(enderecoModel.getId_endereco(), funcionario.getEnderecos_funcionario().get(0).getId_endereco());
        Assertions.assertEquals(enderecoModel.getCep_endereco(), funcionario.getEnderecos_funcionario().get(0).getCep_endereco());
        Assertions.assertEquals(enderecoModel.getFk_cidade_endereco(), funcionario.getEnderecos_funcionario().get(0).getFk_cidade_endereco());
        Assertions.assertEquals(enderecoModel.getBairro_endereco(), funcionario.getEnderecos_funcionario().get(0).getBairro_endereco());
        Assertions.assertEquals(enderecoModel.getComplemento_endereco(), funcionario.getEnderecos_funcionario().get(0).getComplemento_endereco());
        Assertions.assertEquals(enderecoModel.getFk_funcionario_endereco(), funcionario.getEnderecos_funcionario().get(0).getFk_funcionario_endereco());
    }

    //--------------------------------------------ListAll--Success------------------------------------------------------
    @Test
    @DisplayName("Buscar todos os funcionários existentes")
    void listAllSuccess() {
        //Criando dados de teste
        List<Funcionario> novosFuncionarios = new ArrayList<>();
        novosFuncionarios.add(funcionarioModel);
        Page<Funcionario> page = new PageImpl<>(novosFuncionarios, PageRequest.of(0, 10), 1);

        //Simulando dados
        Mockito.when(funcionarioRepository.findAll(PageRequest.of(0, 10))).thenReturn(page);

        //Listando
        List<Funcionario> funcionariosEncontrados = funcionarioService.listAll(1);

        //Teste
        validation(funcionariosEncontrados.get(0));
        Assertions.assertEquals(1, funcionariosEncontrados.size());
    }

    //---------------------------------------------ListById--Success----------------------------------------------------
    @Test
    @DisplayName("Buscar um único funcionário pelo ID")
    void listByIdSuccess() {
        //Simulando dados
        Mockito.when(funcionarioRepository.findById(ID_FUNCIONARIO)).thenReturn(Optional.of(funcionarioModel));

        //Buscando
        Funcionario funcionarioEncontrado = funcionarioService.listById(ID_FUNCIONARIO);

        //Teste
        validation(funcionarioEncontrado);
    }

    //---------------------------------------------Save--Success--------------------------------------------------------
    @Test
    @DisplayName("Salvando novo funcionário")
    void saveSuccess() {
        //Simulando dados (Somente dependências diretas de funcionarioService)
        Mockito.when(cargoRepository.findById(FK_CARGO)).thenReturn(Optional.of(cargoModel));
        Mockito.when(setorRepository.findById(FK_SETOR)).thenReturn(Optional.of(setorModel));
        Mockito.when(funcionarioRepository.save(Mockito.any(Funcionario.class))).thenReturn(funcionarioModel);

        //Simulando dados (somente dependências diretas de enderecoService)
        Mockito.when(enderecoService.save(enderecoDTO)).thenReturn(enderecoModel);
        List<Endereco> enderecosViculadosFuncionario = new ArrayList<>();
        enderecosViculadosFuncionario.add(enderecoModel);
        Mockito.when(enderecoService.listByFk_funcionario(ID_FUNCIONARIO)).thenReturn(enderecosViculadosFuncionario);


        //Salvando
        Funcionario funcionarioSalvo = funcionarioService.save(funcionarioDTO);

        //Validando teste
        Mockito.verify(enderecoService, Mockito.times(1)).save(enderecoDTO);
        Mockito.verify(enderecoService, Mockito.times(1)).listByFk_funcionario(funcionarioSalvo.getId_funcionario());
        validation(funcionarioSalvo);
    }

    //---------------------------------------------Update--Success------------------------------------------------------
    @Test
    @DisplayName("Alterando dados do funcionário pelo ID")
    void updateSuccess() {
        //Simulando dados (Somente dependências diretas de funcionarioService)
        Mockito.when(funcionarioRepository.findById(ID_FUNCIONARIO)).thenReturn(Optional.of(funcionarioModel));
        Mockito.when(cargoRepository.findById(FK_CARGO)).thenReturn(Optional.of(cargoModel));
        Mockito.when(setorRepository.findById(FK_SETOR)).thenReturn(Optional.of(setorModel));
        Mockito.when(funcionarioRepository.save(funcionarioModel)).thenReturn(funcionarioModel);


        //Simulando dados (somente dependências diretas de enderecoService)
        Mockito.when(enderecoService.update(enderecoDTO, enderecoDTO.getId_endereco())).thenReturn(enderecoModel);

        //Alterando
        Funcionario funcionarioAlterado = funcionarioService.update(funcionarioDTO, ID_FUNCIONARIO);

        //Validando teste
        Mockito.verify(enderecoService, Mockito.times(1)).update(enderecoDTO, 1L);
        validation(funcionarioAlterado);
    }

    //---------------------------------------------Update--Status--Success----------------------------------------------
    @Test
    @DisplayName("Alterando status do funcionário pelo ID")
    void updateStatusSuccess() {
        //Simulando dados
        Mockito.when(funcionarioRepository.findById(ID_FUNCIONARIO)).thenReturn(Optional.of(funcionarioModel));
        Mockito.doNothing().when(funcionarioRepository).changeStatus(ID_FUNCIONARIO, true);

        //Alterando
        Funcionario funcionarioAlterado = funcionarioService.updateStatus(ID_FUNCIONARIO, true);

        //Validando teste
        Mockito.verify(funcionarioRepository, Mockito.times(1)).changeStatus(ID_FUNCIONARIO, true);
        validation(funcionarioAlterado);
    }

    //---------------------------------------------Delete--Success------------------------------------------------------
    @Test
    @DisplayName("Excluindo funcionário pelo ID")
    void deleteSuccess() {
        //Simulando dados
        Mockito.when(funcionarioRepository.findById(ID_FUNCIONARIO)).thenReturn(Optional.of(funcionarioModel));
        Mockito.doNothing().when(funcionarioRepository).delete(funcionarioModel);

        //Excluindo
        funcionarioService.delete(ID_FUNCIONARIO);

        //Validando teste
        Mockito.verify(funcionarioRepository, Mockito.times(1)).delete(funcionarioModel);
    }


    //---------------------------------------------ListAll--NotFoundFuncionarios----------------------------------------
    @Test
    @DisplayName("Exception notFound ao listar todos os funcionários")
    void listAllNotFoundExceptionFuncionarios() {

        //Tentando listar
        NotFoundException exception = Assertions.assertThrows(NotFoundException.class, () -> {
            funcionarioService.listAll(1);
        });


        //Validando teste
        Assertions.assertEquals(NotFoundException.class, exception.getClass());
        Assertions.assertEquals("Nenhum funcionário encontrado.", exception.getMessage());
    }

    //---------------------------------------------ListById--NotFoundFuncionario----------------------------------------
    @Test
    @DisplayName("Exception notFound ao buscar funcionário por ID")
    void listByIdNotFoundExceptionFuncionario() {

        //Tentando buscar
        NotFoundException exception = Assertions.assertThrows(NotFoundException.class, () -> {
            funcionarioService.listById(1L);
        });


        //Validando teste
        Assertions.assertEquals(NotFoundException.class, exception.getClass());
        Assertions.assertEquals("Funcionário de ID (" + ID_FUNCIONARIO + ") não foi encontrado.", exception.getMessage());
    }

    //---------------------------------------------Save--DuplicateName--------------------------------------------------
    @Test
    @DisplayName("Exception duplicateName ao tentar salvar novo funcionário com nome já cadastrado")
    void saveDuplicateNameException() {
        //Lançando exception para simular funcionário com mesmo nome já cadastrado
        Mockito.when(funcionarioRepository.findByName(NOME_FUNCIONARIO)).thenReturn(Optional.of(funcionarioModel));

        //Tentando salvar funcionário com nome duplicado
        DuplicateNameException exception = Assertions.assertThrows(DuplicateNameException.class, () -> {
            funcionarioService.save(funcionarioDTO);
        });


        //Validando teste
        Assertions.assertEquals(DuplicateNameException.class, exception.getClass());
        Assertions.assertEquals("Funcionário de nome (" + NOME_FUNCIONARIO + ") já existe na base de dados.", exception.getMessage());
    }

    //---------------------------------------------Save--NotFoundCargo--------------------------------------------------
    @Test
    @DisplayName("Exception notFound ao tentar salvar novo funcionário com cargo inexistente")
    void saveNotFoundExceptionCargo() {
        //Tentando salvar funcionário com cargo inexistente
        NotFoundException exception = Assertions.assertThrows(NotFoundException.class, () -> {
            funcionarioService.save(funcionarioDTO);
        });


        //Validando teste
        Assertions.assertEquals(NotFoundException.class, exception.getClass());
        Assertions.assertEquals("Cargo informado com ID (" + FK_CARGO + ") não foi encontrado.", exception.getMessage());
    }

    //---------------------------------------------Save--NotFoundSetor--------------------------------------------------
    @Test
    @DisplayName("Exception notFound ao tentar salvar novo funcionário com setor inexistente")
    void saveNotFoundExceptionSetor() {
        //Simulando os dados
        Mockito.when(cargoRepository.findById(FK_CARGO)).thenReturn(Optional.of(cargoModel));

        //Tentando salvar funcionário com setor inexistente
        NotFoundException exception = Assertions.assertThrows(NotFoundException.class, () -> {
            funcionarioService.save(funcionarioDTO);
        });


        //Validando teste
        Assertions.assertEquals(NotFoundException.class, exception.getClass());
        Assertions.assertEquals("Setor informado com ID (" + FK_SETOR + ") não foi encontrado.", exception.getMessage());
    }

    //---------------------------------------------Save--NotFoundEndereco-----------------------------------------------
    @Test
    @DisplayName("Exception badRequest ao tentar salvar novo funcionário com endereço nulo")
    void saveNotFoundExceptionEndereco() {
        //Simulando os dados
        Mockito.when(cargoRepository.findById(FK_CARGO)).thenReturn(Optional.of(cargoModel));
        Mockito.when(setorRepository.findById(FK_SETOR)).thenReturn(Optional.of(setorModel));


        //Tentando salvar funcionário com endereço nulo
        funcionarioDTO.setEnderecos_funcionario(new ArrayList<>());
        BadRequestException exception = Assertions.assertThrows(BadRequestException.class, () -> {
            funcionarioService.save(funcionarioDTO);
        });


        //Validando teste
        Assertions.assertEquals(BadRequestException.class, exception.getClass());
        Assertions.assertEquals("Nenhum endereço foi adicionado", exception.getMessage());
    }

    //---------------------------------------------Update--NotFoundFuncionario------------------------------------------
    @Test
    @DisplayName("Exception notFound ao tentar alterar funcionário com ID inexistente")
    void updateNotFoundExceptionFuncionario() {
        //Lançando exception para simular funcionário com mesmo nome já cadastrado

        //Tentando alterar funcionário com ID inexistente
        NotFoundException exception = Assertions.assertThrows(NotFoundException.class, () -> {
            funcionarioService.update(funcionarioDTO, ID_FUNCIONARIO);
        });


        //Validando teste
        Assertions.assertEquals(NotFoundException.class, exception.getClass());
        Assertions.assertEquals("Funcionário de ID (" + ID_FUNCIONARIO + ") não foi encontrado.", exception.getMessage());
    }

    //---------------------------------------------Update--DuplicateName------------------------------------------------
    @Test
    @DisplayName("Exception duplicateName ao tentar alterar nome do funcionário com nome já existente em outro")
    void updateDuplicateNameExceptionNome() {
        //Lançando exception para simular funcionário com mesmo nome já cadastrado
        Mockito.when(funcionarioRepository.findById(ID_FUNCIONARIO)).thenReturn(Optional.of(funcionarioModel));
        funcionarioModel.setId_funcionario(2L);
        Mockito.when(funcionarioRepository.findByName(NOME_FUNCIONARIO)).thenReturn(Optional.of(funcionarioModel));

        //Tentando alterar funcionário com nome duplicado
        DuplicateNameException exception = Assertions.assertThrows(DuplicateNameException.class, () -> {
            funcionarioService.update(funcionarioDTO, ID_FUNCIONARIO);
        });


        //Validando teste
        Assertions.assertEquals(DuplicateNameException.class, exception.getClass());
        Assertions.assertEquals("Funcionário de nome (" + NOME_FUNCIONARIO + ") já existe na base de dados.", exception.getMessage());
    }

    //---------------------------------------------Update--NotFoundCargo------------------------------------------------
    @Test
    @DisplayName("Exception notFound ao tentar alterar funcionário com cargo inexistente")
    void updateNotFoundExceptionCargo() {
        //Lançando exception para simular cargo inexistente
        Mockito.when(funcionarioRepository.findById(ID_FUNCIONARIO)).thenReturn(Optional.of(funcionarioModel));


        //Tentando alterar funcionário com cargo inexistente
        NotFoundException exception = Assertions.assertThrows(NotFoundException.class, () -> {
            funcionarioService.update(funcionarioDTO, ID_FUNCIONARIO);
        });


        //Validando teste
        Assertions.assertEquals(NotFoundException.class, exception.getClass());
        Assertions.assertEquals("Cargo informado com ID (" + FK_CARGO + ") não foi encontrado.", exception.getMessage());
    }

    //---------------------------------------------Update--NotFoundSetor------------------------------------------------
    @Test
    @DisplayName("Exception notFound ao tentar alterar funcionário com setor inexistente")
    void updateNotFoundExceptionSetor() {

        //Simulando os dados
        Mockito.when(funcionarioRepository.findById(ID_FUNCIONARIO)).thenReturn(Optional.of(funcionarioModel));
        Mockito.when(cargoRepository.findById(FK_CARGO)).thenReturn(Optional.of(cargoModel));


        //Tentando salvar funcionário com setor inexistente
        NotFoundException exception = Assertions.assertThrows(NotFoundException.class, () -> {
            funcionarioService.update(funcionarioDTO, ID_FUNCIONARIO);
        });


        //Validando teste
        Assertions.assertEquals(NotFoundException.class, exception.getClass());
        Assertions.assertEquals("Setor informado com ID (" + FK_SETOR + ") não foi encontrado.", exception.getMessage());
    }

    //---------------------------------------------Update--NotFoundEndereco---------------------------------------------
    @Test
    @DisplayName("Exception notFound ao tentar alterar funcionário sem informar ao menos um endereço")
    void updateNotFoundExceptionEndereco() {
        //Simulando os dados
        Mockito.when(funcionarioRepository.findById(ID_FUNCIONARIO)).thenReturn(Optional.of(funcionarioModel));
        Mockito.when(cargoRepository.findById(FK_CARGO)).thenReturn(Optional.of(cargoModel));
        Mockito.when(setorRepository.findById(FK_SETOR)).thenReturn(Optional.of(setorModel));


        //Tentando salvar funcionário com endereço nulo
        funcionarioDTO.setEnderecos_funcionario(new ArrayList<>());
        BadRequestException exception = Assertions.assertThrows(BadRequestException.class, () -> {
            funcionarioService.update(funcionarioDTO, ID_FUNCIONARIO);
        });


        //Validando teste
        Assertions.assertEquals(BadRequestException.class, exception.getClass());
        Assertions.assertEquals("Nenhum endereço foi adicionado", exception.getMessage());
    }

    //---------------------------------------------Update--NotFoundFuncionario------------------------------------------
    @Test
    @DisplayName("Exception notFound ao tentar alterar status do funcionário inexistente")
    void updateStatusNotFoundExceptionFuncionario() {
        NotFoundException exception = Assertions.assertThrows(NotFoundException.class, () -> {
            funcionarioService.updateStatus(ID_FUNCIONARIO, true);
        });

        //Validando
        Assertions.assertEquals(NotFoundException.class, exception.getClass());
        Assertions.assertEquals("Funcionário de ID (" + ID_FUNCIONARIO + ") não foi encontrado.", exception.getMessage());
    }

    //---------------------------------------------Delete--NotFoundFuncionario------------------------------------------
    @Test
    @DisplayName("Exception notFound ao tentar excluir um funcionário com ID inexistente")
    void deleteNotFoundExceptionFuncionario() {
        NotFoundException exception = Assertions.assertThrows(NotFoundException.class, () -> {
            funcionarioService.delete(ID_FUNCIONARIO);
        });

        //Validando teste
        Assertions.assertEquals(NotFoundException.class, exception.getClass());
        Assertions.assertEquals("Funcionário de ID (" + ID_FUNCIONARIO + ") não foi encontrado.", exception.getMessage());
    }

}