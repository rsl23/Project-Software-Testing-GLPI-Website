package com.proyek.tests.product;

import org.junit.jupiter.api.AfterAll;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
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

import com.proyek.pages.product.HelpdeskPage;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class HelpdeskPageTest {

    private WebDriver driver;
    private HelpdeskPage helpdesk;

    @BeforeAll
    void setupAll() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
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
    @Order(1)
    @DisplayName("Hero section should be visible")
    void testHeroVisible() {
        assertTrue(helpdesk.isHeroVisible(), "Hero section tidak terlihat!");
    }

    @Test
    @Order(2)
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
    // @Test
    // @Order(3)
    // @DisplayName("CTA buttons should open new tab")
    // void testCtaButtons() {
    //     String main = driver.getWindowHandle();

    //     helpdesk.clickCtaButton("viewTestimonials");
    //     assertEquals(main, driver.getWindowHandle());

    //     helpdesk.clickCtaButton("exploreNow");
    //     assertEquals(main, driver.getWindowHandle());
    // }

    // ================= TUTORIAL =================
    @Test
    @Order(3)
    @DisplayName("Tutorial iframe buttons should be clickable")
    void testTutorialIframe() {
        assertDoesNotThrow(() -> helpdesk.clickTutorialIframe(),
                "Tutorial iframe gagal diklik atau loop tombol error");
    }

    // ================= FEATURES =================
    // @Test
    // @DisplayName("Start Now Features button opens new tab")
    // void testStartNowFeatures() {
    //     String main = driver.getWindowHandle();
    //     helpdesk.clickStartNowFeatures();
    //     assertEquals(main, driver.getWindowHandle());
    // }

    // ================= NEWSLETTER =================
    // @Test
    // @DisplayName("Newsletter submission works with valid email")
    // void testNewsletterSubmission() {
    //     String email = "demo@test.com";
    //     helpdesk.fillNewsletter(email);

    //     WebElement emailInput = driver.findElement(By.cssSelector("input[type='email']"));
    //     assertEquals(email, emailInput.getAttribute("value"), "Email tidak sesuai!");

    //     // Bisa ditambahkan assertion untuk success message jika ada
    //     // By successMsg = By.cssSelector(".newsletter-success");
    //     // assertTrue(driver.findElement(successMsg).isDisplayed());
    // }

    // ================= SOCIAL =================
    // @Test
    // @DisplayName("All social media links are clickable")
    // void testSocialMediaLinks() {
    //     String main = driver.getWindowHandle();
    //     assertDoesNotThrow(() -> helpdesk.clickAllSocialMediaLinks(),
    //             "Salah satu link sosial media gagal diklik");
    //     assertEquals(main, driver.getWindowHandle());
    // }
}
