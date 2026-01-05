package com.proyek.tests;

import com.proyek.pages.RndPage;
import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class RndPageTest {

    private WebDriver driver;
    private RndPage rndPage;

    @BeforeAll
    void setupAll() {
        driver = new ChromeDriver();
        rndPage = new RndPage(driver);
        rndPage.open();
    }

    @AfterAll
    void teardownAll() {
        if (driver != null) {
            driver.quit();
        }
    }

    // ================= HEADLINE =================
    @Test
    @DisplayName("R&D headline should be visible")
    void testRndHeadlineVisible() {
        assertTrue(rndPage.isRndHeadlineVisible(), "R&D headline tidak terlihat!");
    }

    // ================= SAFE4SOC =================
    @Test
    @DisplayName("SAFE4SOC link should open correct URL and return to main tab")
    void testSafe4socLink() {
        String main = driver.getWindowHandle();
        rndPage.clickSafe4socLink();

        // tunggu tab baru muncul
        String newHandle = driver.getWindowHandles().stream()
                .filter(handle -> !handle.equals(main))
                .findFirst().orElseThrow(() -> new RuntimeException("Tab baru tidak muncul"));

        // pindah ke tab baru
        driver.switchTo().window(newHandle);

        // cek URL
        assertEquals("https://safe4soc.eu/", driver.getCurrentUrl(), "SAFE4SOC link tidak membuka URL yang benar");

        // tutup tab baru dan balik ke main
        driver.close();
        driver.switchTo().window(main);
    }

    @Test
    @DisplayName("IEN link should open correct URL and return to main tab")
    void testIenLink() {
        String main = driver.getWindowHandle();
        rndPage.clickIenLink();

        String newHandle = driver.getWindowHandles().stream()
                .filter(handle -> !handle.equals(main))
                .findFirst().orElseThrow(() -> new RuntimeException("Tab baru tidak muncul"));

        driver.switchTo().window(newHandle);
        assertEquals("https://www.irt-systemx.fr/projet/projet-ien/", driver.getCurrentUrl(),
                "IEN link tidak membuka URL yang benar");

        driver.close();
        driver.switchTo().window(main);
    }

    // ================= IEN =================
    @Test
    @DisplayName("IEN project should be visible")
    void testIenVisible() {
        assertTrue(rndPage.isIenVisible(), "IEN project tidak terlihat!");
    }

}
