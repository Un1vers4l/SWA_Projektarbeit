package de.hsos.swa.studiom.StudentsManagement.boundary.rest;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;

import org.junit.jupiter.api.Test;

import de.hsos.swa.studiom.StudentsManagement.boundary.dto.Adresse.AdressDTO;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;

@QuarkusTest
public class StudentAddressResourceTest {
    public String token = "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9.eyJpc3MiOiJodHRwOi8vU3R1ZHlPTS5jb20iLCJzdWIiOiIzIiwiaWF0IjoxNjQ0NDM2MTQ2LCJncm91cHMiOlsiVVNFUiIsIlNFS1QiXSwiZXhwIjoxNzY0NDQzNjE0NiwianRpIjoiY2Q3ZTYzMjItYzNmNC00NTNhLWEyNzktZGMwYjgwNWJjMTMyIn0.Y0ucFfsS4tg7GTSHjEUcbUb3QLc8-rYWdcD5QP-rwavYmfXAatMJr8HchKd1O3ontuSOpCqvfFWnyVUIShN_AWZIdIssHjHqZdXNA8egQSRK83pHmFx38bDwk3xqhTm60LjoGsF7ino5vltt5dlIIoVxWZ6Apqgeg90lQnHt4hHk5hui6R18SKY5uLKf7yO2rqzYS3jVi_QiXlHXCCkHyI4YdCLbtWRFAkdX55sd3lF9zP8ZCUqCYUyzKNtWbBLEksJXp1MwGKX-1kTBsYfunDE1W1V09fXxjzS5wPg_rPNNUaPoXwZDQoPbKkb_Lrf9jZCcJtT_ePvLgCiONjsBzA";

    @Test
    void testGetStudentAddress() {
        given()
                .header("Authorization", token)
                .contentType(ContentType.JSON)
                .when().get("/api/v1/student/1000/adress")
                .then()
                .contentType(ContentType.JSON)
                .statusCode(200)
                .body("size()", not(0));
    }

    @Test
    void testDeleteAndPostStudentAddress() {
        given()
                .header("Authorization", token)
                .contentType(ContentType.JSON)
                .when().delete("/api/v1/student/1000/adress")
                .then()
                .statusCode(200);

        AdressDTO adress = new AdressDTO("Sedanstrasse", 8, 49076, "Osnabrueck");
        given()
                .header("Authorization", token)
                .contentType(ContentType.JSON)
                .body(adress)
                .when().post("/api/v1/student/1000/adress")
                .then()
                .contentType(ContentType.JSON)
                .statusCode(200)
                .body("nr", is(adress.nr))
                .body("street", is(adress.street))
                .body("zipCode", is(adress.zipCode))
                .body("town", is(adress.town));
    }

    @Test
    void testPutStudentAddress() {
        AdressDTO adress = new AdressDTO("Sedanstrasse", 8 , 49076, "Osnabrueck");
        given()
                .header("Authorization", token)
                .contentType(ContentType.JSON)
                .body(adress)
                .when().put("/api/v1/student/1001/adress")
                .then()
                .statusCode(200)
                .body("nr", is(adress.nr))
                .body("street", is(adress.street))
                .body("zipCode", is(adress.zipCode))
                .body("town", is(adress.town));
    }
}
