package br.com.study.springEssentials.service;

import br.com.study.springEssentials.domain.Anime;
import br.com.study.springEssentials.repository.AnimeRepository;
import br.com.study.springEssentials.requests.AnimePostRequestBody;
import br.com.study.springEssentials.requests.AnimePutRequestBody;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Service
@RequiredArgsConstructor
public class AnimeService {

    private AnimeRepository animeRepository;


    public List<Anime> listAll(){
        return animeRepository.findAll();
    }

    public Anime findByIdOrThrowBadRequestExceptionOrThrowBadRequestException(long id){
        return animeRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Anime not found"));
    }

    public Anime save(AnimePostRequestBody animePostRequestBody) {
         return animeRepository.save(Anime.builder().name(animePostRequestBody.getName()).build());
    }

    public void delete(Long id){
        animeRepository.deleteById(id);
    }

    public void replace(AnimePutRequestBody animePutRequestBody){
        Anime savedAnime = findByIdOrThrowBadRequestExceptionOrThrowBadRequestException(animePutRequestBody.getId());

        Anime anime = Anime.builder()
                        .id(savedAnime.getId())
                        .name(savedAnime.getName())
                        .build();

        animeRepository.save(anime);
    }
}
