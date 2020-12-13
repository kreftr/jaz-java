package pl.edu.pjwstk.jaz;

import io.restassured.http.Header;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;
import pl.edu.pjwstk.jaz.requests.RegisterRequest;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@IntegrationTest
public class RegisterTest {

    @Test
    public void should_successfully_respond_to_registration(){

        var response = given()
                .when()
                .header(new Header("Content-Type", "application/json"))
                .body(new RegisterRequest("admin1","admin1"))
                .post("/api/register")
                .thenReturn();

        var statusCode = response.getStatusCode();

        assertThat(statusCode).isEqualTo(201);
    }


    @Test
    public void should_successfully_respond_to_registration_2(){

        var response = given()
                .when()
                .header(new Header("Content-Type", "application/json"))
                .body(new RegisterRequest("admin2","admin2"))
                .post("/api/register")
                .thenReturn();

        var statusCode = response.getStatusCode();

        assertThat(statusCode).isEqualTo(201);
    }


    @Test
    public void should_unsuccessfully_respond_to_registration_of_already_existing_user(){

        var response = given()
                .when()
                .header(new Header("Content-Type", "application/json"))
                .body(new RegisterRequest("admin","admin1"))
                .post("/api/register")
                .thenReturn();

        var statusCode = response.getStatusCode();

        assertThat(statusCode).isEqualTo(409);
    }


    @Test
    public void should_unsuccessfully_respond_to_registration_of_null_user(){

        var response = given()
                .when()
                .header(new Header("Content-Type", "application/json"))
                .body(new RegisterRequest(null,null))
                .post("/api/register")
                .thenReturn();

        var statusCode = response.getStatusCode();

        assertThat(statusCode).isEqualTo(400);
    }


    @Test
    public void should_unsuccessfully_respond_to_registration_of_blank_user(){

        var response = given()
                .when()
                .header(new Header("Content-Type", "application/json"))
                .body(new RegisterRequest("",""))
                .post("/api/register")
                .thenReturn();

        var statusCode = response.getStatusCode();

        assertThat(statusCode).isEqualTo(400);
    }

}
