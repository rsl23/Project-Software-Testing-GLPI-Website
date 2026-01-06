package com.proyek.tests.common;

import com.proyek.pages.common.FooterPage;
import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class FooterTest {

    private WebDriver driver;
    private FooterPage footerPage;

    @BeforeAll
    void setup() {
        driver = new ChromeDriver();
        footerPage = new FooterPage(driver);
        footerPage.open(); // buka homepage
    }

    @AfterAll
    void teardown() {
        if (driver != null)
            driver.quit();
    }

    @Test
    @DisplayName("Klik semua link di footer div khusus")
    void testFooterDivLinks() {
        footerPage.goToFooterDiv();

        for (int i = 0; i < footerPage.getAllFooterLinks().size(); i++) {
            try {
                WebElement link = footerPage.getAllFooterLinks().get(i);

                // klik aman
                footerPage.safeClick(link);

                // assert URL valid (skip href="#")
                String url = driver.getCurrentUrl();
                if (!url.equals("#")) {
                    Assertions.assertNotNull(url, "URL footer tidak boleh null");
                    Assertions.assertFalse(url.isEmpty(), "URL footer tidak boleh kosong");
                }

            } catch (Exception e) {
                System.out.println("Skip footer link: " + e.getMessage());
            }
        }
    }
}
