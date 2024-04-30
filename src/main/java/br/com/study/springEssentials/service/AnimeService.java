package br.com.study.springEssentials.service;

import br.com.study.springEssentials.domains.domain.Anime;
import br.com.study.springEssentials.domains.mapper.AnimeMapper;
import br.com.study.springEssentials.repository.AnimeRepository;
import br.com.study.springEssentials.domains.requests.AnimePostRequestBody;
import br.com.study.springEssentials.domains.requests.AnimePutRequestBody;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AnimeService {

    private AnimeRepository animeRepository;
    private AnimeMapper animeMapper;


    public List<Anime> listAll(){
        return animeRepository.findAll();
    }

    public Anime findByIdOrThrowBadRequestExceptionOrThrowBadRequestException(long id){
        return animeRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Anime not found"));
    }

    public Anime save(AnimePostRequestBody animePostRequestBody) {
         return animeRepository.save(animeMapper.toAnime(animePostRequestBody));
    }

    public void delete(Long id){
        animeRepository.deleteById(id);
    }

    public void replace(AnimePutRequestBody animePutRequestBody){
        Anime savedAnime = findByIdOrThrowBadRequestExceptionOrThrowBadRequestException(animePutRequestBody.getId());
        Anime anime = animeMapper.toAnime(animePutRequestBody);
        anime.setId(savedAnime.getId());

        animeRepository.save(anime);
    }
}
