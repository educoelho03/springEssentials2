package br.com.study.springEssentials.util;

import br.com.study.springEssentials.domains.domain.Anime;

public class AnimeCreator {

    public static Anime createAnimeToBeSaved(){
        return Anime.builder().name("Dbz").build();
    }

    public static Anime createValidAnime(){
        return Anime.builder()
                .name("Jutjusu")
                .id(1L)
                .build();
    }

    public static Anime createValidUpdatedAnime(){ // estatico pois sao metodos que nao vao ser alterados
        return Anime.builder()
                .name("Demon")
                .id(1L)
                .build();
    }

}
