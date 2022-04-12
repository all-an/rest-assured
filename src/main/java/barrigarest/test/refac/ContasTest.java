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


public class ContasTest extends BaseTest {
	
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
//	}

	@Test
	public void deveIncluirContaComSucesso() {
		given()
			.body("{ \"nome\": \"Conta inserida\" }")
		.when()
			.post("/contas")
		.then()
			.statusCode(201)
		;
	}
	
	@Test
	public void deveAlterarContaComSucesso() {
		Integer CONTA_ID = BarrigaUtils.getIdContaPeloNome("Conta para alterar");
		
		given()
			.body("{ \"nome\": \"Conta alterada\" }")
			.pathParam("id", CONTA_ID)
		.when()
			.put("/contas/{id}")
		.then()
			.statusCode(200)
			.body("nome", is("Conta alterada"))
		;
	}
	
	@Test
	public void naoDeveInserirContaMesmoNome() {
		given()
			.body("{ \"nome\": \"Conta mesmo nome\" }")
		.when()
			.post("/contas")
		.then()
			.statusCode(400)
			.body("error", is("Já existe uma conta com esse nome!"))
		;
	}
}