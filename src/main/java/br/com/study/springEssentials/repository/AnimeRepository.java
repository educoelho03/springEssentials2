package br.com.study.springEssentials.repository;

import br.com.study.springEssentials.domain.Anime;

import java.util.List;

public interface AnimeRepository{
    List<Anime> listAll();
}
