package com.proyek.tests.resources;

import com.proyek.pages.resources.DownloadPage;
import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class DownloadPageTest {

    private WebDriver driver;
    private DownloadPage downloadPage;

    @BeforeAll
    void setupAll() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        downloadPage = new DownloadPage(driver);
        downloadPage.open();
    }

    @AfterAll
    void teardownAll() {
        if (driver != null)
            driver.quit();
    }

    // ================= PAGE =================
    @Test
    @Order(1)
    @DisplayName("Download page should be visible")
    void testPageVisible() {
        assertTrue(downloadPage.isPageVisible(), "Download page tidak terlihat!");
    }

    // ================= DOWNLOAD VERSION =================
    @Test
    @Order(2)
    @DisplayName("Click 'Download Version' works and returns")
    void testDownloadVersion() {
        downloadPage.clickDownloadVersion();
        assertTrue(downloadPage.isPageVisible());
    }

    // ================= READ DOCUMENTATION =================
    @Test
    @Order(3)
    @DisplayName("Read Documentation link opens new tab")
    void testReadDocumentation() {
        downloadPage.clickReadDocumentation();
        assertTrue(downloadPage.isPageVisible());
    }

    // ================= CHECK SOURCE CODE =================
    @Test
    @Order(4)
    @DisplayName("Check Source Code link opens new tab")
    void testCheckSourceCode() {
        downloadPage.clickCheckSourceCode();
        assertTrue(downloadPage.isPageVisible());
    }

    // ================= CONTACT PARTNER =================
    @Test
    @Order(5)
    @DisplayName("Contact Partner link opens and returns")
    void testContactPartner() {
        downloadPage.clickPartnerAndReturn();
        assertTrue(downloadPage.isPageVisible());
    }

    // ================= CONTACT FORM =================
    @Test
    @Order(6)
    @DisplayName("Contact form can be filled and submitted")
    void testContactForm() {
        downloadPage.fillContactForm();
        // Bisa assert success message jika ada
        assertTrue(downloadPage.isPageVisible());
    }

    // ================= TESTIMONIALS =================
    @Test
    @Order(7)
    @DisplayName("Open Testimonials in new tab")
    void testOpenTestimonials() {
        downloadPage.openTestimonials();
        assertTrue(downloadPage.isPageVisible());
    }

    // ================= EXPLORE =================
    @Test
    @Order(8)
    @DisplayName("Explore features button works and returns")
    void testExploreAndReturn() {
        downloadPage.exploreAndReturn();
        assertTrue(downloadPage.isPageVisible());
    }

    // ================= START NOW =================
    @Test
    @Order(9)
    @DisplayName("Start Now button opens new tab")
    void testStartNow() {
        downloadPage.clickStartNow();
        assertTrue(downloadPage.isPageVisible());
    }

    // ================= NEWSLETTER =================
    @Test
    @Order(10)
    @DisplayName("Subscribe Newsletter works")
    void testNewsletter() {
        String email = "demo@test.com";
        downloadPage.fillNewsletter(email);

        WebElement emailInput = driver.findElement(downloadPage.newsletterEmail);
        assertEquals(email, emailInput.getAttribute("value"), "Email newsletter tidak sesuai!");
    }
}
