package br.com.alura.screenmatch.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DadosSerie(@JsonAlias("Title") String titulo,
                         @JsonAlias("totalSeasons") Integer totalTemporadas,
                         @JsonAlias("imdbRating") String avaliacao) {
}
/**
 * @JsonIgnoreProperties = anotação para ignorar o que eu não precisar no json
 * @JsonIgnoreProperties(ignoreUnknown = true) = por default vem falso e tenta converter todos os
 *     campos no json
 *
 */
