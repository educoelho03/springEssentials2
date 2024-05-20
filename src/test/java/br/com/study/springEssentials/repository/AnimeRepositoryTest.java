package br.com.study.springEssentials.repository;

import br.com.study.springEssentials.domains.domain.Anime;
import lombok.extern.log4j.Log4j2;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

@DataJpaTest
@DisplayName("Tests for Anime repository")
@Log4j2
class AnimeRepositoryTest {

    @Autowired
    private AnimeRepository animeRepository;

    @Test
    @DisplayName("Save persists anime when Successuful")
    void save_PersistAnime_WhenSuccessuful(){

        Anime animeToBeSaved = createAnime();
        Anime animeSaved = this.animeRepository.save(animeToBeSaved);
        Assertions.assertThat(animeSaved).isNotNull();
        Assertions.assertThat(animeSaved.getId()).isNotNull();
        Assertions.assertThat(animeSaved.getName()).isEqualTo(animeToBeSaved.getName());
    }

    @Test
    @DisplayName("Save updated anime when successful")
    void save_UpdatedAnimes_WhenSuccessful(){

        Anime animeToBeUpdated = createAnime();
        Anime animeSaved = this.animeRepository.save(animeToBeUpdated);

        animeSaved.setName("Jujutsu Kaisen");
        Anime animeUpdated = this.animeRepository.save(animeSaved);

        Assertions.assertThat(animeUpdated).isEqualTo(animeSaved);

    }

    @Test
    @DisplayName("Delete anime when successful")
    void delete_RemoveAnime_WhenSuccessful(){

        Anime animeToBeSaved = createAnime();
        Anime animeSaved =  this.animeRepository.save(animeToBeSaved);

        this.animeRepository.delete(animeSaved);

        Optional<Anime> animeOptional = this.animeRepository.findById(animeSaved.getId());
        Assertions.assertThat(animeOptional).isEmpty();
    }

    @Test
    @DisplayName("Find by name return list of anime when sucessful")
    void findByName_ReturnListOfAnime_WhenSuccessful(){

        Anime animeToBeSaved = createAnime();
        Anime animeSaved = this.animeRepository.save(animeToBeSaved);

        String name = animeSaved.getName();
        List<Anime> listAnime = this.animeRepository.findByName(name);

        Assertions.assertThat(listAnime).isNotEmpty();
        Assertions.assertThat(listAnime).contains(animeSaved);
    }

    @Test
    @DisplayName("Find by name returns empty list of anime when no anime is found")
    void findByName_ReturnEmptyList_WhenAnimeIsNotFound(){
        List<Anime> animes = this.animeRepository.findByName("xaxa");

        Assertions.assertThat(animes).isEmpty();
    }

    private Anime createAnime(){
        return Anime.builder().name("Dbz").build();
    }
}