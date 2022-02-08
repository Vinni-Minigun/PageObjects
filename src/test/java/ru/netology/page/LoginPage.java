package ru.netology.page;

import com.codeborne.selenide.SelenideElement;
import ru.netology.data.DataHelper;

import java.time.Duration;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

public class LoginPage {
    private SelenideElement loginField = $("[data-test-id=login] input");
    private SelenideElement passwordField = $("[data-test-id=password] input");
    private SelenideElement loginButton = $("[data-test-id=action-login]");
    private SelenideElement errorMessage = $("[data-test-id=error-notification]");

    public void getAuthInfoLogin(DataHelper.AuthInfo authInfo) {
        loginField.setValue(authInfo.getLogin());
        passwordField.setValue(authInfo.getPassword());
        loginButton.click();
    }

    public VerificationPage validLogin(DataHelper.AuthInfo authInfo) {
        getAuthInfoLogin(authInfo);
        return new VerificationPage();
    }

    public void invalidLoginOrPassword(DataHelper.AuthInfo authInfo) {
        getAuthInfoLogin(authInfo);
        errorMessage.shouldBe(visible)
                .shouldHave(text("Ошибка! Неверно указан логин или пароль"), Duration.ofSeconds(15));
    }


}