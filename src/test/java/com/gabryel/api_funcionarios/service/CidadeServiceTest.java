package com.gabryel.api_funcionarios.service;

import com.gabryel.api_funcionarios.exception.types.DuplicateNameException;
import com.gabryel.api_funcionarios.exception.types.NotFoundException;
import com.gabryel.api_funcionarios.model.Cidade;
import com.gabryel.api_funcionarios.repository.CidadeRepository;
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
class CidadeServiceTest {

    private final static Long ID_CIDADE = 1L;
    private final static String NOME_CIDADE = "Goiás";
    private Cidade cidadeModel;
    @Mock
    private CidadeRepository cidadeRepository;
    @InjectMocks
    private CidadeService cidadeService;

    private CidadeServiceTest() {
        loadDate();
    }

    private void loadDate() {
        this.cidadeModel = new Cidade(ID_CIDADE, NOME_CIDADE);
    }

    //-----------------------------------------Validação--dados--retornados---------------------------------------------
    private void validation(Cidade cidade){
        Assertions.assertNotNull(cidade);
        Assertions.assertEquals(Cidade.class, cidade.getClass());
        Assertions.assertEquals(1, cidade.getId_cidade());
        Assertions.assertEquals(NOME_CIDADE, cidade.getNome_cidade());
    }



    //---------------------------------------------ListAll--Success-----------------------------------------------------
    @Test
    @DisplayName("Buscar todos as cidades existentes")
    void listAllCidadesSuccess() {
        //Criando dados de teste
        List<Cidade> novasCidades = new ArrayList<>();
        novasCidades.add(cidadeModel);

        Page<Cidade> page = new PageImpl<>(novasCidades, PageRequest.of(0, 10), 1);

        //Simulando dados
        Mockito.when(cidadeRepository.findAll(PageRequest.of(0, 10))).thenReturn(page);

        //Listando
        List<Cidade> cidadesEncontradas = cidadeService.listAll(1);

        //Validando
        Assertions.assertEquals(1, cidadesEncontradas.size());
        validation(cidadesEncontradas.get(0));

    }

    //---------------------------------------------ListById--Success----------------------------------------------------
    @Test
    @DisplayName("Buscar uma única cidade pelo ID")
    void listByIdCidadeSuccess() {
        //Simulando dados
        Mockito.when(cidadeRepository.findById(ID_CIDADE)).thenReturn(Optional.of(cidadeModel));

        //Buscando
        Cidade cidadeEncontrada = cidadeService.listById(ID_CIDADE);

        //Validando
        validation(cidadeEncontrada);
    }

    //---------------------------------------------Save--Success--------------------------------------------------------
    @Test
    @DisplayName("Salvando nova cidade")
    void saveCidadeSuccess() {
        //Simulando dados
        Mockito.when(cidadeRepository.save(Mockito.any(Cidade.class))).thenReturn(cidadeModel);

        //Salvando
        Cidade cidadeSalvo = cidadeService.save(cidadeModel.convertToDto_output());

        //Validando
        validation(cidadeSalvo);
    }

    //---------------------------------------------Update--Success------------------------------------------------------
    @Test
    @DisplayName("Alterando dados da cidade pelo ID")
    void updateCidadeSuccess() {
        //Simulando dados
        Mockito.when(cidadeRepository.findById(ID_CIDADE)).thenReturn(Optional.of(cidadeModel));
        Mockito.when(cidadeRepository.save(cidadeModel)).thenReturn(cidadeModel);

        //Alterando
        Cidade cidadeAlterada = cidadeService.update(cidadeModel.convertToDto_output(), ID_CIDADE);

        //Validando
        validation(cidadeAlterada);
    }

    //---------------------------------------------Delete--Success------------------------------------------------------
    @Test
    @DisplayName("Excluindo cidade pelo ID")
    void deleteCidadeSuccess() {
        //Simulando dados
        Mockito.when(cidadeRepository.findById(ID_CIDADE)).thenReturn(Optional.of(cidadeModel));
        Mockito.doNothing().when(cidadeRepository).delete(cidadeModel);

        //Deletando
        cidadeService.delete(ID_CIDADE);

        //Validando
        Mockito.verify(cidadeRepository, Mockito.times(1)).delete(cidadeModel);
    }


    //Teste de exceptions
    //---------------------------------------------ListAll--NotFoundCidades---------------------------------------------
    @Test
    @DisplayName("Exception notFound ao listar todas as cidades")
    void listAllNotFoundExceptionCidades() {
        //Capturando NotFound da cidades não encontradas
        NotFoundException exception = Assertions.assertThrows(NotFoundException.class, () -> {
            cidadeService.listAll(1);
        });


        //Validando
        Assertions.assertEquals(NotFoundException.class, exception.getClass());
        Assertions.assertEquals("Nenhuma cidade encontrada.", exception.getMessage());
    }

    //---------------------------------------------ListByID--NotFoundCidade---------------------------------------------
    @Test
    @DisplayName("Exception notFound ao listar cidade por ID")
    void listByIdNotFoundExceptionCidade() {
        //Capturando NotFound da cidade não encontrada
        NotFoundException exception = Assertions.assertThrows(NotFoundException.class, () -> {
            cidadeService.listById(1L);
        });


        //Validando
        Assertions.assertEquals(NotFoundException.class, exception.getClass());
        Assertions.assertEquals("Cidade de ID (" + ID_CIDADE + ") não foi encontrada.", exception.getMessage());
    }


    //---------------------------------------------Save--DuplicateNameCidade--------------------------------------------
    @Test
    @DisplayName("Exception duplicateName ao salvar nova cidade")
    void saveDuplicateNameExceptionCidade() {
        //Simulando dados
        Mockito.when(cidadeRepository.findByName(NOME_CIDADE)).thenReturn(Optional.of(cidadeModel));


        //Capturando DuplicateNameException nome da cidade duplicado
        DuplicateNameException exception = Assertions.assertThrows(DuplicateNameException.class, () -> {
            cidadeService.save(cidadeModel.convertToDto_output());
        });


        //Validando
        Assertions.assertEquals(DuplicateNameException.class, exception.getClass());
        Assertions.assertEquals("Cidade de nome (" + NOME_CIDADE + ") já existe na base de dados.", exception.getMessage());
    }

    //---------------------------------------------Update--DuplicateNameCidade------------------------------------------
    @Test
    @DisplayName("Exception duplicateName ao alterar cidade")
    void updateDuplicateNameExceptionCidade() {

        //Simulando dados
        Mockito.when(cidadeRepository.findByName(NOME_CIDADE)).thenReturn(Optional.of(cidadeModel));


        //Capturando DuplicateNameException nome da cidade duplicado
        DuplicateNameException exception = Assertions.assertThrows(DuplicateNameException.class, () -> {
            cidadeService.save(cidadeModel.convertToDto_output());
        });


        //Validando
        Assertions.assertEquals(DuplicateNameException.class, exception.getClass());
        Assertions.assertEquals("Cidade de nome (" + NOME_CIDADE + ") já existe na base de dados.", exception.getMessage());
    }

    //---------------------------------------------Delete--NotFoundCidade-----------------------------------------------
    @Test
    @DisplayName("Exception notFound ao excluir cidade")
    void deleteNotFoundExceptionCidade() {
        //Capturando NotFound da cidade não encontrada
        NotFoundException exception = Assertions.assertThrows(NotFoundException.class, () -> {
            cidadeService.delete(1L);
        });

        //Validando
        Assertions.assertEquals(NotFoundException.class, exception.getClass());
        Assertions.assertEquals("Cidade de ID (" + ID_CIDADE + ") não foi encontrada.", exception.getMessage());
    }
}