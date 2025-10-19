package test.java.com.example.tests;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import org.junit.jupiter.api.Test;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Condition.*;

public class SeleniumTest {
    @Test
    public void googleSearchTest() {
        Configuration.startMaximized = true;
        open("https://www.google.com");
        $("input[name='q']").setValue("Selenide Java").pressEnter();
        $("#search").shouldHave(text("Selenide"));
    }
}
