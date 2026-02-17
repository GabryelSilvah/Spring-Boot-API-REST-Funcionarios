package com.gabryel.api_funcionarios.service;

import com.gabryel.api_funcionarios.exception.types.DuplicateNameException;
import com.gabryel.api_funcionarios.exception.types.NotFoundException;
import com.gabryel.api_funcionarios.model.Cargo;
import com.gabryel.api_funcionarios.repository.CargoRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class CargoServiceTest {
    private final static Long ID_CARGO = 1L;
    private final static String NOME_CARGO = "Técnico";
    private Cargo cargoModel;
    @Mock
    private CargoRepository cargoRepository;
    @InjectMocks
    private CargoService cargoService;

    private CargoServiceTest() {
        loadDate();
    }

    private void loadDate() {
        this.cargoModel = new Cargo(ID_CARGO, NOME_CARGO);
    }

    //-----------------------------------------Validação--dados--retornados---------------------------------------------
    private void validation(Cargo cargo) {
        Assertions.assertNotNull(cargo);
        Assertions.assertEquals(Cargo.class, cargo.getClass());
        Assertions.assertEquals(1, cargo.getId_cargo());
        Assertions.assertEquals(NOME_CARGO, cargo.getNome_cargo());
    }

    //--------------------------------------------ListAll--Success------------------------------------------------------
    @Test
    @DisplayName("Listar todos os cargos existentes")
    void listAllSuccess() {
        //Criando dados de teste
        List<Cargo> novosCargos = new ArrayList<>();
        novosCargos.add(cargoModel);

        //Simulando dados
        Mockito.when(cargoRepository.findAll()).thenReturn(novosCargos);

        //Listando
        List<Cargo> cargosEncontrados = cargoService.listAll();

        //Validando
        Assertions.assertEquals(1, cargosEncontrados.size());
        validation(cargosEncontrados.get(0));
    }

    //--------------------------------------------ListById--Success-----------------------------------------------------
    @Test
    @DisplayName("Buscar cargo pelo ID")
    void listByIdSuccess() {
        //Simulando dados
        Mockito.when(cargoRepository.findById(ID_CARGO)).thenReturn(Optional.of(cargoModel));

        //Buscando
        Cargo cargoEncontrado = cargoService.listById(ID_CARGO);

        //Validando
        validation(cargoEncontrado);
    }

    //--------------------------------------------Save--Success---------------------------------------------------------
    @Test
    @DisplayName("Salvar novo cargo")
    void saveSuccess() {
        //Simulando dados
        Mockito.lenient().when(cargoRepository.save(Mockito.any(Cargo.class))).thenReturn(cargoModel);

        //Salvando
        Cargo cargoSalvo = cargoService.save(cargoModel.convertToDto());

        //Validando
        validation(cargoSalvo);
    }

    //--------------------------------------------Update--Success-------------------------------------------------------
    @Test
    @DisplayName("Alterando dados do cargo pelo ID")
    void updateSuccess() {
        //Simulando dados
        Mockito.when(cargoRepository.findById(ID_CARGO)).thenReturn(Optional.of(cargoModel));
        Mockito.when(cargoRepository.save(Mockito.any(Cargo.class))).thenReturn(cargoModel);

        //Alterando
        Cargo cargoAlterado = cargoService.update(cargoModel.convertToDto(), ID_CARGO);

        //Validando
        validation(cargoAlterado);
    }

    //--------------------------------------------Delete--Success-------------------------------------------------------
    @Test
    @DisplayName("Excluindo cargo pelo ID")
    void deleteSuccess() {
        //Simulando dados
        Mockito.when(cargoRepository.findById(ID_CARGO)).thenReturn(Optional.of(cargoModel));
        Mockito.doNothing().when(cargoRepository).delete(cargoModel);

        //Excluindo
        cargoService.delete(ID_CARGO);

        //Validando
        Mockito.verify(cargoRepository, Mockito.times(1)).delete(cargoModel);
    }


    //Teste de exceptions
    //---------------------------------------------ListAll--NotFoundCargos----------------------------------------------
    @Test
    @DisplayName("Exception notFound ao listar todos os cargos")
    void listAllNotFoundExceptionCargos() {
        //Capturando NotFound dos cargos não encontrados
        NotFoundException exception = Assertions.assertThrows(NotFoundException.class, () -> {
            cargoService.listAll();
        });


        //Validando
        Assertions.assertEquals(NotFoundException.class, exception.getClass());
        Assertions.assertEquals("Nenhum cargo encontrado.", exception.getMessage());
    }

    //---------------------------------------------ListAll--NotFoundCargo-----------------------------------------------
    @Test
    @DisplayName("Exception notFound ao listar cargo por ID")
    void listByIdNotFoundExceptionCargo() {
        //Capturando NotFound do cargo não encontrado
        NotFoundException exception = Assertions.assertThrows(NotFoundException.class, () -> {
            cargoService.listById(1L);
        });


        //Validando
        Assertions.assertEquals(NotFoundException.class, exception.getClass());
        Assertions.assertEquals("Cargo de ID (" + ID_CARGO + ") não foi encontrado.", exception.getMessage());
    }

    //---------------------------------------------Save--DuplicateNomeCargo--------------------------------------------
    @Test
    @DisplayName("Exception duplicateName ao salvar novo cargo")
    void saveDuplicateNameExceptionNomeCargo() {
        //Simulando dados
        Mockito.when(cargoRepository.findByName(NOME_CARGO)).thenReturn(Optional.of(cargoModel));


        //Capturando DuplicateNameException nome do cargo duplicado
        DuplicateNameException exception = Assertions.assertThrows(DuplicateNameException.class, () -> {
            cargoService.save(cargoModel.convertToDto());
        });


        //Validando
        Assertions.assertEquals(DuplicateNameException.class, exception.getClass());
        Assertions.assertEquals("Cargo de nome (" + NOME_CARGO + ") já existe na base de dados.", exception.getMessage());
    }

    //---------------------------------------------Update--DuplicateNameCargo-------------------------------------------
    @Test
    @DisplayName("Exception duplicateName ao alterar cargo")
    void updateDuplicateExceptionNomeCargo() {
        //Simulando dados
        Mockito.when(cargoRepository.findByName(NOME_CARGO)).thenReturn(Optional.of(cargoModel));


        //Capturando DuplicateNameException nome do cargo duplicado
        DuplicateNameException exception = Assertions.assertThrows(DuplicateNameException.class, () -> {
            cargoService.save(cargoModel.convertToDto());
        });


        //Validando
        Assertions.assertEquals(DuplicateNameException.class, exception.getClass());
        Assertions.assertEquals("Cargo de nome (" + NOME_CARGO + ") já existe na base de dados.", exception.getMessage());
    }

    //---------------------------------------------Delete--NotFoundCargos-----------------------------------------------
    @Test
    @DisplayName("Exception notFound ao excluir cargo")
    void deleteNotFoundExceptionCargo() {
        //Capturando NotFound do cargo não encontrado
       NotFoundException exception = Assertions.assertThrows(NotFoundException.class, () -> {
            cargoService.delete(1L);
        });

        //Validando teste
        Assertions.assertEquals(NotFoundException.class, exception.getClass());
        Assertions.assertEquals("Cargo de ID (" + ID_CARGO + ") não foi encontrado.", exception.getMessage());
    }
}