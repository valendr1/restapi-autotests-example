package tests.demowebshop;


import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.logevents.SelenideLogger;
import config.CredentialsConfig;
import helpers.Attach;
import io.qameta.allure.junit5.AllureJunit5;
import io.qameta.allure.selenide.AllureSelenide;
import io.restassured.RestAssured;
import org.aeonbits.owner.ConfigFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.remote.DesiredCapabilities;
import pages.DemoWebShopPages;

import static com.codeborne.selenide.Selenide.closeWebDriver;

@ExtendWith({AllureJunit5.class})
public class TestBaseDemoWebShop {
    DemoWebShopPages demoWebShopPages = new DemoWebShopPages();

    static CredentialsConfig config = ConfigFactory.create(CredentialsConfig.class);
    static String login = config.loginSelenoid();
    static String password = config.passwordSelenoid();
    static String selenoidSign = "https://" + login + ":" + password + "@selenoid.autotests.cloud/wd/hub";

    @BeforeAll
    static void startup() {
        SelenideLogger.addListener("AllureSelenide", new AllureSelenide());

        Configuration.remote = selenoidSign;
        Configuration.baseUrl = "http://demowebshop.tricentis.com";
        RestAssured.baseURI = "http://demowebshop.tricentis.com";

        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("enableVNC", true);
        capabilities.setCapability("enableVideo", true);
        Configuration.browserCapabilities = capabilities;

    }

    @AfterEach
    public void addAttachments() {
        Attach.screenshotAs("Last screenshot");
        Attach.pageSource();
        Attach.browserConsoleLogs();
        Attach.addVideo();
        closeWebDriver();
    }
    }
