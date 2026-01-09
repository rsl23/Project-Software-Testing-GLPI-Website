package com.proyek.tests.product;

import org.junit.jupiter.api.AfterAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
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

import com.proyek.pages.product.FinancePage;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class FinancePageTest {

    private WebDriver driver;
    private FinancePage financePage;

    @BeforeAll
    void setupAll() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
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
    // @Test
    // @Order(3)
    // @DisplayName("Start Now button 1 opens new tab")
    // void testStartNow1() {
    //     String main = driver.getWindowHandle();
    //     financePage.clickStartNow1();
    //     assertEquals(main, driver.getWindowHandle(), "Fokus tidak kembali ke tab utama setelah klik Start Now 1");
    // }


    // ================= TUTORIAL IFRAME =================
    // @Test
    // @Order(4)
    // @DisplayName("Tutorial iframe steps can be clicked")
    // void testTutorialIframe() {
    //     financePage.runTutorial();
    //     // Assertion: pastikan iframe selesai dengan tombol terakhir tidak ada
    //     assertDoesNotThrow(() -> financePage.runTutorial(), "Tutorial iframe gagal dijalankan ulang");
    // }

    // ================= TESTIMONIALS =================
    // @Test
    // @Order(5)
    // @DisplayName("Browse All Testimonials button opens new tab")
    // void testBrowseAllTestimonials() {
    //     String main = driver.getWindowHandle();
    //     financePage.clickBrowseAllTestimonials();
    //     assertEquals(main, driver.getWindowHandle(),
    //             "Fokus tidak kembali ke tab utama setelah klik Browse All Testimonials");
    // }

    // ================= EXPLORE NOW =================
    // @Test
    // @Order(5)
    // @DisplayName("Explore Now button works and returns to Finance page")
    // void testExploreNow() {
    //     String currentUrl = driver.getCurrentUrl();
    //     financePage.clickExploreNow();
    //     assertEquals(currentUrl, driver.getCurrentUrl(),
    //             "Halaman tidak kembali ke Finance page setelah klik Explore Now");
    // }

    // ================= NEWSLETTER =================
    // @Test
    // @Order(5)
    // @DisplayName("Newsletter submission works with valid email")
    // void testNewsletterSubmission() {
    //     String email = "demo@test.com";
    //     financePage.submitNewsletter(email);

    //     // Assertion sederhana: input email sudah kosong / halaman tetap di Finance page
    //     assertEquals(financePage.financeUrl, driver.getCurrentUrl(),
    //             "Halaman tidak tetap di Finance page setelah submit newsletter");
    // }
}
