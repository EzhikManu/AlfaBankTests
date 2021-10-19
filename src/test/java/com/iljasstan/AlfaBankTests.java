package com.iljasstan;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.logevents.SelenideLogger;
import com.iljasstan.enums.TariffConditions;
import config.CredentialsConfig;
import helpers.Attach;
import io.qameta.allure.selenide.AllureSelenide;
import org.aeonbits.owner.ConfigFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.openqa.selenium.Keys;
import org.openqa.selenium.remote.DesiredCapabilities;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.*;
import static com.codeborne.selenide.Selenide.*;
import static io.qameta.allure.Allure.step;

public class AlfaBankTests {
    CredentialsConfig credentieals = ConfigFactory.create(CredentialsConfig.class);
    String login = credentieals.login();
    String password = credentieals.password();
    @BeforeEach
    void beforeEach() {
        SelenideLogger.addListener("AllureSelenide", new AllureSelenide());
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("enableVNC", true);
        capabilities.setCapability("enableVideo", true);
        Configuration.browserCapabilities = capabilities;
        Configuration.startMaximized = true;
        Configuration.remote = String.format("https://%s:%s@selenoid.autotests.cloud/wd/hub", login, password);
    }

    @Test
    @DisplayName("Test credit card application form with wrong tel. number")
    void testCreditCardApplicationForm0() {
        step("open main page and click 'Интернет-банк'", () -> {
                    open("https://alfabank.ru");
                    $(".H1Cda9").click();
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
                    $(".a3qH-M").click();
                    $("[type=search]").setValue("кредит наличными").pressEnter();
                });
        step("check, that the result has value 'кредит наличными'", () -> {
            $("#ya-site-results").shouldHave(text("кредит наличными"));
        });
    }

    @ParameterizedTest
    @EnumSource(TariffConditions.class)
    @DisplayName("Test, that just 1% tariff condition {0} are on the page")
    void tariffConditionsTest(TariffConditions condition) {
        step("open main page and click to 'Малому бизнесу и ИП'", () -> {
            open("https://alfabank.ru");
            $(byText("Малому бизнесу и ИП")).click();
        });
        step("open just 1% tariff conditions", () -> {
            $$(byPartialLinkText("Подробнее")).first().click();
        });
        step("check, that all conditions are on the page", () -> {
            $(".aGTC57").shouldHave(text(condition.getDesc()));
        });
        }

        @Test
    void testCreditCalc() {
            step("open main page", () -> {
                open("https://alfabank.ru");
            });
            step("set values to credit calc", () -> {
                $("#creditAmount").doubleClick().sendKeys(Keys.BACK_SPACE);
                $("#creditAmount").doubleClick().sendKeys(Keys.BACK_SPACE);
                $("#creditAmount").doubleClick().sendKeys(Keys.BACK_SPACE);

                $("#creditAmount").setValue("2500000");
                $("[data-test-id='creditTerm-4']").click();
            });
            step("check the monthly payment", () -> {
                $$(".aJuYLG").first().shouldHave(text("63 900 ₽"));
            });
        }

        @Test
    void testATMMap() {
            step("open main page", () -> {
                open("https://alfabank.ru");
            });
            step("open page with ATM map", () -> {
                $(byPartialLinkText("Банкоматы")).click();
            });
            step("check, that ATM map is visible", () -> {
                $(".ymaps-2-1-79-map").shouldBe(visible);
            });
        }
    @AfterEach
    void tearDown() {
        Attach.screenshotAs("Last screenshot");
        Attach.browserConsoleLogs();
        Attach.addVideo();
    }
    }