package restassuredJUnit5;

import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.*;

public class TestHTTPRequests {

    @Test
    public void deveDeserializarXMLAoSalvarUsuario() {
        User user = new User("Usuario XML", 40);

        User usuarioInserido = given()
                .log().all()
                .contentType(ContentType.XML)
                .body(user)//
                .when()
                .post("https://restapi.wcaquino.me/usersXML")
                .then()
                .log().all()//
                .statusCode(201)
                .extract().body().as(User.class);

        assertNotNull(usuarioInserido.getId());
    }
    
    
}
