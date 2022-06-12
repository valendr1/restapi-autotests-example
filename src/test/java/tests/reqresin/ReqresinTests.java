package tests.reqresin;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static io.restassured.RestAssured.*;
import static io.restassured.http.ContentType.*;
import static org.hamcrest.Matchers.*;
import static tests.reqresin.ReqresEndpoints.*;

public class ReqresinTests extends TestBaseReqresin {

    String errorMessage = "Missing password";
    String bodyForUnsuccessfulLogin = "{\"email\": \"peter@klaven\"}";
    String bodyForRegistration = "{\"email\": \"eve.holt@reqres.in\",\"password\": \"pistol\"}";

    @Test
    @DisplayName("Проверка авторизации без пароля")
    void loginUnsuccessfulTest() {
        given()
                .body(bodyForUnsuccessfulLogin)
                .contentType(JSON)
                .when()
                .post(loginUrl)
                .then()
                .statusCode(400)
                .body("error", is(errorMessage));
    }

    @ParameterizedTest(name = "Поиск {0} {1} в списке")
    @CsvSource(value = {
            "Michael, Lawson",
            "Byron, Fields",
    })
    void checkUserListTest(String first_name, String last_name) {
        get(userListUrl)
                .then()
                .statusCode(200)
                .body("data.first_name", hasItem(first_name))
                .body("data.last_name", hasItem(last_name));
    }

    @Test
    @DisplayName("Проверка регистрации")
    void registerSuccessfulTest() {
        given()
                .body(bodyForRegistration)
                .contentType(JSON)
                .when()
                .post(registerUrl)
                .then()
                .statusCode(200)
                .body("id", is(4))
                .body("token", notNullValue());
    }

    @Test
    @DisplayName("Проверка DELETE")
    void checkDeleteTest() {
        delete(deleteUrl)
                .then()
                .statusCode(204);
    }

    @Test
    @DisplayName("Поиск пользователя в пустом списке")
    void userNotFoundTest() {
        get(bodyEmpty)
                .then()
                .statusCode(404);
    }
}
