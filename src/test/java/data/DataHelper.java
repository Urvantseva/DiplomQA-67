package data;

import com.github.javafaker.Faker;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class DataHelper {
    private static final Faker fakerLangEn = new Faker(new Locale("en"));
    private static final Faker fakerLangRu = new Faker(new Locale("ru"));

    public static CardInfo getCardNumberForStatusApproved() { // Карта APPROVED Заполнена валидными данными.
        return new CardInfo("4444 4444 4444 4441",
                "12", "27","Ivan Ivanov", "123");
    }

    public static CardInfo getCardNumberForStatusDeclined() {  // Карта DECLINE Заполнена валидными данными.
        return new CardInfo("4444 4444 4444 4442",
                "12", "27","Ivan Ivanov", "123");
    }

    // ПОЛЕ ВВОДА «НОМЕР КАРТЫ».
    public static CardInfo getCardNumberOf15Digits() { // В поле ввода номера карты ввести менее 16 символов.
        return new CardInfo("4444 4444 4444 444",
                "12", "27","Ivan Ivanov", "123");
    }

    public static CardInfo getCardNumberOfZero() {  // В поле ввода номера карты ввести 16 нулей 0000 0000 0000 0000.
        return new CardInfo("0000 0000 0000 0000",
                "12", "27","Ivan Ivanov", "123");
    }

    public static CardInfo getNonStatusCardNumber() {  // Поле ввода номера карты оставить пустым.
        return new CardInfo("",
                "12", "27","Ivan Ivanov", "123");
    }

    // ПОЛЕ ВВОДА «МЕСЯЦ».
    public static CardInfo getMonthNotValid() {  // В поле «Месяц» ввести номер месяца больше 12.
        return new CardInfo("4444 4444 4444 4441", "13",
                "27","Ivan Ivanov", "123");
    }

    public static CardInfo getMonthEmptyField() {  // Поле «Месяц» оставить пустым.
        return new CardInfo("4444 4444 4444 4441", "",
                "27","Ivan Ivanov", "123");
    }
    // ПОЛЕ ВВОДА «ГОД».

    public static CardInfo getThePastValueInTheYearField() {  // В поле «Год» ввести истекший срок действия карты.
        return new CardInfo("4444 4444 4444 4441",
                "12", "00", "Ivan Ivanov", "123");
    }

    public static CardInfo getYearEmptyField() {  // Поле «Год» оставить пустым.
        return new CardInfo("4444 4444 4444 4441",
                "12", "", "Ivan Ivanov", "123");
    }

    // ПОЛЕ ВВОДА «ВЛАДЕЛЕЦ».
    public static CardInfo getHolderWithOneName() {  // В поле «Владелец» ввести только Фамилию(или только Имя).
        return new CardInfo("4444 4444 4444 4441",
                "12", "27", "Ivanov", "123");
    }

    public static CardInfo getHolderInCyrillic() {  // В поле «Владелец» ввести имя, фамилию кириллицей.
        return new CardInfo("4444 4444 4444 4441",
                "12", "27", "Иван Иванов", "123");
    }

    public static CardInfo getHolderFromDigits() {  // В поле «Владелец» ввести цифры.
        return new CardInfo("4444 4444 4444 4441",
                "12", "27", "85Ivan Ivanov", "123");
    }

    public static CardInfo getHolderFromSpecialCharacters() {  // В поле «Владелец» ввести спецсимволы.
        return new CardInfo("4444 4444 4444 4441",
                "12", "27", "#Ivan Ivanov", "123");
    }

    public static CardInfo getHolderEmptyField() {  // Поле «Владелец» оставить пустым.
        return new CardInfo("4444 4444 4444 4441",
                "12", "27", "", "123");
    }

    // ПОЛЕ ВВОДА «CVC/CVV».
    public static CardInfo getCVCTwoDigits() {  // В поле «CVC/CVV» ввестит две цифры.
        return new CardInfo("4444 4444 4444 4441",
                "12", "27","Ivan Ivanov", "12");
    }

    public static CardInfo getCVCOfZero() {  // В поле «CVC/CVV» ввести 000.
        return new CardInfo("4444 4444 4444 4441",
                "12", "27","Ivan Ivanov", "000");
    }

    public static CardInfo getCVCEmptyField() {  // Поле «CVC/CVV» оставить пустым.
        return new CardInfo("4444 4444 4444 4441",
                "12", "27","Ivan Ivanov", "");
    }

    // ПУСТЫЕ ПОЛЯ ВВОДА
    public static CardInfo getFormFromEmptyFields() {
        return new CardInfo("", "", "", "", "");
    }

    public static String getValidMonth() {
        String validMonth = LocalDate.now().format(DateTimeFormatter.ofPattern("MM"));
        return validMonth;
    }

    public static String getPastYear() {
        String pastYear = LocalDate.now().minusYears(1).format(DateTimeFormatter.ofPattern("yy"));
        return pastYear;
    }

    public static String getCurrentYear() {
        String currentYear = LocalDate.now().format(DateTimeFormatter.ofPattern("yy"));
        return currentYear;
    }

    public static String getAlwaysValidYear() {
        String alwaysValidYear = LocalDate.now().plusYears(2).format(DateTimeFormatter.ofPattern("yy"));
        return alwaysValidYear;
    }

    public static String getFutureYear() {
        String futureYear = LocalDate.now().plusYears(6).format(DateTimeFormatter.ofPattern("yy"));
        return futureYear;
    }


    public static String getValidHolder() {
        return fakerLangEn.name().firstName().toUpperCase() + " " + fakerLangEn.name().lastName().toUpperCase();
    }

    public static String getHolderRusLang() {
        String name = fakerLangRu.name().fullName();
        return name;
    }

    public static String getRandomCardNumber() {
        return fakerLangEn.business().creditCardNumber();
    }

    public static String getValidCVV() {
        return fakerLangEn.number().digits(3);
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class CardInfo {
        private String cardNumber;
        private String month;
        private String year;
        private String holder;
        private String cvc;
    }
}

