package test;

import com.codeborne.selenide.logevents.SelenideLogger;
import data.DataHelper;
import data.SQLHelper;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.*;
import page.MainPage;
import page.PaymentPage;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class UsualPaymentTest {
    private MainPage mainPage;
    private PaymentPage paymentPage;

    @BeforeAll
    static void setUpAll() {
        SelenideLogger.addListener("allure", new AllureSelenide());
    }

    @AfterAll
    static void tearDownAll() {
        SelenideLogger.removeListener("allure");
    }

    @BeforeEach
    void setupTest() {

        open("http://localhost:8080/");
        mainPage = new MainPage();
        paymentPage = mainPage.goToPaymentPage();
    }

    @AfterEach
    public void cleanBase() {
        SQLHelper.cleanDatabase();
    }

    @Test
    @DisplayName("Покупка картой «APPROVED»")
    void shouldTestBuyCardForStatusApproved() {

        PaymentPage.fillInCardInfo(DataHelper.getCardNumberForStatusApproved());
        PaymentPage.setSuccessNotificationVisible();
        assertEquals("APPROVED", SQLHelper.getStatusForPayment());
    }
    @Test
    @DisplayName("Покупка картой «DECLINED»")
    void shouldTestBuyCardForStatusDeclined() {

        PaymentPage.fillInCardInfo(DataHelper.getCardNumberForStatusDeclined());
        PaymentPage.setErrorNotificationVisible();
        assertEquals("DECLINED", SQLHelper.getStatusForPayment());
    }
    @Test
    @DisplayName("В поле ввода номера карты ввести менее 16 символов.")
    void shouldTestTheBuyWithA14DigitCard() {

        PaymentPage.fillInCardInfo(DataHelper.getCardNumberOf15Digits());
        PaymentPage.setInvalidFormatVisible();
    }
    @Test
    @DisplayName("Ввести 16 нулей 0000 0000 0000 0000.")
    void shouldTestTheBuyWithACardNumberZero() {

        PaymentPage.fillInCardInfo(DataHelper.getCardNumberOfZero());
        PaymentPage.setErrorNotificationVisible();
    }
    @Test
    @DisplayName("Поле ввода «Номер карты» оставить пустым")
    void shouldTestThePurchaseWithAnEmptyCardNumberField() {

        PaymentPage.fillInCardInfo(DataHelper.getNonStatusCardNumber());
        PaymentPage.setRequiredFieldVisible();
    }
    @Test
    @DisplayName("Ввести номер месяца больше 12.")
    void shouldTestMonthFieldForOverUpperBoundaryValue() {

        PaymentPage.fillInCardInfo(DataHelper.getMonthNotValid());
        PaymentPage.setInvalidCardExpirationDateMessageVisible();
    }
    @Test
    @DisplayName("Поле ввода «Месяц» оставить пустым")
    void shouldTestEmptyMonthField() {

        PaymentPage.fillInCardInfo(DataHelper.getMonthEmptyField());
        PaymentPage.setRequiredFieldVisible();
    }
    @Test
    @DisplayName("Ввести истёкший срок действия карты.")
    void shouldTestPatsValueForYearField() {

        PaymentPage.fillInCardInfo(DataHelper.getThePastValueInTheYearField());
        PaymentPage.setCardExpiredMessageVisible();
    }
    @Test
    @DisplayName("Поле ввода «Год» оставить пустым")
    void shouldTestEmptyYearField() {

        PaymentPage.fillInCardInfo(DataHelper.getYearEmptyField());
        PaymentPage.setRequiredFieldVisible();
    }
    @Test
    @DisplayName("Ввести в поле ввода «Владелец» только имя.")
    void shouldTestHolderWithOneName() {

        PaymentPage.fillInCardInfo(DataHelper.getHolderWithOneName());
        PaymentPage.setInvalidFormatVisible();
    }
    @Test
    @DisplayName("Ввести Фамилию, имя кириллицей")
    void shouldTestHolderInCyrillic() {

        PaymentPage.fillInCardInfo(DataHelper.getHolderInCyrillic());
        PaymentPage.setInvalidCharMessageVisible();
    }
    @Test
    @DisplayName("Ввести в поле ввода «Владелец» цифры")
    void shouldTestHolderForDigits() {

        PaymentPage.fillInCardInfo(DataHelper.getHolderFromDigits());
        PaymentPage.setInvalidCharMessageVisible();
    }
    @Test
    @DisplayName("Ввести в поле ввода «Владелец» спецсимволы")
    void shouldTestHolderForSpecialCharacters() {

        PaymentPage.fillInCardInfo(DataHelper.getHolderFromSpecialCharacters());
        PaymentPage.setInvalidCharMessageVisible();
    }
    @Test
    @DisplayName("Поле ввода «Владелец» оставить пустым")
    void shouldTestEmptyHolderField() {

        PaymentPage.fillInCardInfo(DataHelper.getHolderEmptyField());
        PaymentPage.setRequiredFieldVisible();
    }
    @Test
    @DisplayName("В поле «CVC/CVV» ввестит две цифры.")
    void shouldTestCVCTwoDigits() {

        PaymentPage.fillInCardInfo(DataHelper.getCVCTwoDigits());
        PaymentPage.setInvalidFormatVisible();
    }
    @Test
    @DisplayName("В поле «CVC/CVV» ввести 000.")
    void shouldTestCVCFieldOfZero() {

        PaymentPage.fillInCardInfo(DataHelper.getCVCOfZero());
        PaymentPage.setInvalidFormatVisible();
    }
    @Test
    @DisplayName("Поле «CVC/CVV» оставить пустым.")
    void shouldTestEmptyCVCField() {

        PaymentPage.fillInCardInfo(DataHelper.getCVCEmptyField());
        PaymentPage.textValidationForTheCVCField("Поле обязательно для заполнения");
    }
    @Test
    @DisplayName("Оставить все поля не заполненными.")
    void shouldTestSendingAnEmptyForm() {

        PaymentPage.fillInCardInfo(DataHelper.getFormFromEmptyFields());
        PaymentPage.textValidationForTheCardNumberField("Поле обязательно для заполнения");
        PaymentPage.textValidationForTheMonthField("Поле обязательно для заполнения");
        PaymentPage.textValidationForTheYearField("Поле обязательно для заполнения");
        PaymentPage.textValidationForTheHolderField("Поле обязательно для заполнения");
        PaymentPage.textValidationForTheCVCField("Поле обязательно для заполнения");
    }
}
