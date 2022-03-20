package restassured;

import static org.hamcrest.Matchers.*;


import org.junit.Test;
import static io.restassured.RestAssured.given;

public class UserJsonTest {
	
	@Test
	public void mustVerifyFirstLevel() {
		given()
		.when()
			.get("https://restapi.wcaquino.me/users/1")
		.then()
			.statusCode(200)
			.body("id", is(1))
			.body("name", containsString("Silva"))
			.body("age", greaterThan(18));
	}

}
