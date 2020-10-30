package pl.edu.pjwstk.jaz.average;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;
import pl.edu.pjwstk.jaz.IntegrationTest;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;


@RunWith(SpringRunner.class)
@IntegrationTest
public class AverageControllerIT {
    @Test
    public void should_calculate_simple_average() {
        var response = given()
                .param("numbers", "1,2,3,4")
                .when()
                .get("/api/average")
                .then()
                .statusCode(200)
                .body(equalTo("Average equals: 2.5"));

    }
    @Test
    public void should_calculate_simple_average_2() {
        var response = given()
                .param("numbers", "4,3,1,7,5")
                .when()
                .get("/api/average")
                .then()
                .statusCode(200)
                .body(equalTo("Average equals: 4"));

    }
    @Test
    public void should_calculate_simple_average_3() {
        var response = given()
                .param("numbers", "2,1")
                .when()
                .get("/api/average")
                .then()
                .statusCode(200)
                .body(equalTo("Average equals: 1.5"));

    }
    @Test
    public void should_calculate_simple_average_4() {
        var response = given()
                .param("numbers", "2,1,1")
                .when()
                .get("/api/average")
                .then()
                .statusCode(200)
                .body(equalTo("Average equals: 1.33"));

    }
    @Test
    public void should_calculate_simple_average_5() {
                given()
                .when()
                .get("/api/average")
                .then()
                .statusCode(200)
                .body(equalTo("Please put parameters"));

    }
}