package de.hsos.swa.studiom.UserManagement.boundary.rest;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.not;

import org.junit.jupiter.api.Test;

import de.hsos.swa.studiom.UserManagement.boundary.dto.AuthDto;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;

@QuarkusTest
public class AuthRestTest {
    @Test
    public void testGetUser() {

        AuthDto authDto = new AuthDto("student", "student");
        given()
                .body(authDto)
                .contentType(ContentType.JSON)
                .when().post("api/v1/login")
                .then()
                .contentType(ContentType.JSON)
                .statusCode(200)
                .body("success", not(false));
    }
}
