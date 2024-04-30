package br.com.study.springEssentials.controller;

import br.com.study.springEssentials.domain.Anime;
import br.com.study.springEssentials.requests.AnimePostRequestBody;
import br.com.study.springEssentials.requests.AnimePutRequestBody;
import br.com.study.springEssentials.service.AnimeService;
import br.com.study.springEssentials.util.DateUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController // server para identificar que isso é um controller e vai adicionar o ResponseBody que todos os metodos vao retornar String
@RequestMapping("animes") // por padrao é sempre no plural
@Log4j2
@RequiredArgsConstructor // vai criar um construtor com os atributos que estao final
//@AllArgsConstructor cria um construtor com todas as atributos que temos na classe
//@Controller  funciona da mesma maneira mas nao tem o ResponseBody, entao teria que adicionar para cada método um @ResponseBody
public class AnimeController {

    private final DateUtil dateUtil;
    private final AnimeService animeService;

    @GetMapping
    public ResponseEntity<List<Anime>> list(){
        log.info(dateUtil.formatLocalDateTimeToDatabaseStyle(LocalDateTime.now()));
        return new ResponseEntity<>(animeService.listAll(), HttpStatus.OK); // Para retornar o status da aplicação usamos o ResponseEntity
    }

    @GetMapping("/{id}")
    public ResponseEntity<Anime> findById(@PathVariable Long id){
        return new ResponseEntity<>(animeService.findByIdOrThrowBadRequestExceptionOrThrowBadRequestException(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Anime> save(@RequestBody AnimePostRequestBody animePostRequestBody){
        return new ResponseEntity<>(animeService.save(animePostRequestBody), HttpStatus.CREATED);
    }

    @DeleteMapping
    public ResponseEntity<Void> delete(@PathVariable Long id){
        animeService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping
    public ResponseEntity<Anime> replace(@RequestBody AnimePutRequestBody animePutRequestBody){
        animeService.replace(animePutRequestBody);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}