package br.com.study.springEssentials.domains.requests;

import lombok.Data;

@Data
public class AnimePutRequestBody {
    private Long id;
    private String name;

}
