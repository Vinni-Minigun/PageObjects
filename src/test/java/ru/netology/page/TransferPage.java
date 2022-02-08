package ru.netology.page;

import com.codeborne.selenide.SelenideElement;
import ru.netology.data.DataHelper;

import java.time.Duration;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.$;

public class TransferPage {
    private SelenideElement amountField = $("[data-test-id=amount] input");
    private SelenideElement fromField = $("[data-test-id=from] input");
    private SelenideElement transferField = $("[data-test-id=action-transfer]");
    private SelenideElement cancelField = $("[data-test-id=action-cancel]");
    private SelenideElement errorField = $("[data-test-id=error-notification]");
    private SelenideElement errorMessageMoreLimit = $(withText("Ошибка! Недостаточно средств на карте!"));


    public void getTransferMoney(int amount, DataHelper.CardInfo cardInfo) {
        amountField.setValue(String.valueOf(amount));
        fromField.setValue(cardInfo.getCardNumber());
        transferField.click();
    }

    public AccountPage validTransfer(int amount, DataHelper.CardInfo cardInfo) {
        getTransferMoney(amount, cardInfo);
        return new AccountPage();
    }

    public void errorTransfer(int amount, DataHelper.CardInfo cardInfo) {
        getTransferMoney(amount, cardInfo);
        errorField.shouldBe(visible).shouldHave(text("Ошибка! Произошла ошибка"), Duration.ofSeconds(15));
    }

    public void errorTransferMoreLimit(int amount, DataHelper.CardInfo cardInfo) {
        getTransferMoney(amount, cardInfo);
        errorMessageMoreLimit.shouldBe((visible), Duration.ofSeconds(15));
    }
}