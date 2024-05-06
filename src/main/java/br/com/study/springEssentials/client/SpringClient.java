package br.com.study.springEssentials.client;

import br.com.study.springEssentials.domains.domain.Anime;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

@Log4j2
public class SpringClient {
    ResponseEntity<Anime> entity = new RestTemplate().getForEntity("http://localhost:8080/animes/{id}", Anime.class);
    Anime object = new RestTemplate().getForObject("http://localhost:8080/animes/{id}", Anime.class);
}
