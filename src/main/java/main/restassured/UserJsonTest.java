
package main.restassured;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

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
	
	@Test
	public void mustVerifyFirstLevelOtherWays() {
		Response response = RestAssured.request(Method.GET, url + "1");
		
		//path
		System.out.println(response.path("id"));
		
		assertEquals(Integer.valueOf(1), response.path("id"));
		assertEquals(Integer.valueOf(1), response.path("%s" , "id"));
	
		//jsonpath
		JsonPath jpath = new JsonPath(response.asString());
		assertEquals(1, jpath.getInt("id"));
		
		//from 
		int id = JsonPath.from(response.asString()).getInt("id");
		assertEquals(1, id);
	}
	
	@Test
	public void mustVerifySecondLevel() {
		
	}
	
}
