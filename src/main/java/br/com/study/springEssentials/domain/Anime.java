package br.com.study.springEssentials.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

@Data
@AllArgsConstructor
public class Anime {
    private Long id;
    private String name;
}