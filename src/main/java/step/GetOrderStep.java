package step;

import com.google.gson.Gson;
import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.response.ValidatableResponse;
import base.Order;

public class GetOrderStep {
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

    @Step("Delete order")
    public ValidatableResponse deleteOrder(String track) {
        return RestAssured.given()
                .queryParam("track", track)
                .when()
                .put("/api/v1/orders/cancel")
                .then();
    }

    @Step("Get order by number")
    public ValidatableResponse getOrder(String track) {
        return RestAssured.given()
                .queryParam("t", track)
                .when()
                .get("/api/v1/orders/track")
                .then();
    }
}
