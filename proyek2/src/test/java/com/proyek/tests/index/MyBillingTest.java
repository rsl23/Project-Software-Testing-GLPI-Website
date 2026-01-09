package com.proyek.tests.index;

import java.time.Duration;

import org.junit.jupiter.api.AfterAll;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.proyek.pages.auth.LoginPage;
import com.proyek.pages.index.DashboardPage;
import com.proyek.pages.index.MyBilling;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class MyBillingTest {

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
        loginPage.open();
        loginPage.fillCompleteLoginForm(USERNAME, PASSWORD);
        loginPage.clickSubmit();

        // 2️⃣ Tunggu sampai dashboard muncul
        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(webDriver -> webDriver.getCurrentUrl().contains("mainmenu=home"));

        // 3️⃣ DashboardPage
        dashboardPage = new DashboardPage(driver);

        // 4️⃣ MyBillingPage
        myBillingPage = new MyBilling(driver);
        myBillingPage.open();

        assertTrue(myBillingPage.isOnMyBillingPage(), "Tidak diarahkan ke halaman My Billing");
    }

    @AfterAll
    void teardownAll() {
        if (driver != null) {
            driver.quit();
        }
    }

    // ================= VISIBILITY TEST =================
    @Test
    @Order(1)
    @DisplayName("Check visibility of main elements")
    void testElementsVisibility() {
        assertTrue(myBillingPage.isContractLinkVisible(), "Contract link tidak terlihat");
        assertTrue(myBillingPage.isAddPaymentModeBtnVisible(), "Add Payment Mode button tidak terlihat");
    }

    // ================= ACTION TEST =================
    @Test
    @Order(2)
    @DisplayName("Click Contract link and switch to instance tab")
    void testClickContractLink() {
        myBillingPage.clickContractLink();

        // Simpan tab lama
        String originalTab = driver.getWindowHandle();

        // Tunggu tab baru terbuka
        new WebDriverWait(driver, Duration.ofSeconds(10)).until(webDriver -> driver.getWindowHandles().size() > 1);

        // Switch ke tab baru
        for (String handle : driver.getWindowHandles()) {
            if (!handle.equals(originalTab)) {
                driver.switchTo().window(handle);
                break;
            }
        }

        // Tunggu URL instance terbuka
        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(webDriver -> webDriver.getCurrentUrl().contains("glpi-network.cloud"));

        System.out.println("URL tab baru: " + driver.getCurrentUrl());
        assertTrue(driver.getCurrentUrl().contains("glpi-network.cloud"),
                "Tab baru tidak mengarah ke instance yang benar");

        // Opsional: bisa close tab baru dan kembali ke tab asli
        driver.close();
        driver.switchTo().window(originalTab);
    }

    @Test
    @Order(3)
    @DisplayName("Click Add Payment Mode button")
    void testClickAddPaymentMode() {
        myBillingPage.open(); // pastikan di halaman billing lagi
        myBillingPage.clickAddPaymentMode();

        // tunggu navigasi ke halaman register payment mode
        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(webDriver -> webDriver.getCurrentUrl().contains("mode=registerpaymentmode"));

        assertTrue(driver.getCurrentUrl().contains("mode=registerpaymentmode"),
                "Add Payment Mode button tidak diarahkan ke halaman yang benar");
    }
}
