package main.restassured;

import static io.restassured.RestAssured.*;

import org.junit.Assert;
import org.junit.Test;

import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;

public class HiTest {

	@Test
	public void testHiWorld() {
		
		Response response = RestAssured.request(Method.GET, "https://restapi.wcaquino.me/ola");
		System.out.println(response.getBody().asString().equals("Ola Mundo!"));
		System.out.println(response.statusCode() == 200);
		
		System.out.println(response.getBody().asString().equals("Olá Teste Erro!"));
		System.out.println(response.statusCode() == 201);
		
		//Assert.assertTrue(response.getBody().asString().equals("Olá Teste Erro!"));
		
		Assert.assertEquals(200, response.getStatusCode());
		
		ValidatableResponse validacao = response.then();
		validacao.statusCode(200);
		
	}
	
	@Test
	public void outrasFormasRestAssured() {
		
		String url = "https://restapi.wcaquino.me/ola";
		
		Response response = request(Method.GET, url);
		
		ValidatableResponse validacao = response.then();
		validacao.statusCode(200);
		
		get(url).then().statusCode(200);
		
		given()
		//pré condições
		.when().get(url)
		//verificações
		.then()
		// .assertThat() //opcional
		.statusCode(201);
		
	}
	
}
