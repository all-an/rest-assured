package main.restassured;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import org.junit.Test;

public class UserJsonTest {

	String url = "https://restapi.wcaquino.me/users/";
	
	@Test
	public void mustVerifyFirstLevel() {
		
		given()
		.when()
			.get(url + "1")
		.then()
			.statusCode(200)
			.body("id", is(1))
			.body("name", containsString("Silva"))
			.body("age", greaterThan(18))
		;
		
	}
	
}
