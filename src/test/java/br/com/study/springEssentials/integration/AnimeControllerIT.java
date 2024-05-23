package br.com.study.springEssentials.integration;

import br.com.study.springEssentials.domains.domain.Anime;
import br.com.study.springEssentials.domains.requests.AnimePostRequestBody;
import br.com.study.springEssentials.repository.AnimeRepository;
import br.com.study.springEssentials.util.AnimeCreator;
import br.com.study.springEssentials.util.AnimePostRequestBodyCreator;
import br.com.study.springEssentials.wrapper.PageableResponse;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;

import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT) // roda em uma porta aleatoria
@AutoConfigureTestDatabase
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD) // vai limpar antes de cada teste mas aumenta o tempo de execução dos testse
public class AnimeControllerIT {

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Autowired
    private AnimeRepository animeRepository;

    @Test
    @DisplayName("List returns list of animes inside page object when successful")
    void list_ReturnsListOfAnimesInsidePageObject_WhenSuccessful(){
        Anime savedAdnime = animeRepository.save(AnimeCreator.createAnimeToBeSaved());
        String expectedName = savedAdnime.getName();

        PageableResponse<Anime> animePage = testRestTemplate.exchange("/animes", HttpMethod.GET, null,
                new ParameterizedTypeReference<PageableResponse<Anime>>() {
                }).getBody();

        Assertions.assertThat(animePage).isNotNull();
        Assertions.assertThat(animePage.toList()).isNotNull();
        Assertions.assertThat(animePage.toList()).isNotEmpty().hasSize(1);
        Assertions.assertThat(animePage.toList().get(0).getName()).isEqualTo(expectedName);
    }

    @Test
    @DisplayName("ListAll returns list of animes when successful")
    void listAll_ReturnsListOfAnimes_WhenSuccessful(){
        Anime savedAdnime = animeRepository.save(AnimeCreator.createAnimeToBeSaved());
        String expectedName = savedAdnime.getName();

        List<Anime> animes = testRestTemplate.exchange("/animes/all", HttpMethod.GET, null,
                new ParameterizedTypeReference<List<Anime>>() {
                }).getBody();

        Assertions.assertThat(animes).isNotNull();
        Assertions.assertThat(animes).isNotNull();
        Assertions.assertThat(animes).isNotEmpty().hasSize(1);
        Assertions.assertThat(animes.get(0).getName()).isEqualTo(expectedName);
    }

    @Test
    @DisplayName("FindById returns anime when successful")
    void findById_ReturnSAnime_WhenSuccessful(){
        Anime savedAnime = animeRepository.save(AnimeCreator.createAnimeToBeSaved());
        Long expectedId = savedAnime.getId();

        // Anime animes = animeController.findById(1L).getBody();

        Anime anime = testRestTemplate.getForObject("/animes/{id}", Anime.class, expectedId);

        Assertions.assertThat(anime).isNotNull();
        Assertions.assertThat(anime.getId()).isNotNull().isEqualTo(expectedId);
    }

    @Test
    @DisplayName("FindByName returns anime when successful")
    void findByName_ReturnSAnime_WhenSuccessful(){
        Anime savedAnime = animeRepository.save(AnimeCreator.createAnimeToBeSaved());

        String expectedName = savedAnime.getName();
        String url = String.format("/animes/find?name=%s", expectedName); // como é um parametro precisamos passar assim

        List<Anime> animes = testRestTemplate.exchange(url, HttpMethod.GET, null,
                new ParameterizedTypeReference<List<Anime>>() {
        }).getBody();

        Assertions.assertThat(animes)
                .isNotNull()
                .isNotEmpty();
    }

    @Test
    @DisplayName("FindByName returns an empty list of anime when anime is not found")
    void findByName_ReturnEmptyListOfAnime_WhenAnimeIsNotFound(){
        List<Anime> animes = testRestTemplate.exchange("/animes/find?name=dbz", HttpMethod.GET, null,
                new ParameterizedTypeReference<List<Anime>>() {
                }).getBody();

        Assertions.assertThat(animes)
                .isNotNull()
                .isEmpty();
    }

    @Test
    @DisplayName("Save returns anime when successful")
    void save_ReturnsAnime_WhenSuccessful(){
        AnimePostRequestBody animePostRequestBody = AnimePostRequestBodyCreator.createAnimePostRequestBody();
        ResponseEntity<Anime> animeResponseEntity = testRestTemplate.postForEntity("/animes", animePostRequestBody, Anime.class);

        Assertions.assertThat(animeResponseEntity).isNotNull();
        Assertions.assertThat(animeResponseEntity.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        Assertions.assertThat(animeResponseEntity.getBody()).isNotNull();
        Assertions.assertThat(animeResponseEntity.getBody().getId()).isNotNull();
    }


     @Test
     @DisplayName("Replace updates anime when successful")
     void replace_UpdatesAnime_WhenSuccessful(){
         Anime savedAnime = animeRepository.save(AnimeCreator.createAnimeToBeSaved());
         savedAnime.setName("new name");

         ResponseEntity<Void> animeResponseEntity = testRestTemplate.exchange("/animes", HttpMethod.PUT, new HttpEntity<>(savedAnime), Void.class);

         Assertions.assertThat(animeResponseEntity).isNotNull();
         Assertions.assertThat(animeResponseEntity.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
     }

     @Test
     @DisplayName("Delete updates anime when successful")
     void Delete_UpdatesAnime_WhenSuccessful(){
         Anime savedAnime = animeRepository.save(AnimeCreator.createAnimeToBeSaved());

         ResponseEntity<Void> animeResponseEntity = testRestTemplate.exchange("/animes/{id}", HttpMethod.DELETE, null, Void.class, savedAnime.getId());

         Assertions.assertThat(animeResponseEntity).isNotNull();
         Assertions.assertThat(animeResponseEntity.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
     }


}
