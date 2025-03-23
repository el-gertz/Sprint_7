import io.qameta.allure.Description;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import praktikum.api.CourierAPI;
import praktikum.dto.Courier;
import praktikum.dto.Credentials;

import java.net.HttpURLConnection;

public class LoginTest {
    private final CourierAPI api = new CourierAPI();
    private int courierId;
    private final Courier courier = Courier.random();

    @Before
    public void prepareTestData() {
        api.create(courier);
    }

    @After
    public void clearTestData() {
        courierId = api.login(Credentials.fromCourier(courier)).extract().path("id");
        if (courierId > 0) {
            api.delete(courierId).assertThat().statusCode(HttpURLConnection.HTTP_OK);
        }
    }

    @Test
    @Description("Успешный логин")
    public void login() {
        courierId = api.login(Credentials.fromCourier(courier)).assertThat().statusCode(HttpURLConnection.HTTP_OK).extract().path("id");
        Assert.assertNotEquals(0, courierId);
    }

    @Test
    @Description("Логин с неправильным паролем")
    public void invalidLogin() {
        Credentials badCreds = new Credentials(courier.getLogin(), "ooo");
        String message = api.login(badCreds).assertThat().statusCode(HttpURLConnection.HTTP_NOT_FOUND).extract().path("message");
        Assert.assertEquals("Учетная запись не найдена", message);
    }

    @Test
    @Description("Логин с пустым полем login")
    public void emptyLogin() {
        Credentials badCreds = new Credentials(null, "ooo");
        String message = api.login(badCreds).assertThat().statusCode(HttpURLConnection.HTTP_BAD_REQUEST).extract().path("message");
        Assert.assertEquals("Недостаточно данных для входа", message);
    }

    @Test
    @Description("Логин с неизвестным юзером")
    public void notFoundUser() {
        Credentials badCreds = new Credentials("notExist", courier.getPassword());
        String message = api.login(badCreds).assertThat().statusCode(HttpURLConnection.HTTP_NOT_FOUND).extract().path("message");
        Assert.assertEquals("Учетная запись не найдена", message);
    }
}
