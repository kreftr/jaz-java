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
public class AuthorizationFilterTest {

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
    public void should_unsuccessfully_get_to_admin_content_when_logged_as_normal_user(){
        var response =
                given().when().header(new Header("Content-Type", "application/json"))
                        .body(new LoginRequest("test1","test1")).post("/api/login")
                        .thenReturn();

        var result =
            given().when().
                cookies(response.cookies()).get("/api/register/admin/users").thenReturn();

       var statusCode = result.getStatusCode();

       assertThat(statusCode).isEqualTo(403);
    }

    @Test
    public void should_unsuccessfully_get_to_admin_content_when_logged_as_normal_user_2(){
        var response =
                given().when().header(new Header("Content-Type", "application/json"))
                        .body(new LoginRequest("test1","test1")).post("/api/login")
                        .thenReturn();

        var result =
                given().when().param("user","admin").
                        cookies(response.cookies()).get("/api/register/admin/remove").thenReturn();

        var statusCode = result.getStatusCode();

        assertThat(statusCode).isEqualTo(403);
    }

    @Test
    public void should_successfully_get_to_admin_content_when_logged_as_admin(){
        var response =
                given().when().header(new Header("Content-Type", "application/json"))
                        .body(new LoginRequest("admin","admin1")).post("/api/login")
                        .thenReturn();

        var result =
                given().when().
                        cookies(response.cookies()).get("/api/register/admin/users").thenReturn();

        var statusCode = result.getStatusCode();

        assertThat(statusCode).isEqualTo(200);
    }

    @Test
    public void should_successfully_get_to_admin_content_when_logged_as_admin_2(){
        var response =
                given().when().header(new Header("Content-Type", "application/json"))
                        .body(new LoginRequest("admin","admin1")).post("/api/login")
                        .thenReturn();

        var result =
                given().when().
                        cookies(response.cookies()).
                        get("/api/register/admin/remove?user=test2").thenReturn();

        var statusCode = result.getStatusCode();

        assertThat(statusCode).isEqualTo(200);

        given().when().
                cookies(response.cookies()).get("/api/register/admin/remove?user=test2")
                .then().statusCode(404);
    }

    @Test
    public void should_successfully_get_to_normal_user_content_when_logged_as_admin() {
        var response =
                given().when().header(new Header("Content-Type", "application/json"))
                        .body(new LoginRequest("admin", "admin1")).post("/api/login")
                        .thenReturn();

        given().when().cookies(response.cookies()).get("/api/login/user/logged").then()
                .body(equalTo("Logged in: true"));
    }

}
