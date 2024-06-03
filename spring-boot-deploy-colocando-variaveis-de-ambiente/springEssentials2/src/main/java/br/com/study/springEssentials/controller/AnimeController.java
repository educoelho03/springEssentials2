package br.com.study.springEssentials.controller;

import br.com.study.springEssentials.domains.domain.Anime;
import br.com.study.springEssentials.domains.requests.AnimePostRequestBody;
import br.com.study.springEssentials.domains.requests.AnimePutRequestBody;
import br.com.study.springEssentials.service.AnimeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController // server para identificar que isso é um controller e vai adicionar o ResponseBody que todos os metodos vao retornar String
@RequestMapping("animes") // por padrao é sempre no plural
@Log4j2
@RequiredArgsConstructor // vai criar um construtor com os atributos que estao final
//@AllArgsConstructor cria um construtor com todas as atributos que temos na classe
//@Controller  funciona da mesma maneira mas nao tem o ResponseBody, entao teria que adicionar para cada método um @ResponseBody
public class AnimeController {

    private final AnimeService animeService;

    @GetMapping
    @Operation(summary = "List all animes paginated", description = "the default size is 20, use the parameter size to change the default value",
    tags = {"anime"})
    public ResponseEntity<Page<Anime>> list(@ParameterObject Pageable pageable){
        return new ResponseEntity<>(animeService.listAll(pageable), HttpStatus.OK); // Para retornar o status da aplicação usamos o ResponseEntity
    }

    @GetMapping("/all")
    public ResponseEntity<List<Anime>> listAll(){
        return new ResponseEntity<>(animeService.listAllNonPageable(), HttpStatus.OK); // Para retornar o status da aplicação usamos o ResponseEntity
    }

    @GetMapping("/{id}")
    public ResponseEntity<Anime> findById(@PathVariable Long id){
        return new ResponseEntity<>(animeService.findByIdOrThrowBadRequestException(id), HttpStatus.OK);
    }

    @GetMapping("/admin/by-id/{id}")
    public ResponseEntity<Anime> findByIdAuthenticationPrincipal(@PathVariable Long id,
                                                                 @AuthenticationPrincipal UserDetails userDetails){ // pegar os dados de quem esta autenticado
        log.info(userDetails);
        return new ResponseEntity<>(animeService.findByIdOrThrowBadRequestException(id), HttpStatus.OK);
    }

    @GetMapping("/find")
    public ResponseEntity<List<Anime>> findByName(@RequestParam String name){ // Usa se requestParam quando queremos passar parametros diretamente na URL
        return new ResponseEntity<>(animeService.findByName(name), HttpStatus.OK);

    }

    @PostMapping
    public ResponseEntity<Anime> save(@RequestBody @Valid AnimePostRequestBody animePostRequestBody){
        return new ResponseEntity<>(animeService.save(animePostRequestBody), HttpStatus.CREATED);
    }

    @DeleteMapping("/admin/by-id/{id}")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Successful Operation"),
            @ApiResponse(responseCode = "400", description = "When anime does not exist in the Databsae")
    })
    public ResponseEntity<Void> delete(@PathVariable Long id){
        animeService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping
    public ResponseEntity<Void> replace(@RequestBody AnimePutRequestBody animePutRequestBody){
        animeService.replace(animePutRequestBody);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}