package com.proyek.tests.index;

import java.time.Duration;

import org.junit.jupiter.api.AfterAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.proyek.pages.auth.LoginPage;
import com.proyek.pages.index.DashboardPage;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class DashboardPageTest {

    private WebDriver driver;
    private LoginPage loginPage;
    private DashboardPage dashboardPage;

    private final String USERNAME = "testdemo224@gmail.com";
    private final String PASSWORD = "Passworddemo2.";

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

        // Pastikan URL dashboard
        assertTrue(dashboardPage.isOnDashboardPage(), "Login gagal atau tidak diarahkan ke dashboard");
    }

    @AfterAll
    void teardownAll() {
        if (driver != null) {
            driver.quit();
        }
    }

    // ================= VISIBILITY TESTS =================
    @Test
    @DisplayName("Add Payment Mode button should be visible")
    void testAddPaymentModeBtnVisible() {
        assertTrue(dashboardPage.isAddPaymentModeBtnVisible(), "Add Payment Mode button tidak terlihat!");
    }

    @Test
    @DisplayName("Go To Application button should be visible")
    void testGoToApplicationBtnVisible() {
        assertTrue(dashboardPage.isGoToApplicationBtnVisible(), "Go To Application button tidak terlihat!");
    }

    @Test
    @DisplayName("See Instances button should be visible")
    void testSeeInstancesBtnVisible() {
        assertTrue(dashboardPage.isSeeInstancesBtnVisible(), "See Instances button tidak terlihat!");
    }

    @Test
    @DisplayName("See Profile button should be visible")
    void testSeeProfileBtnVisible() {
        assertTrue(dashboardPage.isSeeProfileBtnVisible(), "See Profile button tidak terlihat!");
    }

    @Test
    @DisplayName("See Billing button should be visible")
    void testSeeBillingBtnVisible() {
        assertTrue(dashboardPage.isSeeBillingBtnVisible(), "See Billing button tidak terlihat!");
    }

    // ================= CLICK TESTS =================
    @Test
    @DisplayName("Click Add Payment Mode button navigates correctly")
    void testClickAddPaymentMode() {
        dashboardPage.clickAddPaymentMode();
        // Bisa tambahkan assertion untuk URL baru atau modal terbuka
        assertTrue(driver.getCurrentUrl().contains("registerpaymentmode"),
                "Klik Add Payment Mode tidak menuju URL yang diharapkan!");
        driver.navigate().back(); // Kembali ke dashboard
    }

    @Test
    @DisplayName("Click Go To Application button opens new tab")
    void testClickGoToApplication() {
        String mainWindow = driver.getWindowHandle();
        dashboardPage.clickGoToApplication();
        assertEquals(mainWindow, driver.getWindowHandle(), "Kembali ke tab utama gagal!");
    }

    @Test
    @DisplayName("Click See Instances button navigates correctly")
    void testClickSeeInstances() {
        dashboardPage.clickSeeInstances();
        assertTrue(driver.getCurrentUrl().contains("mode=instances"),
                "Klik See Instances tidak menuju URL yang diharapkan!");
        dashboardPage.open(); // buka dashboard fresh

    }

    @Test
    @DisplayName("Click See Profile button navigates correctly")
    void testClickSeeProfile() {
        dashboardPage.clickSeeProfile();
        assertTrue(driver.getCurrentUrl().contains("mode=myaccount"),
                "Klik See Profile tidak menuju URL yang diharapkan!");
        driver.navigate().back();
    }

    @Test
    @DisplayName("Click See Billing button navigates correctly")
    void testClickSeeBilling() {
        dashboardPage.clickSeeBilling();
        assertTrue(driver.getCurrentUrl().contains("mode=billing"),
                "Klik See Billing tidak menuju URL yang diharapkan!");
        driver.navigate().back();
    }

    // ================= URL TEST =================
    @Test
    @DisplayName("Dashboard page URL is correct")
    void testDashboardPageUrl() {
        assertTrue(dashboardPage.isOnDashboardPageModeDashboard(), "URL saat ini bukan dashboard!");
    }
}
