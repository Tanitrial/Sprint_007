package step;

import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.response.ValidatableResponse;

public class ListOrderStep {
    @Step("Get orders list")
    public ValidatableResponse getOrders() {
        return RestAssured.given()
                .header("Content-type", "application/json")
                .when()
                .get("/api/v1/orders")
                .then();
    }
}