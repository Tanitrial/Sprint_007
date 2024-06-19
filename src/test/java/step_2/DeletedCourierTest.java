package step_2;

import base.Courier;
import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import step.DeletedCourierStep;
import static org.hamcrest.CoreMatchers.is;

public class DeletedCourierTest extends BaseTest {
    private DeletedCourierStep deleteCourierStep;
    private String courierId;

    @Before
    public void setUp() {
        Courier courier = new Courier("Super", "4234", "Тесей");
        deleteCourierStep = new DeletedCourierStep();
        deleteCourierStep.createdCourier(courier);
        courierId = deleteCourierStep.loginCourierAndGetId(courier.getLogin(), courier.getPassword());
    }

    @After
    public void tearDown() {
        if (courierId != null) {
            deleteCourierStep.deleteCourier(courierId);
        }
    }


    @Test
    @DisplayName("Successful deletion of a courier")
    public void deletedCourierSuccessTest() {
        deleteCourierStep.deleteCourier(courierId)
                .statusCode(200)
                .body("ok", is(true));
        courierId = null;
    }

    //Ой-щй! Баг, в документации один результат,  в postman и в idea код: 404, тело: Not found.
    @Test
    @DisplayName("Delete courier without ID")
    public void deletedCourierWithoutIdTest() {
        deleteCourierStep.deleteCourier(null)
                .statusCode(400)
                .body("message", is("Недостаточно данных для удаления курьера"));
    }

    @Test
    @DisplayName("Delete courier with non-existing ID")
    public void deletedCourierNonExistingIdTest() {
        deleteCourierStep.deleteCourier("11111")
                .statusCode(404)
                .body("message", is("Курьера с таким id нет."));
    }
}