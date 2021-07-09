package br.com.biblioteca;

import br.com.biblioteca.domains.editora.Editora;
import br.com.biblioteca.domains.editora.EditoraRepository;
import br.com.biblioteca.util.ResourceUtils;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class EditoraTest extends IntegrationTestConfiguration {

    @Autowired
    private EditoraRepository editoraRepository;

    private String editoraJson;
    private Editora editora1, editora2;
    private String editora1Id, editora2Id;

    @Before
    public void setUp() {
        super.setUp();
        RestAssured.basePath = "/editoras";
        editoraJson = ResourceUtils.getContentFromResource("/json/criar-editora.json");
        prepararDados();
    }

    private void prepararDados() {
        Editora editora1 = new Editora("Panini");
        this.editora1 = editoraRepository.save(editora1);
        this.editora1Id = editora1.getId().toString();

        Editora editora2 = new Editora("Abril");
        this.editora2 = editoraRepository.save(editora2);
        this.editora2Id = editora2.getId().toString();
    }

    @Test
    public void cadastrarEditora_Retornando201CREATED() {
        String payload = editoraJson
                .replace("{{nome}}", "Abril");

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
                .pathParam("editoraId", id)
                .when()
                .get("/{editoraId}")
                .then()
                .body("size()", is(2))
                .body("$", hasKey("id"))
                .body("$", hasKey("nome"))
                .body("id", is(id))
                .body("nome", is("Abril"))
                .statusCode(HttpStatus.OK.value());
    }

    @Test
    public void findById_Retornando404_NOTFOUND_QuandoIdIncorreto() {
        given()
                .pathParam("editoraId", "710a52bf-21bd-496e-9097-a31d58700f59")
                .when()
                .get("/{editoraId}")
                .then()
                .body("mensagem", is("EDITORA NÃO ENCONTRADA"))
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
                .body("content[0].id", is(editora2.getId().toString()))
                .body("content[0].nome", is("Abril"))
                .body("content[1].size()", is(2))
                .body("content[1].id", is(editora1.getId().toString()))
                .body("content[1].nome", is("Panini"))
                .statusCode(HttpStatus.OK.value());
    }

    @Test
    public void atualizar_Retornando204NOCONTENT() {
        String payload = editoraJson
                .replace("{{nome}}", "Teste1");

        given()
                .pathParam("clienteId", editora1.getId().toString())
                .body(payload)
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .when()
                .put("/{clienteId}")
                .then()
                .statusCode(HttpStatus.NO_CONTENT.value());
    }

    @Test
    public void deletar_Retornando204() {
        given()
                .pathParam("clienteId", editora1.getId().toString())
                .when()
                .delete("/{clienteId}")
                .then()
                .statusCode(HttpStatus.NO_CONTENT.value());
    }

    @Test
    public void dropdown_Retornando200OK() {
        given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .when()
                .get("/dropdown")
                .then()
                .body("$", everyItem(hasKey("id")))
                .body("$", everyItem(hasKey("nome")))
                .body("[0].size()", is(2))
                .body("[0].id", is(editora1Id))
                .body("[0].nome", is("Panini"))
                .body("[1].size()", is(2))
                .body("[1].id", is(editora2Id))
                .body("[1].nome", is("Abril"))
                .statusCode(HttpStatus.OK.value());
    }

}
