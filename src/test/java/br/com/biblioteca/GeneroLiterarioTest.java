package br.com.biblioteca;

import br.com.biblioteca.domains.genero_literario.GeneroLiterario;
import br.com.biblioteca.domains.genero_literario.GeneroLiterarioRepository;
import br.com.biblioteca.util.ResourceUtils;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class GeneroLiterarioTest extends IntegrationTestConfiguration {

    @Autowired
    private GeneroLiterarioRepository generoLiterarioRepository;

    private String generoLiterarioJson;
    private GeneroLiterario generoLiterario1, generoLiterario2;
    private String generoLiterario1Id, generoLiterario2Id;

    @Before
    public void setUp() {
        super.setUp();
        RestAssured.basePath = "/generos-literarios";
        generoLiterarioJson = ResourceUtils.getContentFromResource("/json/criar-generoLiterario.json");
        prepararDados();
    }

    private void prepararDados() {
        this.generoLiterario1 = new GeneroLiterario("Romance");
        this.generoLiterario2 = new GeneroLiterario("Aventura");
        this.generoLiterario1Id = generoLiterario1.getId().toString();
        this.generoLiterario2Id = generoLiterario2.getId().toString();

        List<GeneroLiterario> generos = List.of(generoLiterario1, generoLiterario2);

        generoLiterarioRepository.saveAll(generos);
    }

    @Test
    public void cadastrarGeneroLiterario_Retornando201CREATED() {
        String payload = generoLiterarioJson
                .replace("{{nome}}", "Romance");

        Response response = given()
                .body(payload)
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .when()
                .post();
        response.then()
                .statusCode(HttpStatus.CREATED.value());

        String id = getIdHeaderLocation(response);

        given()
                .pathParam("generoLiterarioId", id)
                .when()
                .get("/{generoLiterarioId}")
                .then()
                .body("$", hasKey("id"))
                .body("$", hasKey("nome"))
                .body("size()", is(2))
                .body("id", is(id))
                .body("nome", is("Romance"))
                .statusCode(HttpStatus.OK.value());
    }

    @Test
    public void findById_Retornando404_NOTFOUND_QuandoIdIncorreto() {
        given()
                .pathParam("generoLiterarioId", "710a52bf-21bd-496e-9097-a31d58700f59")
                .when()
                .get("/{generoLiterarioId}")
                .then()
                .body("mensagem", is("GÊNERO NÃO ENCONTRADO"))
                .statusCode(HttpStatus.NOT_FOUND.value());
    }

    @Test
    public void findByPage_ParametroNome_Retornando200OK() {
        given()
                .param("orderBy", "nome")
                .param("direction", "ASC")
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .when()
                .get()
                .then()
                .body("totalElements", is(2))
                .body("$", hasKey("content"))
                .body("content", everyItem(hasKey("id")))
                .body("content", everyItem(hasKey("nome")))
                .body("content[0].size()", is(2))
                .body("content[0].id", is(generoLiterario2Id))
                .body("content[0].nome", is("Aventura"))
                .body("content[1].size()", is(2))
                .body("content[1].id", is(generoLiterario1Id))
                .body("content[1].nome", is("Romance"))
                .statusCode(HttpStatus.OK.value());
    }

    @Test
    public void atualizar_Retornando204NOCONTENT() {
        String payload = generoLiterarioJson
                .replace("{{nome}}", "Romance");

        given()
                .pathParam("generoLiterarioId", generoLiterario1Id)
                .body(payload)
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .when()
                .put("/{generoLiterarioId}")
                .then()
                .statusCode(HttpStatus.NO_CONTENT.value());
    }

    @Test
    public void deletar_Retornando204() {
        given()
                .pathParam("generoLiterarioId", generoLiterario1Id)
                .when()
                .delete("/{generoLiterarioId}")
                .then()
                .statusCode(HttpStatus.NO_CONTENT.value());
    }

    @Test
    public void cadastrarGeneroLiterario_Retornando400BADREQUEST_QuandoNomeMaiorQue50Caracteres() {
        String payload = generoLiterarioJson
                .replace("{{nome}}", "Teste para validar quando o nome supera 50 caracteres");

        Response response = given()
                .body(payload)
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .when()
                .post();
        response.then()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .body("mensagem", is("O CAMPO NOME NÃO DEVE CONTER MAIS DO QUE 50 CARACTERES."));
    }

    @Test
    public void cadastrarGeneroLiterario_Retornando400BADREQUEST_QuandoNomeVazio() {
        String payload = generoLiterarioJson
                .replace("{{nome}}", "");

        given()
                .body(payload)
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .when()
                .post()
                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .body("mensagem", is("O CAMPO NOME NÃO DEVE ESTAR EM BRANCO."));
    }

}