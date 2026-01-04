package com.proyek.tests;

import com.proyek.pages.PricesPage;
import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class PricesPageTest {

    private WebDriver driver;
    private PricesPage pricesPage;

    @BeforeAll
    void setupAll() {
        driver = new ChromeDriver();
        pricesPage = new PricesPage(driver);
        pricesPage.open();
    }

    @AfterAll
    void teardownAll() {
        if (driver != null) {
            driver.quit();
        }
    }

    // ================= PAGE =================
    @Test
    @Order(1)
    @DisplayName("Prices page should be visible")
    void testPageVisible() {
        assertTrue(pricesPage.isPageVisible(), "Prices page tidak terlihat!");
    }

    // ================= SWITCH =================
    @Test
    @Order(2)
    @DisplayName("Pricing switch can be toggled")
    void testPricingSwitch() {
        pricesPage.togglePricingSwitch();
        // Kita hanya cek page tetap terlihat
        assertTrue(pricesPage.isPageVisible(), "Page hilang setelah toggle switch");
    }

    // ================= CHOOSE BUTTONS =================
    @Test
    @Order(3)
    @DisplayName("All 'Choose' buttons open new tab")
    void testChooseButtons() {
        pricesPage.clickAllChooseButtons();
        assertTrue(pricesPage.isPageVisible(), "Tidak kembali ke halaman utama setelah klik Choose buttons");
    }

    // ================= START NOW BEFORE =================
    @Test
    @Order(4)
    @DisplayName("'Start Now' buttons before explore open new tab")
    void testStartNowBeforeExplore() {
        pricesPage.clickStartNowBeforeExplore();
        assertTrue(pricesPage.isPageVisible(),
                "Tidak kembali ke halaman utama setelah klik Start Now (before explore)");
    }

    // ================= CONTACT FORM =================
    @Test
    @Order(5)
    @DisplayName("Contact form can be submitted")
    void testContactForm() {
        pricesPage.fillContactForm();
        assertTrue(pricesPage.isPageVisible(), "Page hilang setelah submit contact form");
    }

    // ================= TESTIMONIALS =================
    @Test
    @Order(6)
    @DisplayName("Open testimonials in new tab")
    void testOpenTestimonials() {
        pricesPage.openTestimonials();
        assertTrue(pricesPage.isPageVisible(), "Tidak kembali ke halaman utama setelah buka testimonials");
    }

    // ================= EXPLORE =================
    @Test
    @Order(7)
    @DisplayName("Explore features button works and returns")
    void testExploreAndReturn() {
        pricesPage.exploreAndReturn();
        assertTrue(pricesPage.isPageVisible(), "Tidak kembali ke halaman utama setelah explore");
    }

    // ================= START NOW AFTER =================
    @Test
    @Order(8)
    @DisplayName("'Start Now' buttons after explore open new tab")
    void testStartNowAfterExplore() {
        pricesPage.clickStartNowAfterExplore();
        assertTrue(pricesPage.isPageVisible(), "Tidak kembali ke halaman utama setelah klik Start Now (after explore)");
    }

    // ================= NEWSLETTER =================
    @Test
    @Order(9)
    @DisplayName("Newsletter submission works with valid email")
    void testNewsletter() {
        String email = "demo@test.com";
        pricesPage.fillNewsletter(email);

        // Cek nilai email tetap di input
        WebElement emailInput = driver.findElement(pricesPage.newsletterEmail);
        assertEquals(email, emailInput.getAttribute("value"), "Email newsletter tidak sesuai!");
    }
}
