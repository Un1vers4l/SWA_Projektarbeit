package de.hsos.swa.studiom;


import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;


public class GreetingResourceTest {

    public void testHelloEndpoint() {
        given()
          .when().get("/hello")
          .then()
             .statusCode(200)
             .body(is("Hello RESTEasy"));
    }

}