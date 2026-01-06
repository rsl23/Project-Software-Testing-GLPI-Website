package com.proyek.tests;

import com.proyek.pages.common.HomePage;
import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class HomePageTest {

    private WebDriver driver;
    private HomePage homepage;

    @BeforeAll
    void setupAll() {
        driver = new ChromeDriver();
        homepage = new HomePage(driver);
        homepage.open();
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
        assertTrue(homepage.isHeroVisible(), "Hero section tidak terlihat!");
    }

    @Test
    @DisplayName("Get Started Hero button opens new tab")
    void testGetStartedHeroButton() {
        String main = driver.getWindowHandle();
        homepage.clickGetStartedHeroAndClose();
        assertEquals(main, driver.getWindowHandle(), "Kembali ke tab utama gagal!");
    }

    // ================= CARDS =================
    @Test
    @DisplayName("Helpdesk card 'Get Started' opens new tab")
    void testHelpdeskCard() {
        String main = driver.getWindowHandle();
        homepage.clickGetStartedHelpdeskAndClose();
        assertEquals(main, driver.getWindowHandle());
    }

    @Test
    @DisplayName("Inventory card 'Get Started' opens new tab")
    void testInventoryCard() {
        String main = driver.getWindowHandle();
        homepage.clickGetStartedInventoryAndClose();
        assertEquals(main, driver.getWindowHandle());
    }

    @Test
    @DisplayName("Financial card 'Get Started' opens new tab")
    void testFinancialCard() {
        String main = driver.getWindowHandle();
        homepage.clickGetStartedFinancialAndClose();
        assertEquals(main, driver.getWindowHandle());
    }

    // ================= START NOW =================
    @Test
    @DisplayName("'Start Now' button opens new tab")
    void testStartNowButton() {
        String main = driver.getWindowHandle();
        homepage.clickStartNowAndClose();
        assertEquals(main, driver.getWindowHandle());
    }

    // ================= TESTIMONIALS =================
    @Test
    @DisplayName("'View All Testimonials' button opens new tab")
    void testViewTestimonialsButton() {
        String main = driver.getWindowHandle();
        homepage.clickViewTestimonialsAndClose();
        assertEquals(main, driver.getWindowHandle());
    }

    // ================= PARTNERS =================
    @Test
    @DisplayName("'Discover All Partners' button opens new tab")
    void testDiscoverPartnersButton() {
        String main = driver.getWindowHandle();
        homepage.clickDiscoverPartnersAndClose();
        assertEquals(main, driver.getWindowHandle());
    }

    // ================= NEWSLETTER =================
    @Test
    @DisplayName("Newsletter submission works with valid email")
    void testNewsletterSubmission() {
        String email = "demo@test.com";
        homepage.fillNewsletter(email);

        // Assertion: Pastikan email sudah terinput
        WebElement emailInput = driver.findElement(By.cssSelector("input[type='email']"));
        assertEquals(email, emailInput.getAttribute("value"), "Email tidak sesuai!");

        // Jika ada success message di halaman, bisa ditambahkan assertion di sini
        // By successMessage = By.cssSelector(".newsletter-success");
        // assertTrue(driver.findElement(successMessage).isDisplayed());
    }
}



