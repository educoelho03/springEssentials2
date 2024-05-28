package br.com.study.springEssentials.domains.mapper;

import br.com.study.springEssentials.domains.domain.Anime;
import br.com.study.springEssentials.domains.requests.AnimePostRequestBody;
import br.com.study.springEssentials.domains.requests.AnimePutRequestBody;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring") // facilitar o uso de injeção d dependencia
public abstract class AnimeMapper {

    public abstract Anime toAnime(AnimePostRequestBody animePostRequestBody);
    public abstract Anime toAnime(AnimePutRequestBody animePutRequestBody);
}
