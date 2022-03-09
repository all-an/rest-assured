package main.restassured;

import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.response.Response;

public class HiRestAssured {
	
	public static void main(String[] args) {
		Response response = RestAssured.request(Method.GET, "https://restapi.wcaquino.me/ola");
		System.out.println(response.getBody().asString());
	}

}
