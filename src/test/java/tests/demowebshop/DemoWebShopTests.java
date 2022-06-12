package tests.demowebshop;


import config.CredentialsConfig;
import org.aeonbits.owner.ConfigFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Cookie;
import pages.DemoWebShopPages;

import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;
import static helpers.AllureRestAssuredFilter.withCustomTemplates;
import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static tests.demowebshop.DemoWebShopEndpoints.*;

public class DemoWebShopTests extends TestBaseDemoWebShop {

    DemoWebShopPages demoWebShopPages = new DemoWebShopPages();
    static CredentialsConfig config = ConfigFactory.create(CredentialsConfig.class);


    String email = config.loginDemoWebShop();
    String password = config.passwordDemoWebShop();
    String authCookieName = config.authDemoWebShopCookie();
    String addToCartCookieName = config.addToCartDemoWebShopCookie();
    String setContType = "application/x-www-form-urlencoded; charset=UTF-8";
    String formEmail = "Email";
    String formPassword = "Password";
    String checkCart = "Shopping cart\n" +
            "(1)";

    @Test
    @DisplayName("Login test(UI)")
    void loginTest() {
        step("Open login page", () ->
                open("/login"));
        step("Set email", () ->
                demoWebShopPages.setEmail(email));
        step("Set password", () ->
                demoWebShopPages.setPassword(password));
        step("Verify successful authorization", () ->
                demoWebShopPages.checkLogin(email));
    }

    @Test
    @DisplayName("Login test(API+UI)")
    void apiLoginTest() {

        step("Get cookie by api and set it to browser", () -> {
            String authCookie =
                    given()
                            .filter(withCustomTemplates())
                            .contentType(setContType)
                            .formParam(formEmail, email)
                            .formParam(formPassword, password)
                            .log().all()
                            .when()
                            .post(loginUrl)
                            .then()
                            .log().all()
                            .statusCode(302)
                            .extract()
                            .cookie(authCookieName);

            step("Open minimal content, because cookie can be set when site is opened", () ->
                    open(openPictureForAddCookie));

            step("Set cookie to to browser", () ->
                    getWebDriver().manage().addCookie(
                            new Cookie(authCookieName, authCookie)));

        });

        step("Open main page", () ->
                open(""));

        step("Verify successful authorization", () ->
                demoWebShopPages.checkLogin(email));
    }

    @Test
    @DisplayName("Add to cart(API + UI)")
    void addToCartWithoutLoginTest() {

        step("Get cookie by api and set it to browser", () -> {
            String addToCartCookie =
                    given()
                            .filter(withCustomTemplates())
                            .contentType(setContType)
                            .when()
                            .post(addToCartUrl)
                            .then()
                            .statusCode(200)
                            .extract()
                            .cookie(addToCartCookieName);

            step("Open minimal content, because cookie can be set when site is opened", () ->
                    open(openPictureForAddCookie));

            step("Set cookie to to browser", () ->
                    getWebDriver().manage().addCookie(
                            new Cookie(addToCartCookieName, addToCartCookie)));
        });
        step("Open main page", () ->
                open(""));

        step("Check added product", () ->
                demoWebShopPages.checkCartAtHeader(checkCart));

    }
}