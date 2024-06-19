package step;

import base.Courier;
import com.google.gson.Gson;
import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.response.ValidatableResponse;

public class CourierStep {
    private static final Gson gson = new Gson();

    @Step("Create courier")
    public ValidatableResponse createCourier(Courier courier) {
        return RestAssured.given()
                .header("Content-type", "application/json")
                .body(gson.toJson(courier))
                .when()
                .post("/api/v1/courier")
                .then();
    }

    @Step("Login courier")
    public String loginCourier(String login, String password) {
        return RestAssured.given()
                .header("Content-type", "application/json")
                .body(gson.toJson(new Courier(login, password)))
                .when()
                .post("/api/v1/courier/login")
                .then()
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
                .then();
    }
}
