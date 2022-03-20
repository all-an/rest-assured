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

    @Test
	public void mustVerifyFirstLevelAlternativeWays() {
		Response response = RestAssured.request(Method.GET, url + "1");
		
		//path
		System.out.println(response.path("id"));
		assertEquals((Integer)1, response.path("id"));
		assertEquals((Integer)1, response.path("%s", "id"));
		
		//jsonpath
		JsonPath jsonPath = new JsonPath(response.asString());
		assertEquals(1, jsonPath.getInt("id"));
				
		//from
		int id = jsonPath.from(response.asString()).getInt("id");
		assertEquals(1, id);
	}
	
	@Test
	public void mustVerifySecondLevel() {
		given()
		.when()
			.get(url + "2")
		.then()
			.statusCode(200)
			.body("name", containsString("Joaquina"))
			.body("endereco.rua", is("Rua dos bobos"));  // second level rua
	}

}
