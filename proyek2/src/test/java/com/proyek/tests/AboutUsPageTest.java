package com.proyek.tests;

import com.proyek.base.BaseTest;
import com.proyek.pages.AboutUsPage;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

public class AboutUsPageTest extends BaseTest {

    private AboutUsPage aboutUsPage;

    @BeforeEach
    public void setup() {
        aboutUsPage = new AboutUsPage(driver);
        aboutUsPage.open();
    }

    @Test
    @DisplayName("Check About Us headline is visible")
    public void testHeadlineVisible() {
        assertTrue(aboutUsPage.isHeadlineVisible(), "About Us headline should be visible");
    }

    @Test
    @DisplayName("Check Discover our Jobs button is visible and works")
    public void testDiscoverJobsButton() {
        assertTrue(aboutUsPage.isDiscoverJobsButtonVisible(), "Discover our jobs button should be visible");

        String mainWindow = driver.getWindowHandle();
        aboutUsPage.clickDiscoverJobs();

        // Pindah ke tab baru dan cek URL
        for (String win : driver.getWindowHandles()) {
            if (!win.equals(mainWindow)) {
                driver.switchTo().window(win);
                break;
            }
        }

        assertTrue(driver.getCurrentUrl().contains("welcometothejungle.com"), "Should navigate to jobs page");
        driver.close();
        driver.switchTo().window(mainWindow);
    }
}
