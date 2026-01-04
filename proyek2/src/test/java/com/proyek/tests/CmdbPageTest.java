package com.proyek.tests;

import com.proyek.pages.CmdbPage;
import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class CmdbPageTest {

    private WebDriver driver;
    private CmdbPage cmdb;

    @BeforeAll
    void setupAll() {
        driver = new ChromeDriver();
        cmdb = new CmdbPage(driver);
        cmdb.open();
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
        assertTrue(cmdb.isHeroVisible(), "Hero section tidak terlihat!");
    }

    // ================= BUTTONS =================
    @Test
    @DisplayName("Representative buttons are clickable")
    void testButtons() {
        String main = driver.getWindowHandle();

        cmdb.clickButtonByVisibleText("Start managing your first asset");
        assertEquals(main, driver.getWindowHandle());

        cmdb.clickButtonByVisibleText("Start Now");
        assertEquals(main, driver.getWindowHandle());

        cmdb.clickRepresentativeAccessInstance();
        assertEquals(main, driver.getWindowHandle());

        assertDoesNotThrow(() -> cmdb.clickTutorialIframe(), "Tutorial iframe gagal dijalankan");

        cmdb.clickButtonByVisibleText("View All Testimonials");
        assertEquals(main, driver.getWindowHandle());

        cmdb.clickButtonByVisibleText("Explore Now");
        assertEquals(main, driver.getWindowHandle());

        cmdb.clickLastButtonByText("Start Now");
        assertEquals(main, driver.getWindowHandle());
    }

    // ================= NEWSLETTER =================
    @Test
    @DisplayName("Newsletter submission works with valid email")
    void testNewsletterSubmission() {
        String email = "demo@test.com";
        cmdb.fillNewsletter(email);

        WebElement emailInput = driver.findElement(By.cssSelector("input[type='email']"));
        assertEquals(email, emailInput.getAttribute("value"), "Email tidak sesuai!");
    }

    // ================= SOCIAL LINKS =================
    @Test
    @DisplayName("All social media links are clickable")
    void testSocialMediaLinks() {
        String main = driver.getWindowHandle();
        assertDoesNotThrow(() -> cmdb.clickAllSocialMediaLinks(),
                "Salah satu link sosial media gagal diklik");
        assertEquals(main, driver.getWindowHandle());
    }
}
