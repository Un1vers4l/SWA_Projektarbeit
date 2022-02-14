package de.hsos.swa.studiom.UserManagement.boundary.rest;

import org.junit.jupiter.api.Test;

import de.hsos.swa.studiom.UserManagement.boundary.dto.ChangePasswordDto;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
public class UserIDRestTest {
    public String token = "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9.eyJpc3MiOiJodHRwOi8vU3R1ZHlPTS5jb20iLCJzdWIiOiIyIiwiaWF0IjoxNjQ0NDM2MTAzLCJncm91cHMiOlsiQURNSU4iLCJVU0VSIl0sImV4cCI6MTc2NDQ0MzYxMDMsImp0aSI6ImJhMWIxMmViLWI2ZDUtNDYwZS1hZWUwLTM0NDU2NmU1ODU2OSJ9.KIQhZnaTM9HIRNWHmuHA0cfj1Di8qUfxWdoKMifSfFlGR8RFXn6BLEAnxGvWQd6gVM1mQUfL09zo2076B2ownIPQ_F9xMUBuOL9dnO0JqcNglnTlMhngpeCJ75E4WuToHSrPXiBPnFf4H1qMqgXarAoCGQ2CongiODCfLiAE2Zt8afYUDUsjPwGa5V5T4GF4lA1ORL75U4UBgw0k0uihFnpSTwkpt99fjRTnjLTEn2nQCCO37bxQlUk77A8BTM62SPCOwls1oTxQvhISjpynHRaVhXHC0sSH5dL-ytY_5OtQeexLNnTjc7wU44swaWIKyKdl7t5svflWPV_ZmCXq3g";

    @Test
    public void testGetUser() {
        given()
                .header("Authorization", token)
                .when().get("api/v1/User/3")
                .then()
                .contentType(ContentType.JSON)
                .statusCode(200)
                .body("username", is("sekt"));
    }

    @Test
    public void testPutUser() {
        ChangePasswordDto password = new ChangePasswordDto("123");

        given()
                .body(password)
                .header("Authorization", token)
                .contentType(ContentType.JSON)
                .when().put("api/v1/User/4")
                .then()
                .contentType(ContentType.JSON)
                .statusCode(200)
                .body("success", is(true));
    }

    @Test
    public void testDeleteUser() {
        given()
                .header("Authorization", token)
                .contentType(ContentType.JSON)
                .when().delete("api/v1/User/3")
                .then()
                .contentType(ContentType.JSON)
                .statusCode(200)
                .body("success", is(true));

        given()
                .header("Authorization", token)
                .when().get("api/v1/User/3")
                .then()
                .statusCode(404);
    }
}
