package test.java.com.example;

import com.github.tomakehurst.wiremock.WireMockServer;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class WireMockTest {
    static WireMockServer wireMockServer;

    @BeforeAll
    static void setup() {
        wireMockServer = new WireMockServer(8089);
        wireMockServer.start();
        configureFor("localhost", 8089);
        stubFor(get(urlEqualTo("/mocked/user/1"))
            .willReturn(aResponse()
                .withStatus(200)
                .withHeader("Content-Type", "application/json")
                .withBody("{\"id\":1,\"name\":\"Mocked Alice\"}")));
    }

    @Test
    public void testMockedUser() {
        get("http://localhost:8089/mocked/user/1")
            .then()
            .statusCode(200)
            .body("id", equalTo(1))
            .body("name", equalTo("Mocked Alice"));
    }

    @AfterAll
    static void teardown() {
        wireMockServer.stop();
    }
}
