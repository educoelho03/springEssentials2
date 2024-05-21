package br.com.study.springEssentials.domains.requests;

import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AnimePostRequestBody {

    @NotEmpty(message = "the anime name cannot be null")
    private String name;
}
