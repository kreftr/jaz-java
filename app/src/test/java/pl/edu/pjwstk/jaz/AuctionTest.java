package pl.edu.pjwstk.jaz;

import io.restassured.http.Header;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;
import pl.edu.pjwstk.jaz.requests.AuctionRequest;
import pl.edu.pjwstk.jaz.requests.LoginRequest;
import pl.edu.pjwstk.jaz.requests.RegisterRequest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@IntegrationTest
public class AuctionTest {

    @BeforeClass
    public static void create_test_users_section_category_parameter_and_auction(){
        given().when()
                .header(new Header("Content-Type", "application/json"))
                .body(new RegisterRequest("test1", "test1"))
                .post("/api/register").then().statusCode(201);

        given().when()
                .header(new Header("Content-Type", "application/json"))
                .body(new RegisterRequest("test2", "test2"))
                .post("/api/register").then().statusCode(201);

        var response =
                given().when()
                        .header(new Header("Content-Type", "application/json"))
                        .body(new LoginRequest("admin", "admin1"))
                        .post("/api/login").thenReturn();

        given().when().param("title", "Elektronika").
                cookies(response.cookies()).post("/api/section").then().statusCode(201);

        given().when().param("section_id", 4)
                .param("title", "Laptopy")
                .cookies(response.cookies()).post("/api/category").then().statusCode(201);

        given().when().param("key", "Marka")
                .cookies(response.cookies()).post("/api/parameter").then().statusCode(201);

        given().when().param("key", "Kolor")
                .cookies(response.cookies()).post("/api/parameter").then().statusCode(201);

        given().when().param("key", "Przekątna ekranu")
                .cookies(response.cookies()).post("/api/parameter").then().statusCode(201);


        List<String> images = new ArrayList<>();
        images.add("https://upload.wikimedia.org/wikipedia/commons/thumb/3/35/Tux.svg/1200px-Tux.svg.png");
        images.add("https://www.tabletowo.pl/wp-content/uploads/2018/08/Linux-Tux-1200x675.png");

        Map<String, String> parameters = new HashMap<>();
        parameters.put("6", "Dell");
        parameters.put("7", "Czerwony");

        given().when().cookies(response.cookies()).
                header(new Header("Content-Type", "application/json")).
                body(new AuctionRequest("Laptop Dell XYZ", "Test description...", 1700.21,
                        images, 5L, parameters)).post("/api/auction").then().statusCode(201);

    }


    @Test
    public void should_successfully_respond_auctions_creation(){
        var response =
                given().when()
                        .header(new Header("Content-Type", "application/json"))
                        .body(new LoginRequest("test2", "test2"))
                        .post("/api/login").thenReturn();

        List<String> images = new ArrayList<>();
        images.add("https://upload.wikimedia.org/wikipedia/commons/thumb/3/35/Tux.svg/1200px-Tux.svg.png");
        images.add("https://www.tabletowo.pl/wp-content/uploads/2018/08/Linux-Tux-1200x675.png");

        Map<String, String> parameters = new HashMap<>();
        parameters.put("6", "Lenovo");
        parameters.put("7", "Srebrny");
        parameters.put("8", "15.6");

        given().when().cookies(response.cookies())
                .header(new Header("Content-Type", "application/json"))
                .body(new AuctionRequest("Laptop Lenovo XYZ", "Laptop description...", 2100.21,
                        images, 5L, parameters)).post("/api/auction").then().statusCode(201);

        Map<String, String> parameters2 = new HashMap<>();
        parameters.put("6", "Samsung");
        parameters.put("7", "Czarny");
        parameters.put("8", "17.8");

        var result = given().when().cookies(response.cookies())
                .header(new Header("Content-Type", "application/json"))
                .body(new AuctionRequest("Laptop Samsung", "Laptop description2...", 2500.33,
                        images, 5L, parameters2)).post("/api/auction").thenReturn();


        var statusCode = result.getStatusCode();
        assertThat(statusCode).isEqualTo(201);
    }

    @Test
    public void should_unsuccessfully_respond_auction_creation_not_logged(){

        List<String> images = new ArrayList<>();
        images.add("https://upload.wikimedia.org/wikipedia/commons/thumb/3/35/Tux.svg/1200px-Tux.svg.png");
        images.add("https://www.tabletowo.pl/wp-content/uploads/2018/08/Linux-Tux-1200x675.png");

        Map<String, String> parameters = new HashMap<>();
        parameters.put("6", "Lenovo");
        parameters.put("7", "Srebrny");
        parameters.put("8", "15.6");

        var result = given().when().
                header(new Header("Content-Type", "application/json")).
                body(new AuctionRequest("Laptop Lenovo XYZ", "Laptop description...", 2100.21,
                        images, 5L, parameters)).post("/api/auction").thenReturn();

        var statusCode = result.getStatusCode();
        assertThat(statusCode).isEqualTo(401);
    }

    @Test
    public void should_unsuccessfully_respond_auction_removal_not_auction_owner(){

        var response =
                given().when()
                        .header(new Header("Content-Type", "application/json"))
                        .body(new LoginRequest("test1", "test1"))
                        .post("/api/login").thenReturn();

        var result = given().when().cookies(response.cookies())
                .delete("/api/auction/14").thenReturn();

        var statusCode = result.getStatusCode();
        assertThat(statusCode).isEqualTo(401);
    }

    @Test
    public void should_unsuccessfully_respond_auction_removal_not_found(){

        var response =
                given().when()
                        .header(new Header("Content-Type", "application/json"))
                        .body(new LoginRequest("test1", "test1"))
                        .post("/api/login").thenReturn();

        var result = given().when().cookies(response.cookies())
                .delete("/api/auction/123").thenReturn();

        var statusCode = result.getStatusCode();
        assertThat(statusCode).isEqualTo(404);
    }

    @Test
    public void should_unsuccessfully_respond_auction_edition_not_auction_owner(){

        var response =
                given().when()
                        .header(new Header("Content-Type", "application/json"))
                        .body(new LoginRequest("test1", "test1"))
                        .post("/api/login").thenReturn();

        List<String> images = new ArrayList<>();
        images.add("https://upload.wikimedia.org/wikipedia/commons/thumb/3/35/Tux.svg/1200px-Tux.svg.png");
        images.add("https://www.tabletowo.pl/wp-content/uploads/2018/08/Linux-Tux-1200x675.png");

        Map<String, String> parameters = new HashMap<>();
        parameters.put("6", "Lenovo");
        parameters.put("7", "Srebrny");
        parameters.put("8", "15.6");

        var result = given().when().cookies(response.cookies())
                .header(new Header("Content-Type", "application/json"))
                .body(new AuctionRequest("ABABABABABABA", "Laptop description...", 999.21,
                        images, 5L, parameters)).put("/api/auction/14").thenReturn();

        var statusCode = result.getStatusCode();
        assertThat(statusCode).isEqualTo(401);
    }

    @Test
    public void should_unsuccessfully_respond_auction_edition_not_found(){

        var response =
                given().when()
                        .header(new Header("Content-Type", "application/json"))
                        .body(new LoginRequest("test1", "test1"))
                        .post("/api/login").thenReturn();

        List<String> images = new ArrayList<>();
        images.add("https://upload.wikimedia.org/wikipedia/commons/thumb/3/35/Tux.svg/1200px-Tux.svg.png");
        images.add("https://www.tabletowo.pl/wp-content/uploads/2018/08/Linux-Tux-1200x675.png");

        Map<String, String> parameters = new HashMap<>();
        parameters.put("6", "Lenovo");
        parameters.put("7", "Srebrny");
        parameters.put("8", "15.6");

        var result = given().when().cookies(response.cookies())
                .header(new Header("Content-Type", "application/json"))
                .body(new AuctionRequest("ABABABABABABA", "Laptop description...", 999.21,
                        images, 5L, parameters)).put("/api/auction/123").thenReturn();

        var statusCode = result.getStatusCode();
        assertThat(statusCode).isEqualTo(404);
    }

    @Test
    public void should_successfully_respond_auction_edition(){

        var response =
                given().when()
                        .header(new Header("Content-Type", "application/json"))
                        .body(new LoginRequest("test2", "test2"))
                        .post("/api/login").thenReturn();

        List<String> images = new ArrayList<>();
        images.add("https://upload.wikimedia.org/wikipedia/commons/thumb/3/35/Tux.svg/1200px-Tux.svg.png");
        images.add("https://www.tabletowo.pl/wp-content/uploads/2018/08/Linux-Tux-1200x675.png");

        Map<String, String> parameters = new HashMap<>();
        parameters.put("6", "Lenovo");
        parameters.put("7", "Srebrny");
        parameters.put("8", "5.8");

        var result = given().when().cookies(response.cookies())
                .header(new Header("Content-Type", "application/json"))
                .body(new AuctionRequest("ABABABABABABA", "Laptop description...", 999.21,
                        images, 5L, parameters)).put("/api/auction/20").thenReturn();

        var statusCode = result.getStatusCode();
        assertThat(statusCode).isEqualTo(200);
    }

    @Test
    public void should_successfully_respond_auction_removal(){

        var response =
                given().when()
                        .header(new Header("Content-Type", "application/json"))
                        .body(new LoginRequest("admin", "admin1"))
                        .post("/api/login").thenReturn();


        var result = given().when().cookies(response.cookies())
                .delete("/api/auction/9").thenReturn();

        var statusCode = result.getStatusCode();
        assertThat(statusCode).isEqualTo(200);
    }
}
