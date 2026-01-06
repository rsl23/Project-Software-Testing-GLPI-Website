package com.proyek.tests;

import com.proyek.base.BaseTest;
import com.proyek.pages.company.AboutUsPage;
import io.qameta.allure.*;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

@Epic("GLPI Website Testing")
@Feature("About Us Page")
public class AboutUsPageTest extends BaseTest {

    private AboutUsPage aboutUsPage;

    @BeforeEach
    public void setup() {
        aboutUsPage = new AboutUsPage(driver);
        aboutUsPage.open();
    }

    @Test
    @DisplayName("Check About Us headline is visible")
    @Description("Verify that the About Us headline is visible on the page")
    @Severity(SeverityLevel.CRITICAL)
    @Story("Headline Verification")
    public void testHeadlineVisible() {
        assertTrue(aboutUsPage.isHeadlineVisible(), "About Us headline should be visible");
    }

    @Test
    @DisplayName("Check Discover our Jobs button is visible and works")
    @Description("Verify that the Discover our Jobs button is visible and opens the careers page in a new tab")
    @Severity(SeverityLevel.NORMAL)
    @Story("Button Navigation")
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



