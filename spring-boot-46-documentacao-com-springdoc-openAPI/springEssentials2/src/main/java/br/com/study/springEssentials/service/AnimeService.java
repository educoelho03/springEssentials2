package br.com.study.springEssentials.service;

import br.com.study.springEssentials.domains.domain.Anime;
import br.com.study.springEssentials.domains.mapper.AnimeMapper;
import br.com.study.springEssentials.exceptions.BadRequestException;
import br.com.study.springEssentials.repository.AnimeRepository;
import br.com.study.springEssentials.domains.requests.AnimePostRequestBody;
import br.com.study.springEssentials.domains.requests.AnimePutRequestBody;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AnimeService {

    private final AnimeRepository animeRepository;
    private final AnimeMapper animeMapper;

    public Page<Anime> listAll(Pageable pageable) {
        return animeRepository.findAll(pageable);
    }

    public List<Anime> listAllNonPageable(){
        return animeRepository.findAll();
    }

    public Anime findByIdOrThrowBadRequestException(long id){
        return animeRepository.findById(id)
                .orElseThrow(() -> new BadRequestException("Anime not found"));
    }

    public List<Anime> findByName(String name){
        return animeRepository.findByName(name);
    }

    @Transactional
    public Anime save(AnimePostRequestBody animePostRequestBody) {
         return animeRepository.save(animeMapper.toAnime(animePostRequestBody));
    }

    public void delete(Long id){
        animeRepository.deleteById(id);
    }

    public void replace(AnimePutRequestBody animePutRequestBody){
        Anime savedAnime = findByIdOrThrowBadRequestException(animePutRequestBody.getId());
        Anime anime = animeMapper.toAnime(animePutRequestBody);
        anime.setId(savedAnime.getId());

        animeRepository.save(anime);
    }
}
