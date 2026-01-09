package com.proyek.tests.index;

import java.time.Duration;

import org.junit.jupiter.api.AfterAll;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.proyek.base.BaseTest;
import com.proyek.pages.auth.LoginPage;
import com.proyek.pages.index.DashboardPage;
import com.proyek.pages.index.MyBilling;
import com.proyek.pages.index.MyPaymentMethod;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;

@Epic("GLPI MyAccount - Payment Management")
@Feature("Add Payment Method")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class MyPaymentMethodTest extends BaseTest {

    private MyPaymentMethod paymentMethodPage;
    private WebDriver driver;
    private LoginPage loginPage;
    private DashboardPage dashboardPage;
    private MyBilling myBillingPage;

    private final String USERNAME = "demoasdffdsao@test.com";
    private final String PASSWORD = "NewPassword123!";

    @BeforeAll
    void setupAll() {
        driver = new ChromeDriver();

        // 1️⃣ LoginPage
        loginPage = new LoginPage(driver);

        // 2️⃣ Buka login page dan login
        loginPage.open();
        loginPage.fillCompleteLoginForm(USERNAME, PASSWORD);
        loginPage.clickSubmit();

        // 3️⃣ Tunggu sampai URL dashboard muncul
        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(webDriver -> webDriver.getCurrentUrl().contains("mainmenu=home"));

        // 4️⃣ DashboardPage baru dibuat setelah login
        dashboardPage = new DashboardPage(driver);

        // 5️⃣ Buka MyBilling dari dashboard
        dashboardPage.clickSeeBilling();

        // 6️⃣ MyBillingPage
        myBillingPage = new MyBilling(driver);
        myBillingPage.clickAddPaymentMode();

        // 7️⃣ MyPaymentMethodPage
        paymentMethodPage = new MyPaymentMethod(driver);

        // Pastikan URL dashboard
        assertTrue(paymentMethodPage.isOnPaymentMethodPage(), "Login gagal atau tidak diarahkan ke halaman payment method");
    }

    @AfterAll
    void teardownAll() {
        if (driver != null) {
            driver.quit();
        }
    }

    // ================= TEST CASE 1: PAGE LOAD =================
    @Test
    @Story("Page Display")
    @Severity(SeverityLevel.CRITICAL)
    @DisplayName("Halaman Add Payment Method tampil dengan form yang benar")
    @Description("Memverifikasi bahwa halaman Add Payment Method dapat diakses dan menampilkan form payment dengan lengkap")
    void testPaymentMethodPageLoad() {
        paymentMethodPage.open();

        assertTrue(paymentMethodPage.isOnPaymentMethodPage(), 
            "URL tidak mengarah ke halaman payment method");
    }

    // ================= TEST CASE 2: REQUIRED FIELD VALIDATION =================
    @Test
    @Story("Form Validation")
    @Severity(SeverityLevel.CRITICAL)
    @DisplayName("Validasi field wajib - Cardholder Name kosong")
    @Description("Memverifikasi bahwa sistem menampilkan error message ketika field Name on card dikosongkan")
    void testRequiredFieldValidation() {
        // Step 1: Buka halaman Add Payment Method
        paymentMethodPage.open();
        
        // Step 2: Pastikan Card payment selected
        paymentMethodPage.selectCardPayment();
        
        // Step 3: Kosongkan field Cardholder Name (leave empty)
        // Fill other fields only
        paymentMethodPage.fillCardNumber("4242424242424242");
        paymentMethodPage.fillExpirationDate("1228");
        paymentMethodPage.fillCvc("123");
        
        // Step 4: Klik Save and Pay button
        paymentMethodPage.clickSaveAndPay();
        
        // Step 5: Verifikasi error message muncul di #card-errors
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement errorMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(
            By.id("card-errors")
        ));
        
        String errorText = errorMessage.getText();
        assertTrue(errorText.contains("Field 'Name on card' is required"), 
            "Error message tidak sesuai. Expected: Field 'Name on card' is required, Actual: " + errorText);
    }

    // ================= TEST CASE 3: INVALID CARD FORMAT =================
    @Test
    @Story("Form Validation")
    @Severity(SeverityLevel.NORMAL)
    @DisplayName("TC-PM-003: Validasi format kartu tidak valid")
    @Description("Memverifikasi bahwa sistem menampilkan error message untuk nomor kartu yang tidak valid")
    void testInvalidCardNumberFormat() {
        // Step 1: Buka halaman Add Payment Method
        paymentMethodPage.open();
        
        // Step 2: Pastikan Card payment selected
        paymentMethodPage.selectCardPayment();
        
        // Step 3: Fill form dengan nomor kartu dummy/invalid
        paymentMethodPage.fillCardholderName("Test User");
        paymentMethodPage.fillCardNumber("1111111111111111"); // Invalid card number
        paymentMethodPage.fillExpirationDate("1228");
        paymentMethodPage.fillCvc("123");
        
        // Step 4: Klik Save and Pay button
        paymentMethodPage.clickSaveAndPay();
        
        // Step 5: Verifikasi error message untuk invalid card number di #card-errors
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement errorMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(
            By.id("card-errors")
        ));
        
        String errorText = errorMessage.getText();
        assertTrue(errorText.contains("Your card number is invalid"), 
            "Error message tidak sesuai. Expected: Your card number is invalid, Actual: " + errorText);
    }
}
