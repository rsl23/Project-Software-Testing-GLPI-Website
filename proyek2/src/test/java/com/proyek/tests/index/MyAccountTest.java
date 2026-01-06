package com.proyek.tests.index;

import com.proyek.pages.auth.LoginPage;
import com.proyek.pages.index.DashboardPage;
import com.proyek.pages.index.MyAccount;
import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class MyAccountTest {

    private WebDriver driver;
    private LoginPage loginPage;
    private DashboardPage dashboardPage;
    private MyAccount myAccountPage;

    private final String USERNAME = "testdemo222@gmail.com";
    private final String PASSWORD = "NewPassword123!";

    @BeforeAll
    void setupAll() {
        driver = new ChromeDriver();

        // 1️⃣ LoginPage
        loginPage = new LoginPage(driver);
        loginPage.open();
        loginPage.fillCompleteLoginForm(USERNAME, PASSWORD);
        loginPage.clickSubmit();

        // 2️⃣ Tunggu sampai dashboard muncul
        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(webDriver -> webDriver.getCurrentUrl().contains("mainmenu=home"));

        // 3️⃣ DashboardPage
        dashboardPage = new DashboardPage(driver);

        // 4️⃣ Buka MyAccount dari dropdown
        dashboardPage.clickSeeProfile();

        // 5️⃣ MyAccountPage
        myAccountPage = new MyAccount(driver);

        // Pastikan di halaman My Account
        assertTrue(myAccountPage.isOnMyAccountPage(), "Tidak diarahkan ke halaman My Account");
    }

    @AfterAll
    void teardownAll() {
        if (driver != null) {
            driver.quit();
        }
    }

    // ================= VISIBILITY TESTS =================
    @Test
    @DisplayName("Organization form fields are visible")
    void testOrganizationFieldsVisibility() {
        assertTrue(myAccountPage.isOrgNameFieldVisible());
        assertTrue(myAccountPage.isAddressFieldVisible());
        assertTrue(myAccountPage.isTownFieldVisible());
        assertTrue(myAccountPage.isZipFieldVisible());
        assertTrue(myAccountPage.isStateOrCountyFieldVisible());
        assertTrue(myAccountPage.isCountryDropdownVisible());
        assertTrue(myAccountPage.isVatassujCheckboxVisible());
        assertTrue(myAccountPage.isVatNumberFieldVisible());
        assertTrue(myAccountPage.isProfidFieldVisible());
        assertTrue(myAccountPage.isOrgSaveButtonVisible());
    }

    @Test
    @DisplayName("Contact form fields are visible")
    void testContactFieldsVisibility() {
        assertTrue(myAccountPage.isEmailFieldVisible());
        assertTrue(myAccountPage.isPhoneFieldVisible());
        assertTrue(myAccountPage.isFirstNameFieldVisible());
        assertTrue(myAccountPage.isLastNameFieldVisible());
        assertTrue(myAccountPage.isEmailCcInvoiceFieldVisible());
        assertTrue(myAccountPage.isOptinMessagesCheckboxVisible());
        assertTrue(myAccountPage.isContactSaveButtonVisible());
    }

    @Test
    @DisplayName("Password fields are visible")
    void testPasswordFieldsVisibility() {
        assertTrue(myAccountPage.isPasswordFieldVisible());
        assertTrue(myAccountPage.isPassword2FieldVisible());
        assertTrue(myAccountPage.isChangePasswordButtonVisible());
    }

    @Test
    @DisplayName("Links are visible")
    void testLinksVisibility() {
        assertTrue(myAccountPage.isAddPaymentModeLinkVisible());
        assertTrue(myAccountPage.isDeleteAccountLinkVisible());
    }

    // ================= FORM FILLING TESTS =================
    @Test
    @DisplayName("Fill organization form")
    void testFillOrganizationForm() {
        myAccountPage.fillCompleteOrganizationForm(
                "Test Org",
                "Jl. Example 123",
                "Jakarta",
                "12345",
                "DKI Jakarta",
                "Indonesia (ID)",
                "PROFID123");
        myAccountPage.clickOrgSaveButton();

        // Assert success message muncul
        assertTrue(myAccountPage.isOrgSaveSuccessVisible(),
                "Organization save success message tidak muncul");
    }

    @Test
    @DisplayName("Fill contact form")
    void testFillContactForm() {
        myAccountPage.fillCompleteContactForm(
                "demoo@test.com",
                "081234567890",
                "John",
                "Doe",
                "billing@test.com");
        myAccountPage.clickContactSaveButton();

        // Assert success message muncul
        assertTrue(myAccountPage.isContactSaveSuccessVisible(),
                "Contact save success message tidak muncul");
    }

    @Test
    @DisplayName("Change password form")
    void testChangePassword() {
        myAccountPage.fillCompletePasswordForm("NewPassword123!");
        myAccountPage.clickChangePasswordButton();

        // Assert success message muncul
        assertTrue(myAccountPage.isChangePasswordSuccessVisible(),
                "Change password success message tidak muncul");
    }

    // ================= LINKS TEST =================
    @Test
    @DisplayName("Click Add Payment Mode link")
    void testClickAddPaymentModeLink() {
        myAccountPage.clickAddPaymentMode();

        // Assert URL baru terbuka
        assertTrue(driver.getCurrentUrl().contains("paymentmode"),
                "URL tidak diarahkan ke Payment Mode page");

        driver.get("https://myaccount.glpi-network.cloud/index.php?mode=myaccount");
    }

    @Test
    @DisplayName("Click Delete Account link")
    void testClickDeleteAccountLink() {
        WebElement infoMessage = myAccountPage.clickDeleteAccount();

        // Assert pesan muncul
        assertTrue(infoMessage.getText().contains(
                "To be able to close your account, your must first delete/close your online instances"),
                "Pesan delete account tidak sesuai");
    }

}
