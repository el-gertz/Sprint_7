package praktikum.api;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import praktikum.dto.Order;

import java.util.Map;

public class OrderAPI extends BaseAPI {

    private static final String ORDERS = "orders";

    @Step("Создать заказ")
    public ValidatableResponse createOrder(Order order) {
        return spec()
                .body(order)
                .when()
                .post("/" + ORDERS)
                .then().log().all();
    }

    @Step("Получить список заказов")
    public ValidatableResponse getOrders() {
        return spec()
                .when()
                .get("/" + ORDERS)
                .then().log().all();
    }

    @Step("Отменить заказ")
    public ValidatableResponse cancelOrder(int track) {
        return spec()
                .param("track", track)
                .body(Map.of("track", track))
                .when()
                .put("/" + ORDERS + "/cancel")
                .then().log().all();
    }

}
