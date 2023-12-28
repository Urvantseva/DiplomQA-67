package test;

import com.codeborne.selenide.logevents.SelenideLogger;
import data.DataHelper;
import data.SQLHelper;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.*;
import page.CreditPage;
import page.MainPage;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class CreditPaymentTest {
    private MainPage mainPage;
    private CreditPage creditPage;

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
        creditPage = mainPage.goToCreditPage();
    }

    @AfterEach
    public void cleanBase() {
        SQLHelper.cleanDatabase();
    }

    @Test
    @DisplayName("Покупка картой «APPROVED»")
    void shouldTestBuyCardForStatusApproved() {

        CreditPage.fillInCardInfo(DataHelper.getCardNumberForStatusApproved());
        CreditPage.setSuccessNotificationVisible();
        assertEquals("APPROVED", SQLHelper.getStatusForCredit());
    }
    @Test
    @DisplayName("Покупка картой «DECLINED»")
    void shouldTestBuyCardForStatusDeclined() {

        CreditPage.fillInCardInfo(DataHelper.getCardNumberForStatusDeclined());
        CreditPage.setErrorNotificationVisible();
        assertEquals("DECLINED", SQLHelper.getStatusForCredit());
    }
    @Test
    @DisplayName("В поле ввода номера карты ввести менее 16 символов.")
    void shouldTestTheBuyWithA14DigitCard() {

        CreditPage.fillInCardInfo(DataHelper.getCardNumberOf15Digits());
        CreditPage.setInvalidFormatVisible();
    }
    @Test
    @DisplayName("Ввести 16 нулей 0000 0000 0000 0000.")
    void shouldTestTheBuyWithACardNumberZero() {

        CreditPage.fillInCardInfo(DataHelper.getCardNumberOfZero());
        CreditPage.setErrorNotificationVisible();
    }
    @Test
    @DisplayName("Поле ввода «Номер карты» оставить пустым")
    void shouldTestThePurchaseWithAnEmptyCardNumberField() {

        CreditPage.fillInCardInfo(DataHelper.getNonStatusCardNumber());
        CreditPage.setRequiredFieldVisible();
    }
    @Test
    @DisplayName("Ввести номер месяца больше 12.")
    void shouldTestMonthFieldForOverUpperBoundaryValue() {

        CreditPage.fillInCardInfo(DataHelper.getMonthNotValid());
        CreditPage.setInvalidCardExpirationDateMessageVisible();
    }
    @Test
    @DisplayName("Поле ввода «Месяц» оставить пустым")
    void shouldTestEmptyMonthField() {

        CreditPage.fillInCardInfo(DataHelper.getMonthEmptyField());
        CreditPage.setRequiredFieldVisible();
    }
    @Test
    @DisplayName("Ввести истёкший срок действия карты.")
    void shouldTestPatsValueForYearField() {

        CreditPage.fillInCardInfo(DataHelper.getThePastValueInTheYearField());
        CreditPage.setCardExpiredMessageVisible();
    }
    @Test
    @DisplayName("Поле ввода «Год» оставить пустым")
    void shouldTestEmptyYearField() {

        CreditPage.fillInCardInfo(DataHelper.getYearEmptyField());
        CreditPage.setRequiredFieldVisible();
    }
    @Test
    @DisplayName("Ввести в поле ввода «Владелец» только имя.")
    void shouldTestHolderWithOneName() {

        CreditPage.fillInCardInfo(DataHelper.getHolderWithOneName());
        CreditPage.setInvalidFormatVisible();
    }
    @Test
    @DisplayName("Ввести Фамилию, имя кириллицей")
    void shouldTestHolderInCyrillic() {

        CreditPage.fillInCardInfo(DataHelper.getHolderInCyrillic());
        CreditPage.setInvalidCharMessageVisible();
    }
    @Test
    @DisplayName("Ввести в поле ввода «Владелец» цифры")
    void shouldTestHolderForDigits() {

        CreditPage.fillInCardInfo(DataHelper.getHolderFromDigits());
        CreditPage.setInvalidCharMessageVisible();
    }
    @Test
    @DisplayName("Ввести в поле ввода «Владелец» спецсимволы")
    void shouldTestHolderForSpecialCharacters() {

        CreditPage.fillInCardInfo(DataHelper.getHolderFromSpecialCharacters());
        CreditPage.setInvalidCharMessageVisible();
    }
    @Test
    @DisplayName("Поле ввода «Владелец» оставить пустым")
    void shouldTestEmptyHolderField() {

        CreditPage.fillInCardInfo(DataHelper.getHolderEmptyField());
        CreditPage.setRequiredFieldVisible();
    }
    @Test
    @DisplayName("В поле «CVC/CVV» ввестит две цифры.")
    void shouldTestCVCTwoDigits() {

        CreditPage.fillInCardInfo(DataHelper.getCVCTwoDigits());
        CreditPage.setInvalidFormatVisible();
    }
    @Test
    @DisplayName("В поле «CVC/CVV» ввести 000.")
    void shouldTestCVCFieldOfZero() {

        CreditPage.fillInCardInfo(DataHelper.getCVCOfZero());
        CreditPage.setInvalidFormatVisible();
    }
    @Test
    @DisplayName("Поле «CVC/CVV» оставить пустым.")
    void shouldTestEmptyCVCField() {

        CreditPage.fillInCardInfo(DataHelper.getCVCEmptyField());
        CreditPage.textValidationForTheCVCField("Поле обязательно для заполнения");
    }
    @Test
    @DisplayName("Оставить все поля не заполненными.")
    void shouldTestSendingAnEmptyForm() {

        CreditPage.fillInCardInfo(DataHelper.getFormFromEmptyFields());
        CreditPage.textValidationForTheCardNumberField("Поле обязательно для заполнения");
        CreditPage.textValidationForTheMonthField("Поле обязательно для заполнения");
        CreditPage.textValidationForTheYearField("Поле обязательно для заполнения");
        CreditPage.textValidationForTheHolderField("Поле обязательно для заполнения");
        CreditPage.textValidationForTheCVCField("Поле обязательно для заполнения");
    }
}
