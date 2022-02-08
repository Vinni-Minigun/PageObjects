package ru.netology.data;

import lombok.Value;
import ru.netology.page.AccountPage;

import java.util.Random;

public class DataHelper {
    private DataHelper() {
    }

    @Value
    public static class AuthInfo {
        private String login;
        private String password;

    }

    public static AuthInfo getAuthInfo() {
        return new AuthInfo("vasya", "qwerty123");
    }

    public static AuthInfo getInvalidPassword() {
        return new AuthInfo("petya", "qwerty");
    }

    public static AuthInfo getInvalidLogin() {
        return new AuthInfo("ilya", "qwerty123");
    }

    @Value
    public static class VerificationCode {
        private String code;
    }

    public static VerificationCode getVerificationCodeFor(AuthInfo authInfo) {
        return new VerificationCode("12345");
    }

    public static VerificationCode getInvalidVerificationCodeFor(AuthInfo authInfo) {
        return new VerificationCode("54321");
    }

    @Value
    public static class CardInfo {
        private String cardNumber;
    }

    public static CardInfo getFirstCardInfo() {
        return new CardInfo("5559 0000 0000 0001");
    }

    public static CardInfo getSecondCardInfo() {
        return new CardInfo("5559 0000 0000 0002");
    }

    public static CardInfo getInvalidFirstCardInfo() {
        return new CardInfo("0000 0000 0000 0001");
    }

    public static int getAmount(int cardBalance) {
        Random r = new Random();
        int amount = r.nextInt(cardBalance);
        return amount;
    }
}