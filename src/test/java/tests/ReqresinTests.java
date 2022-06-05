package tests;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import static io.restassured.RestAssured.*;
import static io.restassured.http.ContentType.*;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.is;

public class ReqresinTests {

    String baseUrl = "https://reqres.in";
    String loginUrl = "/api/login";
    String errorMessage = "Missing password";
    String bodyForUnsuccessfulLogin = "{\"email\": \"peter@klaven\"}";
    String userListUrl = "/api/users?page=2";
    String registerUrl = "/api/register";
    String bodyForRegistration = "{\"email\": \"eve.holt@reqres.in\",\"password\": \"pistol\"}";
    String deleteUrl = "/api/users/2";
    String bodyEmpty = "/api/users/23";

    @Test
    @DisplayName("Проверка авторизации без пароля")
    void loginUnsuccessfulTest() {
        given()
                .body(bodyForUnsuccessfulLogin)
                .contentType(JSON)
                .when()
                .post(baseUrl + loginUrl)
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
        get(baseUrl + userListUrl)
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
                .post(baseUrl + registerUrl)
                .then()
                .statusCode(200)
                .body("id", is(4))
                .body("token", is("QpwL5tke4Pnpja7X4"));
    }

    @Test
    @DisplayName("Проверка DELETE")
    void checkDeleteTest() {
        delete(baseUrl + deleteUrl)
                .then()
                .statusCode(204);
    }

    @Test
    @DisplayName("Поиск пользователя в пустом списке")
    void userNotFoundTest() {
        get(baseUrl + bodyEmpty)
                .then()
                .statusCode(404);
    }
}
