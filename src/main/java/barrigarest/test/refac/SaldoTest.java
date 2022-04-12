package barrigarest.test.refac;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;

import java.util.HashMap;
import java.util.Map;

import org.junit.BeforeClass;
import org.junit.Test;

import barrigarest.core.BaseTest;
import barrigarest.utils.BarrigaUtils;
import io.restassured.RestAssured;

public class SaldoTest extends BaseTest {
	
//	@BeforeClass
//	public static void login() {
//		Map<String, String> login = new HashMap<String, String>();
//		login.put("email", "allan@allan.com");
//		login.put("senha", "123456");
//		
//		String TOKEN = given()
//			.body(login)
//		.when()
//			.post("/signin")
//		.then()
//			.statusCode(200)
//			.extract().path("token");
//		
//		RestAssured.requestSpecification.header("Authorization", "JWT " + TOKEN);
//		
//		RestAssured.get("/reset").then().statusCode(200);
//	}

	@Test
	public void deveCalcularSaldoContas() {
		Integer CONTA_ID = BarrigaUtils.getIdContaPeloNome("Conta para saldo");
		
		given()
		.when()
			.get("/saldo")
		.then()
			.statusCode(200)
			.body("find{it.conta_id == "+CONTA_ID+"}.saldo", is("534.00"))
		;
	}
}