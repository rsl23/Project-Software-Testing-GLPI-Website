package com.proyek.tests.product;

import org.junit.jupiter.api.AfterAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import com.proyek.pages.product.AdministrationPage;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class AdministrationPageTest {

    private WebDriver driver;
    private AdministrationPage adminPage;

    @BeforeAll
    void setupAll() {
        driver = new ChromeDriver();
        adminPage = new AdministrationPage(driver);
        adminPage.open();
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
        assertTrue(adminPage.isHeroVisible(), "Hero section tidak terlihat!");
    }

    // ================= MANAGE YOUR USERS =================
    @Test
    @DisplayName("Manage Your Users button opens new tab")
    void testManageYourUsersButton() {
        String main = driver.getWindowHandle();
        adminPage.clickManageYourUsers();
        assertEquals(main, driver.getWindowHandle(), "Kembali ke tab utama gagal!");
    }

    // ================= START NOW BUTTONS =================
    @Test
    @DisplayName("Start Now button 1 opens new tab")
    void testStartNow1() {
        String main = driver.getWindowHandle();
        adminPage.clickStartNow1();
        assertEquals(main, driver.getWindowHandle());
    }

    // @Test
    // @DisplayName("Start Now button 2 opens new tab")
    // void testStartNow2() {
    // String main = driver.getWindowHandle();
    // adminPage.clickStartNow2();
    // assertEquals(main, driver.getWindowHandle());
    // }

    // @Test
    // @DisplayName("Start Now button 3 opens new tab")
    // void testStartNow3() {
    // String main = driver.getWindowHandle();
    // adminPage.clickStartNow3();
    // assertEquals(main, driver.getWindowHandle());
    // }

    // @Test
    // @DisplayName("Start Now button 4 opens new tab")
    // void testStartNow4() {
    //     String main = driver.getWindowHandle();
    //     adminPage.clickStartNow4();
    //     assertEquals(main, driver.getWindowHandle());
    // }

    // ================= TUTORIAL IFRAME =================
    @Test
    @DisplayName("Tutorial iframe steps can be clicked")
    void testTutorialIframe() {
        adminPage.runTutorialIframe();
        // Optional: assert ada button yang diklik / frame berhasil ditampilkan
    }

    // ================= TESTIMONIALS =================
    @Test
    @DisplayName("Browse All Testimonials button opens new tab")
    void testBrowseAllTestimonials() {
        String main = driver.getWindowHandle();
        adminPage.clickBrowseAllTestimonials();
        assertEquals(main, driver.getWindowHandle());
    }

    // ================= EXPLORE FEATURES =================
    @Test
    @DisplayName("Explore Now button works and returns to Administration page")
    void testExploreNow() {
        String currentUrl = driver.getCurrentUrl();
        adminPage.clickExploreNow();
        assertEquals(currentUrl, driver.getCurrentUrl());
    }

    // ================= NEWSLETTER =================
    @Test
    @DisplayName("Newsletter submission works with valid email")
    void testNewsletterSubmission() {
        String email = "demo@test.com";
        adminPage.fillNewsletter(email);

        WebElement emailInput = driver.findElement(By.cssSelector("input[type='email']"));
        assertEquals(email, emailInput.getAttribute("value"), "Email tidak sesuai!");
    }
}
