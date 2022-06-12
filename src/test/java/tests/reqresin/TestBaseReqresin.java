package tests.reqresin;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;

public class TestBaseReqresin {

    @BeforeAll
    static void setUp() {
        RestAssured.baseURI = "https://reqres.in";
    }
}
