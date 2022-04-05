package main.restassured;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import org.junit.Test;

public class UserXMLTest {

	@Test
	public void testLearningXMLtestingUser3() {
		given()
		.when()
			.get("https://restapi.wcaquino.me/usersXML/3")
		.then()
			.statusCode(200)
			.body("user.name", is("Ana Julia"))
			.body("name.@id", is("3"))
			.body("user.filhos.name.size()", is(2))
			.body("user.filhos.name[0]", is("Zezinho"))
			.body("user.filhos.name[1]", is("Luizinho"))
			.body("user.filhos.name", hasItem("Luizinho"))
			.body("user.filhos.name", hasItems("Zezinho","Luizinho"))
		;
	}
	
}
