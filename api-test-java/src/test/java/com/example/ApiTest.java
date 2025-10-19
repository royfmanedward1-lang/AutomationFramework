package com.example;

import io.restassured.RestAssured;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class ApiTest {
    @Test
    public void testGetUser() {
        RestAssured.get("https://jsonplaceholder.typicode.com/users/1")
            .then()
            .statusCode(200)
            .body("id", equalTo(1))
            .body("name", notNullValue());
    }

    @Test
    public void testCreatePost() {
        given()
            .contentType("application/json")
            .body("{\"title\":\"foo\",\"body\":\"bar\",\"userId\":1}")
        .when()
            .post("https://jsonplaceholder.typicode.com/posts")
        .then()
            .statusCode(201)
            .body("title", equalTo("foo"))
            .body("body", equalTo("bar"));
    }
}
