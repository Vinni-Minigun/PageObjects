package ru.netology.test;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.data.DataHelper;
import ru.netology.page.AccountPage;
import ru.netology.page.LoginPage;
import ru.netology.page.VerificationPage;

import static org.junit.jupiter.api.Assertions.assertEquals;

import static com.codeborne.selenide.Selenide.open;

public class MoneyTransferTest {

    @BeforeEach
    public void setUp() {
        Configuration.headless = true;
        open("http://localhost:9999");
        Selenide.clearBrowserCookies();
        Selenide.clearBrowserLocalStorage();
    }


    @Test
    void shouldValidTransferMoneyFromSecondCardToFirstCard() {
        var loginPage = new LoginPage();
        var authInfo = DataHelper.getAuthInfo();
        var verificationPage = loginPage.validLogin(authInfo);
        var verifyInfo = DataHelper.getVerificationCodeFor(authInfo);
        var dashboardPage = verificationPage.validVerify(verifyInfo);
        var balanceFirstCard = dashboardPage.getFirstCardBalance();
        var balanceSecondCard = dashboardPage.getSecondCardBalance();
        var transferPage = dashboardPage.transferCard1();
        var amount = DataHelper.getAmount(balanceSecondCard);
        var dashboardPage2 = transferPage.validTransfer(amount, DataHelper.getSecondCardInfo());
        var actualBalanceFirstCard = dashboardPage2.getFirstCardBalance();
        var actualBalanceSecondCard = dashboardPage2.getSecondCardBalance();
        var expectedBalanceFirstCard = balanceFirstCard + amount;
        var expectedBalanceSecondCard = balanceSecondCard - amount;
        assertEquals(expectedBalanceFirstCard, actualBalanceFirstCard);
        assertEquals(expectedBalanceSecondCard, actualBalanceSecondCard);
    }

    @Test
    void shouldValidTransferMoneyFromFirstCardsTwoSecondCard() {
        var loginPage = new LoginPage();
        var authInfo = DataHelper.getAuthInfo();
        var verificationPage = loginPage.validLogin(authInfo);
        var verifyInfo = DataHelper.getVerificationCodeFor(authInfo);
        var dashboardPage = verificationPage.validVerify(verifyInfo);
        var balanceFirstCard = dashboardPage.getFirstCardBalance();
        var balanceSecondCard = dashboardPage.getSecondCardBalance();
        var transferPage = dashboardPage.transferCard2();
        var amount = DataHelper.getAmount(balanceFirstCard);
        var dashboardPage2 = transferPage.validTransfer(amount, DataHelper.getFirstCardInfo());
        var actualBalanceFirstCard = dashboardPage2.getFirstCardBalance();
        var actualBalanceSecondCard = dashboardPage2.getSecondCardBalance();
        var expectedBalanceFirstCard = balanceFirstCard - amount;
        var expectedBalanceSecondCard = balanceSecondCard + amount;
        assertEquals(expectedBalanceFirstCard, actualBalanceFirstCard);
        assertEquals(expectedBalanceSecondCard, actualBalanceSecondCard);
    }

    @Test
    void shouldTransferMoneyFromFirstCardsTwoSecondCardMoreLimit() {
        var loginPage = new LoginPage();
        var authInfo = DataHelper.getAuthInfo();
        var verificationPage = loginPage.validLogin(authInfo);
        var verifyInfo = DataHelper.getVerificationCodeFor(authInfo);
        var dashboardPage = verificationPage.validVerify(verifyInfo);
        var balanceFirstCard = dashboardPage.getFirstCardBalance();
        var transferPage = dashboardPage.transferCard2();
        var amount = DataHelper.getAmount(balanceFirstCard) + 50_000;
        transferPage.errorTransferMoreLimit(amount, DataHelper.getFirstCardInfo());

    }

    @Test
    void shouldTransferMoneyInvalidPassword() {
        var loginPage = new LoginPage();
        var authInfo = DataHelper.getInvalidPassword();
        loginPage.invalidLoginOrPassword(authInfo);
    }

    @Test
    void shouldTransferMoneyInvalidLogin() {
        var loginPage = new LoginPage();
        var authInfo = DataHelper.getInvalidLogin();
        loginPage.invalidLoginOrPassword(authInfo);
    }

    @Test
    void shouldTransferMoneyInvalidVerificationCode() {
        var loginPage = new LoginPage();
        var authInfo = DataHelper.getAuthInfo();
        var verificationPage = loginPage.validLogin(authInfo);
        var verifyInfo = DataHelper.getInvalidVerificationCodeFor(authInfo);
        verificationPage.invalidVerify(verifyInfo);
    }

    @Test
    void shouldTransferMoneyFromInvalidNumberCards() {
        var loginPage = new LoginPage();
        var authInfo = DataHelper.getAuthInfo();
        var verificationPage = loginPage.validLogin(authInfo);
        var verifyInfo = DataHelper.getVerificationCodeFor(authInfo);
        var dashboardPage = verificationPage.validVerify(verifyInfo);
        var balanceFirstCard = dashboardPage.getFirstCardBalance();
        var transferPage = dashboardPage.transferCard2();
        var amount = DataHelper.getAmount(balanceFirstCard);
        transferPage.errorTransfer(amount, DataHelper.getInvalidFirstCardInfo());

    }

}