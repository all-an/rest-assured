
package main.restassured;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertEquals;

import java.util.Arrays;

import org.junit.Test;

import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.internal.ValidatableResponseImpl;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.restassured.response.ValidatableResponseOptions;

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
		given()
		.when()
			.get(url + "2")
		.then()
			.statusCode(200)
			.body("name", containsString("Joaquina"))
			.body("endereco.rua", is("Rua dos bobos"))
		;
	}
	
	@Test
	public void mustAssertList() {
		given()
		.when()
			.get(url + "3")
		.then()
			.statusCode(200)
			.body("name", containsString("Ana"))
			.body("filhos", hasSize(2))
			.body("filhos[0].name", is("Zezinho"))
			.body("filhos[1].name", is("Luizinho"))
			.body("filhos.name", hasItem("Zezinho"))
			.body("filhos.name", hasItems("Zezinho", "Luizinho"))
		;
	}
	
	@Test
	public void mustReturnErrorNoUser() {
		given()
		.when()
			.get(url + "4")
		.then()
			.statusCode(404)
			.body("error", is("Usuário inexistente"))
		;
	}
	
	@Test
	public void mustAssertRootList() {
		given()
		.when()
			.get(url)
		.then()
			.statusCode(200)
			.body("$", hasSize(3)) // $ root list
			.body("", hasSize(3)) // root
			.body("name", hasItems("João da Silva","Maria Joaquina","Ana Júlia"))
			.body("age[1]", is(25))
			.body("filhos.name", hasItem(Arrays.asList("Zezinho" , "Luizinho")))
			.body("salary", contains(1234.5678f, 2500, null))
		;
		
	}
	
	@Test
	public void mustAssertAdvancedVerify() {
		given()
		.when()
			.get(url)
		.then()
			.statusCode(200)
			.body("$", hasSize(3)) // $ root list
			.body("", hasSize(3)) // root
			.body("age.findAll{it <= 25}.size()", is(2))
			.body("findAll{it.age <= 25 && it.age > 20}.name", hasItem("Maria Joaquina"))
			.body("findAll{it.age <= 25}[0].name", is("Maria Joaquina"))
			.body("findAll{it.age <= 25}[-1].name", is("Ana Júlia"))
			.body("find{it.age <= 25}.name", is("Maria Joaquina"))
			.body("findAll{it.name.contains('n')}.name", hasItems("Maria Joaquina","Ana Júlia"))
			.body("findAll{it.name.length() > 10}.name", hasItems("João da Silva", "Maria Joaquina"))
			.body("name.collect{it.toUpperCase()}", hasItem("MARIA JOAQUINA"))
			.body("name.findAll{it.startsWith('Maria')}.collect{it.toUpperCase()}.toArray()", 
					allOf(
							arrayContaining("MARIA JOAQUINA"), 
							arrayWithSize(1)) 
					)
			.body("age.collect{it * 2}", hasItems(60, 50, 40))
			.body("id.max()", is(3))
			.body("salary.min()", is(1234.5678f))
			//.body("salary.findAll{it != null}.sum()", is(3734.5678f)) // error decimal places
			.body("salary.findAll{it != null}.sum()", is(closeTo(3734.5678f, 0.001)))
			.body("salary.findAll{it != null}.sum()", allOf(greaterThan(3000d), lessThan(5000d)))
		;
	}
		
		
	
	
	
	
}
