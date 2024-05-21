package br.com.study.springEssentials.service;

import br.com.study.springEssentials.domains.domain.Anime;
import br.com.study.springEssentials.repository.AnimeRepository;
import br.com.study.springEssentials.util.AnimeCreator;
import br.com.study.springEssentials.util.AnimePostRequestBodyCreator;
import br.com.study.springEssentials.util.AnimePutRequestBodyCreator;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

@ExtendWith(SpringExtension.class)
public class AnimeServiceTest {
    
    @InjectMocks // usamos InjectMocks quando eu quero testar a classe em si
    private AnimeService animeService;

    @Mock // usamos ele para todas as classes que estao sendo usando DENTRO DA CLASSE USADA PELO INJECTMOCKS
    private AnimeRepository animeRepositoryMock;

    @BeforeEach
        // Diz que o metodo deve ser executado antes de todos os testes(usado para configurar o ambiente de testes)
    void setUp() {
        PageImpl<Anime> animePage = new PageImpl<>(List.of(AnimeCreator.createValidAnime()));
        BDDMockito.when(animeRepositoryMock.findAll(ArgumentMatchers.any(PageRequest.class)))
                .thenReturn(animePage);
        // significa que quando alguem executar uma chamdaa para o listAll,
        // independente do argumento passado para ele, deve retornar o valor independente do argumento passado

        BDDMockito.when(animeRepositoryMock.findAll()).thenReturn(List.of(AnimeCreator.createValidAnime()));

        BDDMockito.when(animeRepositoryMock.findById(ArgumentMatchers.anyLong()))
                .thenReturn(Optional.of(AnimeCreator.createValidAnime()));

        BDDMockito.when(animeRepositoryMock.findByName(ArgumentMatchers.any()))
                .thenReturn(List.of(AnimeCreator.createValidAnime()));

        BDDMockito.when(animeRepositoryMock.save(ArgumentMatchers.any(Anime.class)))
                .thenReturn(AnimeCreator.createValidAnime());

        BDDMockito.doNothing().when(animeRepositoryMock).delete(ArgumentMatchers.any(Anime.class));

    }

    @Test
    @DisplayName("List returns list of animes inside page object when successful")
    void listAll_ReturnsListOfAnimesInsidePageObject_WhenSuccessful(){
        String expectedName = AnimeCreator.createValidAnime().getName();
        Page<Anime> animePage = animeService.listAll(PageRequest.of(1,1));

        Assertions.assertThat(animePage).isNotNull();
        Assertions.assertThat(animePage.toList()).isNotNull();
        Assertions.assertThat(animePage.toList()).isNotEmpty().hasSize(1);
        Assertions.assertThat(animePage.toList().get(0).getName()).isEqualTo(expectedName);
    }

    @Test
    @DisplayName("ListAll returns list of animes when successful")
    void listAllNonPageable_ReturnsListOfAnimes_WhenSuccessful(){
        String expectedName = AnimeCreator.createValidAnime().getName();
        List<Anime> animes = animeService.listAllNonPageable();

        Assertions.assertThat(animes).isNotNull();
        Assertions.assertThat(animes).isNotEmpty().hasSize(1);
        Assertions.assertThat(animes.get(0).getName()).isEqualTo(expectedName);
    }

    @Test
    @DisplayName("FindById returns anime when successful")
    void findByIdOrThrowBadRequestException_ReturnSAnime_WhenSuccessful(){
        Long expectedId = AnimeCreator.createValidAnime().getId();
        Anime animes = animeService.findByIdOrThrowBadRequestException(1L);

        Assertions.assertThat(animes).isNotNull();
        Assertions.assertThat(animes.getId()).isNotNull().isEqualTo(expectedId);
    }

    @Test
    @DisplayName("FindByName returns anime when successful")
    void findByName_ReturnSAnime_WhenSuccessful(){
        BDDMockito.when(animeRepositoryMock.findByName(ArgumentMatchers.any()))
                .thenReturn(List.of(AnimeCreator.createValidAnime()));

        List<Anime> animes = animeService.findByName("anime");

        Assertions.assertThat(animes)
                .isNotNull()
                .isNotEmpty();
    }

    // CORRIGIR ESSE TESTE
//    @Test
//    @DisplayName("Save returns anime when successful")
//    void save_ReturnsAnime_WhenSuccessful(){
//        Anime anime = animeService.save(AnimePostRequestBodyCreator.createAnimePostRequestBody());
//
//        Assertions.assertThat(anime).isNotNull().isEqualTo(AnimeCreator.createValidAnime());
//    }


    // CORRIGIR ESSE TESTE
//    @Test
//    @DisplayName("Replace updates anime when successful")
//    void replace_UpdatesAnime_WhenSuccessful(){
//        Assertions.assertThatCode(() -> animeService.replace(AnimePutRequestBodyCreator.createAnimePutRequestBody()))
//                .doesNotThrowAnyException();
//    }

    @Test
    @DisplayName("Delete updates anime when successful")
    void delete_UpdatesAnime_WhenSuccessful() {
        Assertions.assertThatCode(() -> animeService.delete(1L)).doesNotThrowAnyException();
        }
    }
