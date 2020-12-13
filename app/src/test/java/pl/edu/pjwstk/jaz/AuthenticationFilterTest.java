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
import static org.hamcrest.Matchers.equalTo;


@RunWith(SpringRunner.class)
@IntegrationTest
public class AuthenticationFilterTest {

    @BeforeClass
    public static void create_test_user(){
        given().when()
                .header(new Header("Content-Type", "application/json"))
                .body(new RegisterRequest("test1", "test1"))
                .post("/api/register").then().statusCode(201);
    }

    @Test
    public void should_successfully_get_to_content_when_logged(){
        var response =
                given().when().header(new Header("Content-Type", "application/json"))
                .body(new LoginRequest("test1","test1")).post("/api/login")
                .thenReturn();

        given().when().cookies(response.cookies()).get("/api/login/user/logged").then()
                .body(equalTo("Logged in: true"));

    }

    @Test
    public void should_unsuccessfully_get_to_content_when_not_logged(){
        var response =
                given().when()
                        .get("/api/login/user/logged").thenReturn();

        var statusCode = response.getStatusCode();

        assertThat(statusCode).isEqualTo(401);
    }

    @Test
    public void should_successfully_logout_when_logged(){
        var response =
                given().when().header(new Header("Content-Type", "application/json"))
                        .body(new LoginRequest("test1","test1")).post("/api/login")
                        .thenReturn();

        var result =
                given().when()
                .cookies(response.cookies()).get("/api/login/user/logout").thenReturn();

        var statusCode = result.getStatusCode();

        assertThat(statusCode).isEqualTo(200);
    }

    @Test
    public void should_unsuccessfully_logout_when_not_logged(){

        var response =
                given().when().get("/api/login/user/logout").thenReturn();

        var statusCode = response.getStatusCode();

        assertThat(statusCode).isEqualTo(401);
    }

    @Test
    public void should_successfully_get_to_content_when_logged_2(){
        var response =
                given().when().header(new Header("Content-Type", "application/json"))
                        .body(new LoginRequest("admin","admin1")).post("/api/login")
                        .thenReturn();

        given().when().cookies(response.cookies()).get("/api/register/admin/users").then()
                .statusCode(200);

    }

    @Test
    public void should_unsuccessfully_get_to_content_when_logged_2(){

        var response =
        given().when().get("/api/register/admin/users").thenReturn();

        var statusCode = response.getStatusCode();

        assertThat(statusCode).isEqualTo(401);
    }

}
