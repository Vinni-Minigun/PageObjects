package ru.netology.page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import lombok.val;

import java.util.List;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class AccountPage {
    private SelenideElement heading = $("[data-test-id=dashboard]");
    private ElementsCollection depositButton = $$("[data-test-id=action-deposit]");
    private SelenideElement reloadButton = $("[data-test-id=action-reload]");
    private ElementsCollection cards = $$(".list__item");
    private final String balanceStart = "баланс: ";
    private final String balanceFinish = " р.";


    public AccountPage() {
        heading.shouldBe(visible);
    }

    public int getFirstCardBalance() {
        val text = cards.first().text();
        return extractBalance(text);
    }

    public int getSecondCardBalance() {
        val text = cards.last().text();
        return extractBalance(text);
    }

    private int extractBalance(String text) {
        val value = text.substring(text.indexOf(balanceStart) + balanceStart.length(), text.indexOf(balanceFinish));
        return Integer.parseInt(value);
    }

    public TransferPage transferCard1() {
        depositButton.first().click();
        return new TransferPage();
    }

    public TransferPage transferCard2() {
        depositButton.last().click();
        return new TransferPage();
    }

    public AccountPage reloadCards() {
        reloadButton.click();
        return new AccountPage();
    }

}