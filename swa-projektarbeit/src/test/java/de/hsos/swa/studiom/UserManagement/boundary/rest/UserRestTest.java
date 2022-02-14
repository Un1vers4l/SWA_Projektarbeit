package de.hsos.swa.studiom.UserManagement.boundary.rest;

import org.junit.jupiter.api.Test;

import de.hsos.swa.studiom.UserManagement.boundary.dto.PostUserDto;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;

import java.util.ArrayList;
import java.util.List;

@QuarkusTest
public class UserRestTest {
  public String token = "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9.eyJpc3MiOiJodHRwOi8vU3R1ZHlPTS5jb20iLCJzdWIiOiIyIiwiaWF0IjoxNjQ0NDM2MTAzLCJncm91cHMiOlsiQURNSU4iLCJVU0VSIl0sImV4cCI6MTc2NDQ0MzYxMDMsImp0aSI6ImJhMWIxMmViLWI2ZDUtNDYwZS1hZWUwLTM0NDU2NmU1ODU2OSJ9.KIQhZnaTM9HIRNWHmuHA0cfj1Di8qUfxWdoKMifSfFlGR8RFXn6BLEAnxGvWQd6gVM1mQUfL09zo2076B2ownIPQ_F9xMUBuOL9dnO0JqcNglnTlMhngpeCJ75E4WuToHSrPXiBPnFf4H1qMqgXarAoCGQ2CongiODCfLiAE2Zt8afYUDUsjPwGa5V5T4GF4lA1ORL75U4UBgw0k0uihFnpSTwkpt99fjRTnjLTEn2nQCCO37bxQlUk77A8BTM62SPCOwls1oTxQvhISjpynHRaVhXHC0sSH5dL-ytY_5OtQeexLNnTjc7wU44swaWIKyKdl7t5svflWPV_ZmCXq3g";

  @Test
  public void testGetAllUsers() {
    given()
        .header("Authorization", token)
        .when().get("api/v1/User")
        .then()
        .contentType(ContentType.JSON)
        .statusCode(200)
        .body("data.size()", not(0));
  }

  @Test
  public void testPostUser() {
    List<String> role = new ArrayList<>();
    role.add("SEKT");
    PostUserDto user = new PostUserDto("Peter", "123", role);

    given()
        .body(user)
        .header("Authorization", token)
        .contentType(ContentType.JSON)
        .when().post("api/v1/User")
        .then()
        .contentType(ContentType.JSON)
        .statusCode(200)
        .body("success", is(true));
  }
}
