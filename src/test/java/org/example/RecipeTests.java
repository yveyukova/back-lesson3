package org.example;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeAll;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static io.restassured.RestAssured.filters;


public class RecipeTests
{
    @BeforeAll
static void beforeAll() {
//for logging request and responses in Allure reporting
RestAssured.filters(new AllureRestAssured());
}

    @Test
    void getRecipePositiveQueryTest() {
        given()
                .queryParam("apiKey", "02d67ad1edbf41eabdd912b8e712140c")
                .queryParam("query","chicken")
                .expect()
                .body("results[0].title",equalTo("Chicken 65"))
                .when()
                .get("https://api.spoonacular.com/recipes/complexSearch")
                .then()
                .statusCode(200);
    }
    @Test
    void getRecipeSortAscTest() {
        given()
                .queryParam("apiKey", "02d67ad1edbf41eabdd912b8e712140c")
                .queryParam("sort","calories")
                .queryParam("sortDirection","asc")
                .expect()
                .body("results[0].title",equalTo("Watermelon Popsicles with Mint, Basil & Lime"))
                .when()
                .get("https://api.spoonacular.com/recipes/complexSearch")
                .then()
                .statusCode(200);
    }
    @Test
    void getRecipeSortDescTest() {
        Response response =given()
                .queryParam("apiKey", "02d67ad1edbf41eabdd912b8e712140c")
                .queryParam("sort","calories")
                .queryParam("sortDirection","desc")
                .expect()
                .body("results[0].title",equalTo("Kim's Baked Macaroni & Cheese"))
                .when()
                .get("https://api.spoonacular.com/recipes/complexSearch");
        JsonPath json = response
                .body()
                .jsonPath();
        response
                .then()
                .statusCode(200);
        assertThat(json.getString("results[0].title"), equalTo("Kim's Baked Macaroni & Cheese"));
    }
    @Test
    void getRecipeSortNumberTest() {
        Response response =given()
                .queryParam("apiKey", "02d67ad1edbf41eabdd912b8e712140c")
                .queryParam("number","12")
                .when()
                .get("https://api.spoonacular.com/recipes/complexSearch");
        JsonPath json = response
                .body()
                .jsonPath();
        response
                .then()
                .statusCode(200);
        assertThat(json.getList("results").size(), equalTo(12));
    }
    @Test
    void getRecipeSortNumberWrongTest() {
        given()
                .queryParam("apiKey", "02d67ad1edbf41eabdd912b8e712140c")
                .queryParam("number","not number")
                .when()
                .get("https://api.spoonacular.com/recipes/complexSearch")
                .then()
                .statusCode(404);
    }
    @Test
    void postRecipeCuisinePizza() {
        given()
                .queryParam("apiKey", "02d67ad1edbf41eabdd912b8e712140c")
                .header("Content-Type", "application/x-www-form-urlencoded")
                .formParam("title","pizza")
                .expect()
                .body("confidence", equalTo(0.95F))
                .body("cuisine", equalTo("Italian"))
                .when()
                .post("https://api.spoonacular.com/recipes/cuisine")
                .then()
                .statusCode(200);
    }
    @Test
    void postRecipeCuisineHotdog() {
        given()
                .queryParam("apiKey", "02d67ad1edbf41eabdd912b8e712140c")
                .header("Content-Type", "application/x-www-form-urlencoded")
                .formParam("title","hotdog")
                .expect()
                .body("confidence", equalTo(0.85F))
                .body("cuisine", equalTo("American"))
                .when()
                .post("https://api.spoonacular.com/recipes/cuisine")
                .then()
                .statusCode(200);
    }
    @Test
    void postRecipeCuisineMoussaka() {
        given()
                .queryParam("apiKey", "02d67ad1edbf41eabdd912b8e712140c")
                .header("Content-Type", "application/x-www-form-urlencoded")
                .formParam("title","moussaka")
                .expect()
                .body("confidence", equalTo(0.85F))
                .body("cuisine", equalTo("Greek"))
                .when()
                .post("https://api.spoonacular.com/recipes/cuisine")
                .then()
                .statusCode(200);
    }
    @Test
    void postRecipeCuisineSushi() {
        given()
                .queryParam("apiKey", "02d67ad1edbf41eabdd912b8e712140c")
                .header("Content-Type", "application/x-www-form-urlencoded")
                .formParam("title","sushi")
                .expect()
                .body("confidence", equalTo(0.85F))
                .body("cuisine", equalTo("Japanese"))
                .when()
                .post("https://api.spoonacular.com/recipes/cuisine")
                .then()
                .statusCode(200);
    }
    @Test
    void postRecipeCuisineCurry() {
        given()
                .queryParam("apiKey", "02d67ad1edbf41eabdd912b8e712140c")
                .header("Content-Type", "application/x-www-form-urlencoded")
                .formParam("title","curry")
                .expect()
                .body("confidence", equalTo(0.95F))
                .body("cuisine", equalTo("Indian"))
                .when()
                .post("https://api.spoonacular.com/recipes/cuisine")
                .then()
                .statusCode(200);
    }

}
