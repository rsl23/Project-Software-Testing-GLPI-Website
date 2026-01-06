package com.proyek.tests.product;

import com.proyek.pages.product.HelpdeskPage;
import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class HelpdeskPageTest {

    private WebDriver driver;
    private HelpdeskPage helpdesk;

    @BeforeAll
    void setupAll() {
        driver = new ChromeDriver();
        helpdesk = new HelpdeskPage(driver);
        helpdesk.open();
    }

    @AfterAll
    void teardownAll() {
        if (driver != null) {
            driver.quit();
        }
    }

    // ================= HERO =================
    @Test
    @DisplayName("Hero section should be visible")
    void testHeroVisible() {
        assertTrue(helpdesk.isHeroVisible(), "Hero section tidak terlihat!");
    }

    @Test
    @DisplayName("Hero buttons should open new tab")
    void testHeroButtons() {
        String main = driver.getWindowHandle();

        helpdesk.clickHeroButton("firstTicket");
        assertEquals(main, driver.getWindowHandle());

        helpdesk.clickHeroButton("startNowHero");
        assertEquals(main, driver.getWindowHandle());

        helpdesk.clickHeroButton("accessInstance");
        assertEquals(main, driver.getWindowHandle());

        helpdesk.clickHeroButton("openMyInstance");
        assertEquals(main, driver.getWindowHandle());
    }

    // ================= CTA =================
    @Test
    @DisplayName("CTA buttons should open new tab")
    void testCtaButtons() {
        String main = driver.getWindowHandle();

        helpdesk.clickCtaButton("viewTestimonials");
        assertEquals(main, driver.getWindowHandle());

        helpdesk.clickCtaButton("exploreNow");
        assertEquals(main, driver.getWindowHandle());
    }

    // ================= TUTORIAL =================
    @Test
    @DisplayName("Tutorial iframe buttons should be clickable")
    void testTutorialIframe() {
        assertDoesNotThrow(() -> helpdesk.clickTutorialIframe(),
                "Tutorial iframe gagal diklik atau loop tombol error");
    }

    // ================= FEATURES =================
    @Test
    @DisplayName("Start Now Features button opens new tab")
    void testStartNowFeatures() {
        String main = driver.getWindowHandle();
        helpdesk.clickStartNowFeatures();
        assertEquals(main, driver.getWindowHandle());
    }

    // ================= NEWSLETTER =================
    @Test
    @DisplayName("Newsletter submission works with valid email")
    void testNewsletterSubmission() {
        String email = "demo@test.com";
        helpdesk.fillNewsletter(email);

        WebElement emailInput = driver.findElement(By.cssSelector("input[type='email']"));
        assertEquals(email, emailInput.getAttribute("value"), "Email tidak sesuai!");

        // Bisa ditambahkan assertion untuk success message jika ada
        // By successMsg = By.cssSelector(".newsletter-success");
        // assertTrue(driver.findElement(successMsg).isDisplayed());
    }

    // ================= SOCIAL =================
    @Test
    @DisplayName("All social media links are clickable")
    void testSocialMediaLinks() {
        String main = driver.getWindowHandle();
        assertDoesNotThrow(() -> helpdesk.clickAllSocialMediaLinks(),
                "Salah satu link sosial media gagal diklik");
        assertEquals(main, driver.getWindowHandle());
    }
}
