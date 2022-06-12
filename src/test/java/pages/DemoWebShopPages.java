package pages;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class DemoWebShopPages {
    String baseUrl = "http://demowebshop.tricentis.com/";

    SelenideElement email = $("#Email");
    SelenideElement password = $("#Password");
    SelenideElement accountAtHeader = $(".account");
    SelenideElement cartAtHeader = $("#topcartlink");

    public void openPage() {
        open(baseUrl);
    }

    public DemoWebShopPages setEmail(String setEmail) {
        email.setValue(setEmail);
        return this;
    }

    public DemoWebShopPages setPassword(String setPassword) {
        password.setValue(setPassword).submit();
        return this;
    }

    public DemoWebShopPages checkLogin(String accountName) {
        accountAtHeader.shouldHave(text(accountName));
        return this;
    }

    public DemoWebShopPages checkCartAtHeader(String cart){
        cartAtHeader.shouldHave(exactText("Shopping cart\n" +
                "(1)"));
        return this;
    }

}
