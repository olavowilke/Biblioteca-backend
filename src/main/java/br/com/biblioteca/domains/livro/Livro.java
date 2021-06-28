package br.com.biblioteca.domains.livro;

import br.com.biblioteca.domains.autor.Autor;
import br.com.biblioteca.domains.livro.dto.LivroCriarAtualizarDTO;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@EqualsAndHashCode
@Getter
@Entity
@Table(name = "tb_livro")
@Where(clause = "deleted_at IS NULL")
public class Livro {

    @Id
    @EqualsAndHashCode.Include
    private UUID id;

    private String titulo;
    private LocalDate dataPublicacao;
    private String editora;
    private String generoLiterario;
    private String isbn;

    @OneToOne(fetch = FetchType.LAZY)
    private Autor autor;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;
    private LocalDateTime deletedAt;

    public Livro() {
        this.id = UUID.randomUUID();
    }

    public Livro(LivroCriarAtualizarDTO livroCriarAtualizarDTO, Autor autor) {
        this();
        this.titulo = livroCriarAtualizarDTO.getTitulo();
        this.dataPublicacao = livroCriarAtualizarDTO.getDataPublicacao();
        this.editora = livroCriarAtualizarDTO.getEditora();
        this.generoLiterario = livroCriarAtualizarDTO.getGeneroLiterario();
        this.isbn = livroCriarAtualizarDTO.getIsbn();
        this.autor = autor;
    }

    public Livro(String titulo, LocalDate dataPublicacao, String editora, String generoLiterario, String isbn, Autor autor) {
        this();
        this.titulo = titulo;
        this.dataPublicacao = dataPublicacao;
        this.editora = editora;
        this.generoLiterario = generoLiterario;
        this.isbn = isbn;
        this.autor = autor;
    }

    public void delete() {
        this.deletedAt = LocalDateTime.now();
    }

    public void atualizar(LivroCriarAtualizarDTO livroAtualizarDTO) {
        this.titulo = livroAtualizarDTO.getTitulo();
        this.dataPublicacao = livroAtualizarDTO.getDataPublicacao();
        this.editora = livroAtualizarDTO.getEditora();
        this.generoLiterario = livroAtualizarDTO.getGeneroLiterario();
    }

}