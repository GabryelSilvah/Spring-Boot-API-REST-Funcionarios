package com.gabryel.api_funcionarios.service;

import com.gabryel.api_funcionarios.exception.types.DuplicateNameException;
import com.gabryel.api_funcionarios.exception.types.NotFoundException;
import com.gabryel.api_funcionarios.model.Setor;
import com.gabryel.api_funcionarios.repository.SetorRepository;
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
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class SetorServiceTest {

    private final static Long ID_SETOR = 1L;
    private final static String NOME_SETOR = "Estoque";
    private Setor setorModel;
    @Mock
    private SetorRepository setorRepository;
    @InjectMocks
    private SetorService setorService;

    private SetorServiceTest() {
        loadDate();
    }

    private void loadDate() {
        this.setorModel = new Setor(ID_SETOR, NOME_SETOR);
    }

    //-----------------------------------------Validação--dados--retornados---------------------------------------------
    private void validation(Setor setor) {
        Assertions.assertNotNull(setor);
        Assertions.assertEquals(Setor.class, setor.getClass());
        Assertions.assertEquals(1, setor.getId_setor());
        Assertions.assertEquals(NOME_SETOR, setor.getNome_setor());
    }

    //--------------------------------------------ListAll--Success------------------------------------------------------
    @Test
    @DisplayName("Buscar todos os setores existentes")
    void listAllSuccess() {
        //Carregando dados de teste
        List<Setor> novosSetor = new ArrayList<>();
        novosSetor.add(setorModel);

        Page<Setor> page = new PageImpl<>(novosSetor, PageRequest.of(0, 10), 1);

        //Simulando dados
        Mockito.when(setorRepository.findAll(PageRequest.of(0, 10))).thenReturn(page);

        //Listando
        List<Setor> setorEncontrados = setorService.listAll(1);

        //Validando
        Assertions.assertEquals(1, setorEncontrados.size());
        validation(setorEncontrados.get(0));

    }

    //--------------------------------------------ListById--Success-----------------------------------------------------
    @Test
    @DisplayName("Buscar um único setor pelo ID")
    void listByIdSuccess() {
        //Simulando dados
        Mockito.when(setorRepository.findById(ID_SETOR)).thenReturn(Optional.of(setorModel));

       //Buscando
        Setor setorEncontrado = setorService.listById(ID_SETOR);

        //Validando
        validation(setorEncontrado);
    }

    //--------------------------------------------Save--Success---------------------------------------------------------
    @Test
    @DisplayName("Salvando novo setor")
    void saveSuccess() {
        //Simulando dados
        Mockito.when(setorRepository.save(Mockito.any(Setor.class))).thenReturn(setorModel);

        //Salvando
        Setor setorSalvo = setorService.save(setorModel.convertToDto());

        //Validando
        validation(setorSalvo);
    }

    //--------------------------------------------Update--Success-------------------------------------------------------
    @Test
    @DisplayName("Alterando dados do setor pelo ID")
    void updateSuccess() {
        //Simulando dados
        Mockito.when(setorRepository.findById(ID_SETOR)).thenReturn(Optional.of(setorModel));
        Mockito.when(setorRepository.save(Mockito.any(Setor.class))).thenReturn(setorModel);

        //Alterando
        Setor setorAlterado = setorService.update(setorModel.convertToDto(), ID_SETOR);

        //Validando
        validation(setorAlterado);
    }

    //--------------------------------------------Delete--Success------------------------------------------------------
    @Test
    @DisplayName("Excluindo setor pelo ID")
    void deleteSuccess() {
        //Simulando dados
        Mockito.when(setorRepository.findById(ID_SETOR)).thenReturn(Optional.of(setorModel));
        Mockito.doNothing().when(setorRepository).delete(setorModel);

        //Deletando
        setorService.delete(ID_SETOR);

        //Validando
        Mockito.verify(setorRepository, Mockito.times(1)).delete(setorModel);
    }


    //Teste de exceptions
    //---------------------------------------------ListAll--NotFoundSetores---------------------------------------------
    @Test
    @DisplayName("Exception notFound ao listar todos os setores")
    void listAllNotFoundExceptionSetores() {
        //Capturando NotFound dos setores não encontrados
        NotFoundException exception = Assertions.assertThrows(NotFoundException.class, () -> {
            setorService.listAll(1);
        });


        //Validando
        Assertions.assertEquals(NotFoundException.class, exception.getClass());
        Assertions.assertEquals("Nenhum setor encontrado.", exception.getMessage());
    }

    //---------------------------------------------ListById--NotFoundSetor----------------------------------------------
    @Test
    @DisplayName("Exception notFound ao listar setor por ID")
    void listByIdNotFoundExceptionSetor() {
        //Capturando NotFound do setor não encontrado
        NotFoundException exception = Assertions.assertThrows(NotFoundException.class, () -> {
            setorService.listById(1L);
        });


        //Validando
        Assertions.assertEquals(NotFoundException.class, exception.getClass());
        Assertions.assertEquals("Setor de ID (" + ID_SETOR + ") não foi encontrado.", exception.getMessage());
    }

    //---------------------------------------------Save--DuplicateNameSetor---------------------------------------------
    @Test
    @DisplayName("Exception duplicateName ao salvar novo setor")
    void saveDuplicateNameExceptionNomeSetor() {

        //Simulando dados
        Mockito.when(setorRepository.findByName(NOME_SETOR)).thenReturn(Optional.of(setorModel));


        //Capturando DuplicateNameException nome do setor duplicado
        DuplicateNameException exception = Assertions.assertThrows(DuplicateNameException.class, () -> {
            setorService.save(setorModel.convertToDto());
        });


        //Validando
        Assertions.assertEquals(DuplicateNameException.class, exception.getClass());
        Assertions.assertEquals("Setor de nome (" + NOME_SETOR + ") já existe na base de dados.", exception.getMessage());
    }

    //---------------------------------------------Update--NotFoundSetor------------------------------------------------
    @Test
    @DisplayName("Exception notFound ao alterar setor")
    void updateNotFoundExceptionSetor() {
        //Capturando NotFound do setor não encontrado
        NotFoundException exception = Assertions.assertThrows(NotFoundException.class, () -> {
            setorService.update(setorModel.convertToDto(), ID_SETOR);
        });


        //Validando teste
        Assertions.assertEquals(NotFoundException.class, exception.getClass());
        Assertions.assertEquals("Setor de ID (" + ID_SETOR + ") não foi encontrado.", exception.getMessage());
    }


    //---------------------------------------------Update--DuplicateNameSetor-------------------------------------------
    @Test
    @DisplayName("Exception duplicateName ao alterar setor")
    void updateDuplcateNameExceptionNomeSetor() {
        //Simulando dados
        Mockito.when(setorRepository.findById(ID_SETOR)).thenReturn(Optional.of(setorModel));
        setorModel.setId_setor(2L);
        Mockito.when(setorRepository.findByName(NOME_SETOR)).thenReturn(Optional.of(setorModel));


        //Capturando DuplicateNameException nome do setor duplicado
        DuplicateNameException exception = Assertions.assertThrows(DuplicateNameException.class, () -> {
            setorService.update(setorModel.convertToDto(), ID_SETOR);
        });


        //Validando
        Assertions.assertEquals(DuplicateNameException.class, exception.getClass());
        Assertions.assertEquals("Setor de nome (" + NOME_SETOR + ") já existe na base de dados.", exception.getMessage());
    }

    //---------------------------------------------Delete--NotFoundSetor------------------------------------------------
    @Test
    @DisplayName("Exception notFound ao excluir setor")
    void deleteNotFoundExceptionSetor() {
        //Capturando NotFound do setor não encontrado
        NotFoundException exception = Assertions.assertThrows(NotFoundException.class, () -> {
            setorService.delete(1L);
        });

        //Validando teste
        Assertions.assertEquals(NotFoundException.class, exception.getClass());
        Assertions.assertEquals("Setor de ID (" + ID_SETOR + ") não foi encontrado.", exception.getMessage());
    }
}