package de.hsos.swa.studiom.StudentsManagement.boundary.rest;

import javax.ws.rs.core.MediaType;

import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;
import static io.restassured.RestAssured.given;

@QuarkusTest
public class StudentRessourceTest {
    @Test
    void testCreateStudent() {
        given().contentType(MediaType.APPLICATION_JSON).body("Student2")
                .when().put("/api/v1/student")
                .then()
                .statusCode(200);
    }

    @Test
    void testGetAllStudent() {
        given().contentType(MediaType.APPLICATION_JSON)
                .when().get()
                .then()
                .statusCode(200);
    }
}
