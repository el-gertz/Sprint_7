package praktikum.api;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import praktikum.dto.Courier;
import praktikum.dto.Credentials;

import java.util.Map;

public class CourierAPI extends BaseAPI {

    private static final String COURIER = "courier";

    @Step("Создать курьера")
    public ValidatableResponse create(Courier courier) {
        return spec()
                .body(courier)
                .when()
                .post(COURIER)
                .then()
                .log().all();
    }

    @Step("Логин курьера")
    public ValidatableResponse login(Credentials creds) {
        return spec()
                .body(creds)
                .when()
                .post(COURIER + "/login")
                .then().log().all();
    }

    @Step("Удалить курьера")
    public ValidatableResponse delete(int id) {
        return spec()
                .body(Map.of("id", id))
                .when()
                .delete(COURIER + "/" + id)
                .then().log().all();
    }

}
