package br.com.study.springEssentials.domains.requests;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class AnimePostRequestBody {

    @NotEmpty(message = "the anime name cannot be null")
    private String name;
}
