package com.proyek.tests.Tech;

import com.proyek.pages.Tech.MainHeaderPage;
import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class MainHeaderPageTest {

    private WebDriver driver;
    private MainHeaderPage header;

    @BeforeAll
    void setup() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("https://glpi-network.cloud/");
        header = new MainHeaderPage(driver);
    }

    @BeforeEach
    void goToHome() {
        driver.get("https://glpi-network.cloud/");
        header = new MainHeaderPage(driver); // reload page object
    }

    @AfterAll
    void teardown() {
        driver.quit();
    }

    // ---------------- Logo ----------------
    @Test
    @DisplayName("Logo muncul, href benar, klik redirect")
    void testLogo() {
        assertTrue(header.isLogoVisible(), "Logo harus terlihat");
        assertEquals("https://glpi-network.cloud/", header.getLogoHref(), "Logo harus link ke homepage");

        header.clickLogo();
        assertEquals("https://glpi-network.cloud/", driver.getCurrentUrl(), "Klik logo harus redirect ke homepage");
    }

    // ---------------- Menu Desktop ----------------
    @Test
    @DisplayName("Menu desktop tampil lengkap dan klik redirect")
    void testTopMenuItemsClick() {
        List<String> menuItems = List.of("Home", "Features", "Prices", "FAQ", "GLPI", "Info", "Contact us",
                "CUSTOMERS AREA");

        for (String item : menuItems) {
            header.clickTopMenuItem(item);

            // validasi redirect URL sederhana
            String currentUrl = driver.getCurrentUrl();
            assertTrue(currentUrl.contains("glpi-network.cloud") || currentUrl.contains("glpi-project.org"),
                    "Klik menu '" + item + "' harus redirect ke URL yang valid");
        }
    }

    // ---------------- Dropdown Sample ----------------
    @Test
    @DisplayName("Dropdown Info → Status klik dan redirect")
    void testDropdownStatus() {
        header.hoverInfoMenu();
        header.clickDropdownStatus();

        String currentUrl = driver.getCurrentUrl();
        assertTrue(currentUrl.contains("status.glpi-network.cloud"),
                "Klik dropdown Status harus ke URL status.glpi-network.cloud");
    }

    // ---------------- WPML ----------------
    // @Test
    // @DisplayName("WPML flag French klik dan redirect")
    // void testWpmlFlagFrenchClick() {
    // String alt = "French";

    // // klik flag French
    // header.clickWpmlFlag(alt);

    // // ambil URL saat ini
    // String currentUrl = driver.getCurrentUrl();

    // // validasi URL mengandung /fr/ atau fallback ke contact-us
    // assertTrue(currentUrl.contains("/fr/") || currentUrl.contains("contact-us"),
    // "Klik flag " + alt + " harus redirect ke URL bahasa French");

    // // kembali ke homepage supaya environment bersih untuk test lain
    // driver.get("https://glpi-network.cloud/");
    // }

}
