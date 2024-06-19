package step;

import com.google.gson.Gson;
import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.response.ValidatableResponse;
import base.Order;
public class CreatedOrderStep {
    private static final Gson gson = new Gson();

    @Step("Create order")
    public ValidatableResponse createOrder(Order order) {
        return RestAssured.given()
                .header("Content-type", "application/json")
                .body(gson.toJson(order))
                .when()
                .post("/api/v1/orders")
                .then();
    }

    @Step("Cancel order")
    public ValidatableResponse cancelOrder(Order order) {
        return RestAssured.given()
                .header("Content-type", "application/json")
                .body(gson.toJson(order))
                .when()
                .put("/api/v1/orders/cancel")
                .then();
    }
}