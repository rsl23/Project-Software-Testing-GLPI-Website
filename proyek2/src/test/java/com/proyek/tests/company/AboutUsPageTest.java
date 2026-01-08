package com.proyek.tests.company;

import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.proyek.base.BaseTest;
import com.proyek.pages.company.AboutUsPage;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;

@Epic("GLPI Website Testing")
@Feature("About Us Page")
public class AboutUsPageTest extends BaseTest {

    private AboutUsPage aboutUsPage;

    @BeforeEach
    public void setup() {
        aboutUsPage = new AboutUsPage(driver);
        aboutUsPage.open();
        sleep(1000); // Tunggu halaman load
    }

    @Test
    @DisplayName("Check About Us headline is visible")
    @Description("Verify that the About Us headline is visible on the page")
    @Severity(SeverityLevel.CRITICAL)
    @Story("Headline Verification")
    public void testHeadlineVisible() {
        sleep(500); // Jeda sebelum verifikasi
        assertTrue(aboutUsPage.isHeadlineVisible(), "About Us headline should be visible");
    }

    @Test
    @DisplayName("Check Discover our Jobs button is visible and works")
    @Description("Verify that the Discover our Jobs button is visible and opens the careers page in a new tab")
    @Severity(SeverityLevel.NORMAL)
    @Story("Button Navigation")
    public void testDiscoverJobsButton() {
        sleep(500); // Jeda sebelum verifikasi button
        assertTrue(aboutUsPage.isDiscoverJobsButtonVisible(), "Discover our jobs button should be visible");

        String mainWindow = driver.getWindowHandle();
        sleep(800); // Jeda sebelum klik button agar terlihat
        aboutUsPage.clickDiscoverJobs();
        sleep(1000); // Tunggu tab baru terbuka

        // Pindah ke tab baru dan cek URL
        for (String win : driver.getWindowHandles()) {
            if (!win.equals(mainWindow)) {
                driver.switchTo().window(win);
                break;
            }
        }
        sleep(500); // Tunggu halaman jobs load

        assertTrue(driver.getCurrentUrl().contains("welcometothejungle.com"), "Should navigate to jobs page");
        driver.close();
        driver.switchTo().window(mainWindow);
    }

    // Helper method untuk sleep
    private void sleep(int milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
