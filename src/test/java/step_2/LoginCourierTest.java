package step_2;

import base.Courier;
import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import step.LoginCourierStep;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;

public class LoginCourierTest extends BaseTest {
    private Courier courier;
    private LoginCourierStep loginCourierStep;
    // Oтслеживание создания курьера
    private boolean courierCreated;

    @Before
    public void setUp() {
        loginCourierStep = new LoginCourierStep();
        courier = new Courier("Super", "1234", "Персей");
        loginCourierStep.createCourier(courier);
        courierCreated = true;
    }

    @After
    public void tearDown() {
        if (courierCreated) {
            String courierId = loginCourierStep.loginCourierAndGetId(courier.getLogin(), courier.getPassword());
            if (courierId != null) {
                loginCourierStep.deleteCourier(courierId);
            }
        }
    }

    @Test
    @DisplayName("Courier can log in")
    public void courierCanLoginTest() {
        loginCourierStep.loginCourier(courier.getLogin(), courier.getPassword())
                .statusCode(200)
                .body("id", is(notNullValue()));
    }

    @Test
    @DisplayName("Login fails without required fields login")
    public void loginFailsWithoutRequiredFieldsLoginTest() {
        // Проверка  логина
        loginCourierStep.loginCourier("", courier.getPassword())
                .statusCode(400)
                .body("message", is("Недостаточно данных для входа"));
    }

    @Test
    @DisplayName("Login fails without required fields password")
    public void loginFailsWithoutRequiredFieldsPasswordTest() {
        // Проверка  пароля
        loginCourierStep.loginCourier(courier.getLogin(), "")
                .statusCode(400)
                .body("message", is("Недостаточно данных для входа"));
    }

    @Test
    @DisplayName("Login fails with non-existent user")
    public void loginFailsWithNonExistentUserTest() {
        loginCourierStep.loginCourier("Puper", "4321")
                .statusCode(404)
                .body("message", is("Учетная запись не найдена"));
    }
}