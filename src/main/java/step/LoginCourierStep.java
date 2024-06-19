package step;

import com.google.gson.Gson;
import base.Courier;
import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.response.ValidatableResponse;

public class LoginCourierStep {
    private static final Gson gson = new Gson();

    @Step("Create courier and set flag")
    public void createCourier(Courier courier) {
        RestAssured.given()
                .header("Content-type", "application/json")
                .body(gson.toJson(courier))
                .when()
                .post("/api/v1/courier")
                .then();
    }

    @Step("Login courier")
    public ValidatableResponse loginCourier(String login, String password) {
        return RestAssured.given()
                .header("Content-type", "application/json")
                .body(gson.toJson(new Courier(login, password)))
                .when()
                .post("/api/v1/courier/login")
                .then();
    }

    @Step("Login courier and get id")
    public String loginCourierAndGetId(String login, String password) {
        return loginCourier(login, password)
                .extract()
                .path("id")
                .toString();
    }

    @Step("Delete courier")
    public void deleteCourier(String id) {
        RestAssured.given()
                .header("Content-type", "application/json")
                .pathParam("id", id)
                .when()
                .delete("/api/v1/courier/{id}")
                .then()
                .statusCode(200);
    }
}