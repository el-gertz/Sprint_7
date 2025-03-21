import io.qameta.allure.Description;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import praktikum.api.CourierAPI;
import praktikum.dto.Courier;
import praktikum.dto.Credentials;

import java.net.HttpURLConnection;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class CreateCourierTest {
    private int courierId;
    private final CourierAPI api = new CourierAPI();
    private int duplicatedCourierId;
    private final Courier duplicatedCourier = Courier.random();

    @Before
    public void prepareTestData() {
        api.create(duplicatedCourier).statusCode(HttpURLConnection.HTTP_CREATED);
        duplicatedCourierId = api.login(Credentials.fromCourier(duplicatedCourier))
                .statusCode(HttpURLConnection.HTTP_OK)
                .extract().path("id");
    }


    @After
    public void clearTestData() {
        if (courierId > 0) {
            api.delete(courierId).statusCode(HttpURLConnection.HTTP_OK);
        }
        if (duplicatedCourierId > 0) {
            api.delete(duplicatedCourierId).statusCode(HttpURLConnection.HTTP_OK);
        }
    }

    @Test
    @Description("Успешное создание курьера")
    public void createCourier() {
        Courier courier = Courier.random();

        boolean createResponse = api.create(courier)
                .assertThat().statusCode(HttpURLConnection.HTTP_CREATED)
                .extract()
                .path("ok");

        assertTrue(createResponse);

        courierId = api.login(Credentials.fromCourier(courier)).assertThat().statusCode(HttpURLConnection.HTTP_OK).extract().path("id");
    }

    @Test
    @Description("Создание курьера с существующим логином")
    public void duplicatedCourier() {
        String response = api.create(duplicatedCourier).assertThat().statusCode(HttpURLConnection.HTTP_CONFLICT).extract().path("message");

        assertEquals("Этот логин уже используется", response);
    }

    @Test
    @Description("Создание курьера без поля password")
    public void oneFieldIsNullCreation() {
        Courier courier = new Courier("ops", null, "john");
        String response = api.create(courier).assertThat().statusCode(HttpURLConnection.HTTP_BAD_REQUEST).extract().path("message");

        assertEquals("Недостаточно данных для создания учетной записи", response);
    }

}
