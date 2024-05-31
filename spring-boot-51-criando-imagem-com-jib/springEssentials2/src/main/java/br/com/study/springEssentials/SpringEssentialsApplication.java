package br.com.study.springEssentials;

import lombok.extern.log4j.Log4j2;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@Log4j2
public class SpringEssentialsApplication {
	public static void main(String[] args) {
		log.info("Iniciando a minha aplicação aula 39 e 40.");
		SpringApplication.run(SpringEssentialsApplication.class, args);
		log.info("API de estudos criada com sucesso.");

	}

}
