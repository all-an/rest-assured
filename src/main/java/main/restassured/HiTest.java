package main.restassured;

import static io.restassured.RestAssured.get;
import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.request;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.anyOf;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.endsWith;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.startsWith;
import static org.hamcrest.CoreMatchers.*;

import java.util.Arrays;
import java.util.List;

import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Test;

import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;

public class HiTest {
	
	public String url = "https://restapi.wcaquino.me/";

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
	
	@Test
	public void matchersComHamcrest() {
		MatcherAssert.assertThat("Maria", Matchers.is("Maria"));
		MatcherAssert.assertThat(128, Matchers.is(128));
		MatcherAssert.assertThat(128, Matchers.isA(Integer.class));
		MatcherAssert.assertThat(987, Matchers.isA(Integer.class));
		MatcherAssert.assertThat(987d, Matchers.greaterThan(120d));
		MatcherAssert.assertThat(987d, Matchers.lessThan(1000d));
		
		List<Integer> impares = Arrays.asList(1,3,5,7,9);
		
		assertThat(impares, hasSize(5));
		assertThat(impares, contains(1,3,5,7,9));
		assertThat(impares, containsInAnyOrder(1,5,3,9,7));
		assertThat(impares, hasItem(1));
		assertThat(impares, hasItems(1,5));
		
		assertThat("Maria", is(not("João")));
		assertThat("Maria", not("João"));
		assertThat("Luiz", anyOf(is("Luiz"), is("Joaquina")));
		assertThat("Joaquina", allOf(startsWith("Joa"), endsWith("ina"), containsString("qui")));
	}
	
	@Test
	public void deveValidarBody() {
		given()
		.when()
			.get(url + "ola")
		.then()
			.statusCode(200)
			.body(is("Ola Mundo!"))
			.body(containsString("Mundo"))
			.body(is(not(nullValue())));
	}
	
}
