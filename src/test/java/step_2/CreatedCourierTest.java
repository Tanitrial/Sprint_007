package step_2;

import base.Courier;
import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import step.CourierStep;
import static org.hamcrest.CoreMatchers.*;

public class CreatedCourierTest extends BaseTest {
    private Courier courier;
    private CourierStep courierStep;
    private Courier courierWithoutPassword;
    private Courier courierWithoutLogin;
    private Courier courierExistingLogin;
    //  отслеживание создания курьера
    private boolean courierCreated;

    @Before
    public void setUp() {
        courierStep = new CourierStep();
        courierCreated = false;
        courier = new Courier("super", "1234", "Tsoy");
        courierWithoutPassword = new Courier("super", null, "Tsoy");
        courierWithoutLogin = new Courier(null, "1234", "Tsoy");
        courierExistingLogin = new Courier(courier.getLogin(), "1234", "Tsoy");
    }

    @After
    public void tearDown() {
        if (courierCreated) {
            String courierId = courierStep.loginCourier(courier.getLogin(), courier.getPassword());
            if (courierId != null) {
                courierStep.deleteCourier(courierId);
            }
        }
    }

    @Test
    @DisplayName("Create new Courier")
    public void createCourierTest() {
        courierStep.createCourier(courier)
                .statusCode(201)
                .body("ok", is(true));;
        courierCreated = true;
    }

    @Test
    @DisplayName("Create a duplicate Courier")
    public void createDuplicateCourierTest() {
        // Создаем курьера в первый раз
        courierStep.createCourier(courier);
        courierCreated = true;
        // Создаем курьера во второй раз с теми же данными
        courierStep.createCourier(courier)
                .statusCode(409)
                .body("message", is("Этот логин уже используется. Попробуйте другой."));
    }

    @Test
    @DisplayName("Creating a Courier without a required field login")
    public void createCourierMissingFieldLoginTest() {
        courierStep.createCourier(courierWithoutLogin)
                .statusCode(400)
                .body("message", is("Недостаточно данных для создания учетной записи"));
    }

    @Test
    @DisplayName("Creating a Courier without a required field password")
    public void createCourierMissingFieldPasswordTest() {
        courierStep.createCourier(courierWithoutPassword)
                .statusCode(400)
                .body("message", is("Недостаточно данных для создания учетной записи"));
    }

    @Test
    @DisplayName("Creating a Courier with an Existing Login")
    public void createCourierWithExistingLoginTest() {
        courierStep.createCourier(courier);
        courierCreated = true;
        // Создаем курьера с существующим логином
        courierStep.createCourier(courierExistingLogin)
                .statusCode(409)
                .body("message", is("Этот логин уже используется. Попробуйте другой."));
    }
}