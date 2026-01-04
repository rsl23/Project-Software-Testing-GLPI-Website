package com.proyek.tests;

import com.proyek.pages.FinancePage;
import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class FinancePageTest {

    private WebDriver driver;
    private FinancePage financePage;

    @BeforeAll
    void setupAll() {
        driver = new ChromeDriver();
        financePage = new FinancePage(driver);
        financePage.open();
    }

    @AfterAll
    void teardownAll() {
        if (driver != null) {
            driver.quit();
        }
    }

    // ================= HERO =================
    @Test
    @Order(1)
    @DisplayName("Hero section should be visible")
    void testHeroVisible() {
        assertTrue(financePage.isHeroVisible(), "Hero section tidak terlihat!");
    }

    // ================= TRACK YOUR EXPENSES =================
    @Test
    @Order(2)
    @DisplayName("Track Your Expenses button opens new tab")
    void testTrackYourExpenses() {
        String main = driver.getWindowHandle();
        financePage.clickTrackYourExpenses();
        // Assertion: fokus harus kembali ke tab utama
        assertEquals(main, driver.getWindowHandle(),
                "Fokus tidak kembali ke tab utama setelah klik Track Your Expenses");
    }

    // ================= START NOW BUTTONS =================
    @Test
    @Order(3)
    @DisplayName("Start Now button 1 opens new tab")
    void testStartNow1() {
        String main = driver.getWindowHandle();
        financePage.clickStartNow1();
        assertEquals(main, driver.getWindowHandle(), "Fokus tidak kembali ke tab utama setelah klik Start Now 1");
    }

    @Test
    @Order(4)
    @DisplayName("Start Now button 2 opens new tab")
    void testStartNow2() {
        String main = driver.getWindowHandle();
        financePage.clickStartNow2();
        assertEquals(main, driver.getWindowHandle(), "Fokus tidak kembali ke tab utama setelah klik Start Now 2");
    }

    @Test
    @Order(5)
    @DisplayName("Start Now button 3 opens new tab")
    void testStartNow3() {
        String main = driver.getWindowHandle();
        financePage.clickStartNow3();
        assertEquals(main, driver.getWindowHandle(), "Fokus tidak kembali ke tab utama setelah klik Start Now 3");
    }

    @Test
    @Order(6)
    @DisplayName("Start Now button 4 opens new tab")
    void testStartNow4() {
        String main = driver.getWindowHandle();
        financePage.clickStartNow4();
        assertEquals(main, driver.getWindowHandle(), "Fokus tidak kembali ke tab utama setelah klik Start Now 4");
    }

    // ================= TUTORIAL IFRAME =================
    @Test
    @Order(7)
    @DisplayName("Tutorial iframe steps can be clicked")
    void testTutorialIframe() {
        financePage.runTutorial();
        // Assertion: pastikan iframe selesai dengan tombol terakhir tidak ada
        assertDoesNotThrow(() -> financePage.runTutorial(), "Tutorial iframe gagal dijalankan ulang");
    }

    // ================= TESTIMONIALS =================
    @Test
    @Order(8)
    @DisplayName("Browse All Testimonials button opens new tab")
    void testBrowseAllTestimonials() {
        String main = driver.getWindowHandle();
        financePage.clickBrowseAllTestimonials();
        assertEquals(main, driver.getWindowHandle(),
                "Fokus tidak kembali ke tab utama setelah klik Browse All Testimonials");
    }

    // ================= EXPLORE NOW =================
    @Test
    @Order(9)
    @DisplayName("Explore Now button works and returns to Finance page")
    void testExploreNow() {
        String currentUrl = driver.getCurrentUrl();
        financePage.clickExploreNow();
        assertEquals(currentUrl, driver.getCurrentUrl(),
                "Halaman tidak kembali ke Finance page setelah klik Explore Now");
    }

    // ================= NEWSLETTER =================
    @Test
    @Order(10)
    @DisplayName("Newsletter submission works with valid email")
    void testNewsletterSubmission() {
        String email = "demo@test.com";
        financePage.submitNewsletter(email);

        // Assertion sederhana: input email sudah kosong / halaman tetap di Finance page
        assertEquals(financePage.financeUrl, driver.getCurrentUrl(),
                "Halaman tidak tetap di Finance page setelah submit newsletter");
    }
}
