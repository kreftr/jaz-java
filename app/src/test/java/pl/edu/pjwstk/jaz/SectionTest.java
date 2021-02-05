package pl.edu.pjwstk.jaz;

import io.restassured.http.Header;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import pl.edu.pjwstk.jaz.requests.LoginRequest;
import pl.edu.pjwstk.jaz.requests.RegisterRequest;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@IntegrationTest
public class SectionTest {

    @BeforeClass
    public static void create_test_section_and_user(){
        given().when()
                .header(new Header("Content-Type", "application/json"))
                .body(new RegisterRequest("test1", "test1"))
                .post("/api/register").then().statusCode(201);

        var response =
                given().when()
                        .header(new Header("Content-Type", "application/json"))
                        .body(new LoginRequest("admin", "admin1"))
                        .post("/api/login").thenReturn();

        given().when().param("title", "ABC")
                .cookies(response.cookies()).post("/api/section").then().statusCode(201);

    }

    @Test
    public void should_successfully_respond_section_creation(){

        var response =
                given().when()
                        .header(new Header("Content-Type", "application/json"))
                        .body(new LoginRequest("admin", "admin1"))
                        .post("/api/login").thenReturn();

        given().when().param("title", "Nieruchomosci")
        .cookies(response.cookies()).post("/api/section").then().statusCode(201);

        var result = given().when().param("title","Samochody")
                            .cookies(response.cookies()).post("/api/section").thenReturn();

        var statusCode = result.getStatusCode();

        assertThat(statusCode).isEqualTo(201);
    }

    @Test
    public void should_unsuccessfully_respond_section_creation_section_already_exists(){

        var response =
                given().when()
                        .header(new Header("Content-Type", "application/json"))
                        .body(new LoginRequest("admin", "admin1"))
                        .post("/api/login").thenReturn();

        var result = given().when().param("title","ABC")
                .cookies(response.cookies()).post("/api/section").thenReturn();

        var statusCode = result.getStatusCode();

        assertThat(statusCode).isEqualTo(409);
    }

    @Test
    public void should_unsuccessfully_respond_section_creation_no_permission(){

        var response =
                given().when()
                        .header(new Header("Content-Type", "application/json"))
                        .body(new LoginRequest("test1", "test1"))
                        .post("/api/login").thenReturn();

        var result = given().when().param("title","Elektronika")
                            .cookies(response.cookies()).post("/api/section").thenReturn();

        var statusCode = result.getStatusCode();

        assertThat(statusCode).isEqualTo(403);
    }

    @Test
    public void should_successfully_respond_section_removal(){

        var response =
                given().when()
                        .header(new Header("Content-Type", "application/json"))
                        .body(new LoginRequest("admin", "admin1"))
                        .post("/api/login").thenReturn();

        given().when().param("title","X")
                .cookies(response.cookies()).post("/api/section").thenReturn();

        var result = given().when()
                            .cookies(response.cookies()).delete("/api/section/3").thenReturn();

        var statusCode = result.getStatusCode();

        assertThat(statusCode).isEqualTo(200);
    }

    @Test
    public void should_unsuccessfully_respond_section_removal(){

        var response =
                given().when()
                        .header(new Header("Content-Type", "application/json"))
                        .body(new LoginRequest("admin", "admin1"))
                        .post("/api/login").thenReturn();


        var result = given().when()
                            .cookies(response.cookies()).delete("/api/section/123").thenReturn();

        var statusCode = result.getStatusCode();

        assertThat(statusCode).isEqualTo(404);
    }

    @Test
    public void should_unsuccessfully_respond_section_removal_no_permission(){

        var response =
                given().when()
                        .header(new Header("Content-Type", "application/json"))
                        .body(new LoginRequest("test1", "test1"))
                        .post("/api/login").thenReturn();

        var result = given().when()
                .cookies(response.cookies()).delete("/api/section/3").thenReturn();

        var statusCode = result.getStatusCode();

        assertThat(statusCode).isEqualTo(403);
    }

    @Test
    public void should_successfully_respond_section_edit(){

        var response =
                given().when()
                        .header(new Header("Content-Type", "application/json"))
                        .body(new LoginRequest("admin", "admin1"))
                        .post("/api/login").thenReturn();


        var result = given().when()
                .param("newTitle", "Test")
                .cookies(response.cookies()).put("/api/section/4").thenReturn();

        var statusCode = result.getStatusCode();

        assertThat(statusCode).isEqualTo(200);
    }

    @Test
    public void should_unsuccessfully_respond_section_edit(){

        var response =
                given().when()
                        .header(new Header("Content-Type", "application/json"))
                        .body(new LoginRequest("admin", "admin1"))
                        .post("/api/login").thenReturn();


        var result = given().when()
                .param("newTitle", "ABCD").
                        cookies(response.cookies()).put("/api/section/321").thenReturn();

        var statusCode = result.getStatusCode();

        assertThat(statusCode).isEqualTo(404);
    }

    @Test
    public void should_unsuccessfully_respond_section_edit_no_permission(){

        var response =
                given().when()
                        .header(new Header("Content-Type", "application/json"))
                        .body(new LoginRequest("test1", "test1"))
                        .post("/api/login").thenReturn();


        var result = given().when()
                .param("newTitle", "AAAA")
                .cookies(response.cookies()).put("/api/section/3").thenReturn();

        var statusCode = result.getStatusCode();

        assertThat(statusCode).isEqualTo(403);
    }

}
