package br.com.study.springEssentials.client;

import br.com.study.springEssentials.domains.domain.Anime;
import lombok.extern.log4j.Log4j2;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Log4j2
public class SpringClient {
    public static void main(String[] args) {
        ResponseEntity<Anime> entity = new RestTemplate().getForEntity("http://localhost:8080/animes/{id}", Anime.class, 2);
        Anime object = new RestTemplate().getForObject("http://localhost:8080/animes/{id}", Anime.class, 2);

        log.info(entity.getBody());

        ResponseEntity<List<Anime>> animeList = new RestTemplate().exchange("http://localhost:8080/animes/{id}", HttpMethod.GET, null,
                new ParameterizedTypeReference<>() {});

        log.info(animeList.getBody());

        Anime kingdom = Anime.builder().name("Kingdom").build();
        Anime kingdomSaved = new RestTemplate().postForObject("http://localhost:8080/animes/{id}", kingdom, Anime.class);

        log.info("Saved anime {}", kingdomSaved);

        Anime samurai = Anime.builder().name("Samurai").build();
        ResponseEntity<Anime> samuraiSaved = new RestTemplate().exchange("http://localhost:8080/animes/{id}", HttpMethod.POST, new HttpEntity<>(samurai) , Anime.class);


        Anime animeToBeUpdated = samuraiSaved.getBody();
        animeToBeUpdated.setName("Dragon Ball z");

        ResponseEntity<Void> samuraiUpdated = new RestTemplate().exchange("http://localhost:8080/animes/", HttpMethod.PUT, new HttpEntity<>(animeToBeUpdated, createJsonHeader()), Void.class);
        log.info(samuraiUpdated);

        ResponseEntity<Void> samuraiDeleted = new RestTemplate().exchange("http://localhost:8080/animes/{id}", HttpMethod.DELETE, new HttpEntity<>(animeToBeUpdated, createJsonHeader()), Void.class, animeToBeUpdated.getId());
        log.info(samuraiDeleted);
    }

    private static HttpHeaders createJsonHeader(){
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        return httpHeaders;
    }
}