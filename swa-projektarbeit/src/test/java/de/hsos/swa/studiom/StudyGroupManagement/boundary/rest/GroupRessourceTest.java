package de.hsos.swa.studiom.StudyGroupManagement.boundary.rest;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.not;

import org.junit.jupiter.api.Test;

import de.hsos.swa.studiom.StudyGroupManagement.boundary.dto.PostGroupDTO;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;

@QuarkusTest
public class GroupRessourceTest {
    public String token = "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9.eyJtYXROciI6MTAwMCwiaXNzIjoiaHR0cDovL1N0dWR5T00uY29tIiwic3ViIjoiNCIsImlhdCI6MTY0NDQzNjA0OCwiZ3JvdXBzIjpbIlNUVURFTlQiLCJVU0VSIl0sImV4cCI6MTc2NDQ0MzYwNDgsImp0aSI6ImJhMWRhYWJhLWQ5ZjAtNDdiNC04ZWYwLTE1MWJiMzkzNDlhOSJ9.IwvCh9KeT09ezz2LC0usRPrkEjq3lvAjUisPB0hMK7mo0HzBZjJewO6Bb2wtaHBuRF0h6H8F_N8Wa5gyFzylXPVT4AJITIcdCuSIBefZ14i0dqXkzJBlp6t4LJ2eu4WMWcLdaiUJvI-QhYfWU5pzrJL3cTJJ0bHLAruL1UR3g8MKE44Bn5nBVugTqRAyRpvEGqrhj7smytaGYoL-SaFw4--jTpLxRsVNL-RUGZSLt4dy8_TB6GFBmX-HC9dI7qDmFbpSJ2APz8qb_U49dgrS1yuVSExwjFWZS8jOrsrfhQg9QwQGEYG9tq7QXVqWEawESijXuew7CkWzKfbhjaj7eQ";

    @Test
    void testGetAllGroups() {
        given()
                .header("Authorization", token)
                .contentType(ContentType.JSON)
                .when().get("/api/v1/group")
                .then()
                .contentType(ContentType.JSON)
                .statusCode(200)
                .body("size()", not(0));
    }
    @Test
    void testPostGroup() {

        PostGroupDTO gDTO = new PostGroupDTO(1000, "SWA  lern gruppe", 10 ,1);
        given()
                .header("Authorization", token)
                .body(gDTO)
                .contentType(ContentType.JSON)
                .when().post("/api/v1/group")
                .then()
                .contentType(ContentType.JSON)
                .statusCode(200)
                .body("size()", not(0));
    }
}
