import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import praktikum.dto.Order;
import praktikum.api.OrderAPI;

import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.List;

public class GetOrdersTest {

    private final OrderAPI apiOrder = new OrderAPI();
    private final List<Integer> orderIds = new ArrayList<>();

    @Before
    public void prepareTestData() {
        for (int i = 0; i <= 2; i++) {
            Order order = new Order("Ivanov", "Ivan", "Street",
                    4, "+7 800 355 35 35", 5,
                    "2020-06-06", "come back to Konoha", List.of("GREY"));

            int track = apiOrder.createOrder(order)
                    .assertThat().statusCode(HttpURLConnection.HTTP_CREATED)
                    .extract().path("track");

            orderIds.add(track);
        }
    }

    @After
    public void clearTestData() {
        for (int i = 0; i < orderIds.toArray().length; i++) {
            apiOrder.cancelOrder(orderIds.get(i));
        }
    }

    @Test
    public void createOrder() {
        List<String> response = apiOrder.getOrders().extract().path("orders");

        Assert.assertFalse("Unexpected track in response", response.isEmpty());
    }

}
