import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.internal.path.xml.NodeImpl;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.junit.jupiter.api.*;

import java.util.ArrayList;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UserXMLTestUsingStaticAtributes{

        public static RequestSpecification reqSpec;
        public static ResponseSpecification resSpec;

        @BeforeAll
        public static void setup() {
            RestAssured.baseURI = "https://restapi.wcaquino.me";
//		RestAssured.port = 443;
//		RestAssured.basePath = "";

            RequestSpecBuilder reqBuilder = new RequestSpecBuilder();
            reqBuilder.log(LogDetail.ALL);
            reqSpec = reqBuilder.build();

            ResponseSpecBuilder resBuilder = new ResponseSpecBuilder();
            resBuilder.expectStatusCode(200);
            resSpec = resBuilder.build();

            RestAssured.requestSpecification = reqSpec;
            RestAssured.responseSpecification = resSpec;
        }

        @Test
        @Order(1)
        public void testLearningXMLtesting() {

//		RestAssured.baseURI = "https://restapi.wcaquino.me"; //acima
//		RestAssured.port = 443;
//		RestAssured.basePath = "/v2";

//		RestAssured.baseURI = "http://restapi.wcaquino.me";
//		RestAssured.port = 80;

            given()
//			.spec(reqSpec)
			.when()
//			.get("/users")
                    .get("/usersXML/3")
                    .then()
//			.statusCode(200)
//			.spec(resSpec)

                    .rootPath("user")
                    .body("name", is("Ana Julia"))
                    .body("@id", is("3"))

                    .rootPath("/user.filhos")
                    .body("name.size()", is(2))

                    .detachRootPath("filhos")
                    .body("filhos.name[0]", is("Zezinho"))
                    .body("filhos.name[1]", is("Luizinho"))

                    .appendRootPath("filhos")
                    .body("name", hasItem("Luizinho"))
                    .body("name", hasItems("Zezinho","Luizinho"))
            ;
        }

        @Test
        @Order(2)
        public void testAdvancedSearchXML() {
            given()
                    .when()
                    .get("/usersXML")
                    .then()
//			.statusCode(200)
                    .body("user.user.size()", is(3))
                    .body("user.user.findAll{it.age.toInteger() <= 25}.size()", is(2))
                    .body("users.user.@id", hasItems("1", "2", "3"))
                    .body("users.user.find{it.age == 25}.name", is("Maria Joaquina"))
                    .body("users.user.findAll{it.name.toString().contains('n')}.name",hasItems("Maria Joaquina", "Ana Julia"))
                    .body("users.user.salary.find{it != null}.toDouble()", is(1234.5678d))
                    .body("users.user.age.collect{it.toInteger() * 2}", hasItems(40, 50, 60))
                    .body("users.user.name.findAll{it.toString().startsWith('Maria')}.collect{it.toString().toUpperCase()}", is("MARIA JOAQUINA"))

            ;
        }

        @Test
        @Order(3)
        public void testMustDoAdvancedSearchWithXMLnJava() {
            ArrayList<NodeImpl> nomes = given()
                    .when()
                    .get("/usersXML")
                    .then()
//			.statusCode(200)
                    .extract().path("users.user.name.findAll{it.toString().contains('n')}");
            ;
            Assertions.assertEquals(2, nomes.size());
            Assertions.assertEquals("Maria Joaquina".toUpperCase(), nomes.get(0).toString().toUpperCase());
            Assertions.assertTrue("ANA JULIA".equalsIgnoreCase(nomes.get(1).toString()));
        }

        @Test
        @Order(4)
        public void testAdvancedSearchWithXPath() {
            given()
                    .when()
                    .get("/usersXML")
                    .then()
//			.statusCode(200)
                    .body(hasXPath("count(/users/user)", is("3")))
                    .body(hasXPath("/users/user[@id = '1']"))
                    .body(hasXPath("//user[@id = '2']"))
                    .body(hasXPath("//name[text() = 'Luizinho']/../../name", is("Ana Julia")))
                    .body(hasXPath("//name[text() = 'Ana Julia']/following-sibling::filhos", allOf(containsString("Zezinho"), containsString("Luizinho"))))
                    .body(hasXPath("/users/user/name", is("João da Silva")))
                    .body(hasXPath("//name", is("João da Silva")))
                    .body(hasXPath("/users/user[2]/name", is("Maria Joaquina")))
                    .body(hasXPath("/users/user[last()]/name", is("Ana Julia")))
                    .body(hasXPath("count(/users/user/name[contains(., 'n')])", is("2")))
                    .body(hasXPath("//user[age < 24]/name", is("Ana Julia")))
                    .body(hasXPath("//user[age > 20 and age < 30]/name", is("Maria Joaquina")))
                    .body(hasXPath("//user[age > 20][age < 30]/name", is("Maria Joaquina")))
            ;
        }



}
