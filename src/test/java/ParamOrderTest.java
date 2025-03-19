import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import praktikum.dto.Order;
import praktikum.api.OrderAPI;

import java.net.HttpURLConnection;
import java.util.List;

@RunWith(Parameterized.class)
public class ParamOrderTest {
    private final OrderAPI apiOrder = new OrderAPI();
    private int track = 0;

    private final List<String> color;

    public ParamOrderTest(List<String> color) {
        this.color = color;
    }

    @Parameterized.Parameters(name = "Тестовые данные: {0} {1}")
    public static Object[][] CreateOrderParams() {
        return new Object[][]{
                {List.of("BLACK")},
                {List.of("GREY")},
                {List.of("BLACK", "GREY")},
                {List.of()},
        };
    }

    @After
    public void clearTestData() {
        if (track != 0) {
            apiOrder.cancelOrder(track).statusCode(HttpURLConnection.HTTP_OK);
        }
    }

    @Test
    public void createOrder() {
        Order order = new Order("Ivan", "Ivanov", "Street",
                4, "+7 800 355 35 35", 5,
                "2020-06-06", "come back to Konoha", color);

        track = apiOrder.createOrder(order)
                .assertThat().statusCode(HttpURLConnection.HTTP_CREATED)
                .extract().path("track");

        Assert.assertNotEquals("Unexpected track in response", 0, track);
    }
}
