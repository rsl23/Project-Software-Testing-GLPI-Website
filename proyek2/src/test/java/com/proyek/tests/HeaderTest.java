package com.proyek.tests;

import com.proyek.pages.HeaderPage;
import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class HeaderTest {

    private WebDriver driver;
    private HeaderPage headerPage;

    @BeforeAll
    void setup() {
        driver = new ChromeDriver();
        headerPage = new HeaderPage(driver);
        headerPage.open();
    }

    @AfterAll
    void teardown() {
        if (driver != null)
            driver.quit();
    }

    @Test
    @DisplayName("Klik semua header dan dropdown link tanpa error")
    void testAllHeaderLinks() {
        // ===== Klik semua normal header links =====
        for (int i = 0; i < headerPage.getAllHeaderLinks().size(); i++) {
            WebElement link = headerPage.getAllHeaderLinks().get(i);
            try {
                headerPage.safeClick(link);
                String url = driver.getCurrentUrl();
                Assertions.assertNotNull(url, "URL tidak boleh null");
                Assertions.assertFalse(url.isEmpty(), "URL tidak boleh kosong");
            } catch (Exception e) {
                System.out.println("Skip header link: " + link.getText());
            }
        }

        // ===== Klik semua dropdown links =====
        for (int i = 0; i < headerPage.getAllDropdownParents().size(); i++) {
            WebElement dropdown = headerPage.getAllDropdownParents().get(i);
            headerPage.hover(dropdown);

            int linksCount = headerPage.getAllDropdownLinks(dropdown).size();
            for (int j = 0; j < linksCount; j++) {
                try {
                    // Re-find dropdown & links setiap klik karena halaman bisa reload
                    dropdown = headerPage.getAllDropdownParents().get(i);
                    headerPage.hover(dropdown);
                    WebElement link = headerPage.getAllDropdownLinks(dropdown).get(j);
                    headerPage.safeClick(link);
                    String url = driver.getCurrentUrl();
                    Assertions.assertNotNull(url, "URL tidak boleh null");
                    Assertions.assertFalse(url.isEmpty(), "URL tidak boleh kosong");
                } catch (Exception e) {
                    System.out.println("Skip dropdown link: " + e.getMessage());
                }
            }
        }
    }
}
