package com.iljasstan;

import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selectors.*;
import static com.codeborne.selenide.Selenide.*;
import static io.qameta.allure.Allure.step;

public class AlfaBankTests {
    @BeforeEach
    void beforeEach() {
        Configuration.startMaximized = true;
    }

    @Test
    @DisplayName("Test credit card application form with wrong tel. number")
    void testCreditCardApplicationForm0() {
        step("open main page and click 'Интернет-банк'", () -> {
                    open("https://alfabank.ru");
                    $(".I1Cda9").click();
                });
        step("click 'стать клиентом'", () -> {
                    $(".browser-header__link").click();
                });
        step("choose credit card", () -> {
                    $$(byPartialLinkText("Выпустить карту")).first().click();
                });
        step("fill the application form", () -> {
                    $(byName("lastName")).click();  //это нужно для того,
                    $(byName("firstName")).click(); //чтобы появились радио-
                    $(byName("middleName")).click();//кнопки выбора пола
                    $(byName("lastName")).setValue("Иванов");
                    $(byName("firstName")).setValue("Петр");
                    $(byName("middleName")).setValue("Васильевич");
                    $(byName("phone")).click();
                    $(byName("phone")).setValue("1151001010");
                    $(byName("email")).setValue("tajixiy969@otozuz.com");
                    $(".checkbox__box").click();
                    $(byText("Мужской")).click();
                    $$(".navigate button").first().click();
                });
        step("check the message about invalid tel. number", () -> {
            $(".step1__client-contacts").$("[data-test-id=phone]").shouldHave(text("Телефон указан неверно. Некорректный код оператора."));
        });
    }

    @Test
    @DisplayName("Test search on the web-site")
    void localSearch() {
        step("open main page", () -> {
            open("https://alfabank.ru");
        });
        step("set to search field value 'кредит наличными'", () -> {
                    $("[type=search]").setValue("кредит наличными").pressEnter();
                });
        $("#ya-site-results").shouldHave(text("кредит наличными"));

    }
}
