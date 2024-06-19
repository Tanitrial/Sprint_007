package step_2;

import io.qameta.allure.junit4.DisplayName;
import org.junit.Before;
import org.junit.Test;

import step.ListOrderStep;
import static org.hamcrest.Matchers.*;

public class ListOrderTest extends BaseTest {
    private ListOrderStep listOrderStep;

    @Before
    public void setUp() {
        listOrderStep = new ListOrderStep();
    }

    @Test
    @DisplayName("Get orders list")
    public void getOrdersListTest() {
        listOrderStep.getOrders()
                .statusCode(200)
                .body("orders", is(not(empty())));
    }
}