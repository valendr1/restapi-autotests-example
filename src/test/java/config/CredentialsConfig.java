package config;

import org.aeonbits.owner.Config;

@Config.Sources("classpath:tpl/config.properties")
public interface CredentialsConfig extends Config {
    String loginSelenoid();
    String passwordSelenoid();
    String loginDemoWebShop();
    String passwordDemoWebShop();
    String authDemoWebShopCookie();
    String addToCartDemoWebShopCookie();

}
