package br.com.study.springEssentials.client;

import br.com.study.springEssentials.SpringEssentialsApplication;
import br.com.study.springEssentials.domains.domain.Anime;
import lombok.extern.log4j.Log4j2;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
        ResponseEntity<Anime> samuraiSaved = new RestTemplate().exchange("http://localhost:8080/animes/{id}", HttpMethod.POST, new HttpEntity<>(kingdom) , Anime.class);
    }




}
