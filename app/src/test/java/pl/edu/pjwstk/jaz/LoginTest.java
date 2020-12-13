package pl.edu.pjwstk.jaz;

import io.restassured.http.Header;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;
import pl.edu.pjwstk.jaz.requests.LoginRequest;
import pl.edu.pjwstk.jaz.requests.RegisterRequest;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@IntegrationTest
public class LoginTest {

    @BeforeClass
    public static void create_test_users(){
        given().when()
                .header(new Header("Content-Type", "application/json"))
                .body(new RegisterRequest("test1", "test1"))
                .post("/api/register").then().statusCode(201);

        given().when()
                .header(new Header("Content-Type", "application/json"))
                .body(new RegisterRequest("test2", "test2"))
                .post("/api/register").then().statusCode(201);
    }

    @Test
    public void should_successfully_respond_to_login(){

        var response =
                given().when()
                .header(new Header("Content-Type", "application/json"))
                .body(new LoginRequest("test1", "test1"))
                .post("/api/login");

        var statusCode = response.getStatusCode();

        assertThat(statusCode).isEqualTo(200);
    }

    @Test
    public void should_unsuccessfully_respond_to_login(){

        var response =
                given().when()
                        .header(new Header("Content-Type", "application/json"))
                        .body(new LoginRequest("test1", "abcde"))
                        .post("/api/login");

        var statusCode = response.getStatusCode();

        assertThat(statusCode).isEqualTo(401);
    }

    @Test
    public void should_successfully_respond_to_login_2(){

        var response =
                given().when()
                        .header(new Header("Content-Type", "application/json"))
                        .body(new LoginRequest("test2", "test2"))
                        .post("/api/login");

        var statusCode = response.getStatusCode();

        assertThat(statusCode).isEqualTo(200);
    }

    @Test
    public void should_unsuccessfully_respond_to_login_2(){

        var response =
                given().when()
                        .header(new Header("Content-Type", "application/json"))
                        .body(new LoginRequest("abcde", "abcde"))
                        .post("/api/login");

        var statusCode = response.getStatusCode();

        assertThat(statusCode).isEqualTo(401);
    }

    @Test
    public void should_unsuccessfully_respond_to_login_when_user_is_already_logged() {

        var response =
                given().when()
                        .header(new Header("Content-Type", "application/json"))
                        .body(new LoginRequest("test1", "test1"))
                        .post("/api/login");

        var result =
                given().when().cookies(response.cookies()).
                        header(new Header("Content-Type", "application/json"))
                        .body(new LoginRequest("test1", "test1"))
                        .post("/api/login");

        var statusCode = result.getStatusCode();

        assertThat(statusCode).isEqualTo(409);
    }

}
