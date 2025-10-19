package test.java.com.example.tests;

import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Condition.*;

public class GoogleHomePage {
    public void search(String query) {
        $("input[name='q']").setValue(query).pressEnter();
    }
}
