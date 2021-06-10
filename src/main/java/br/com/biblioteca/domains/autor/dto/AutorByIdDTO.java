package br.com.biblioteca.domains.autor.dto;

import br.com.biblioteca.domains.autor.Autor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@NoArgsConstructor
public class AutorByIdDTO {

    private UUID id;
    private String nome;
    private String nacionalidade;
    private LocalDate dataNascimento;

    public AutorByIdDTO(Autor autor) {
        this.id = autor.getId();
        this.nome = autor.getNome();
        this.nacionalidade = autor.getNacionalidade();
        this.dataNascimento = autor.getDataNascimento();
    }

}
