package com.proyek.tests.Tech;

import com.proyek.pages.Tech.FooterPage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.time.Duration;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class FooterPageTechTest {

    private WebDriver driver;
    private FooterPage footerPage;
    private final String faqUrl = "https://glpi-network.cloud/faq/";

    @BeforeEach
    void setup() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(30));
        driver.get(faqUrl);

        footerPage = new FooterPage(driver);
    }

    @Test
    @DisplayName("Cek visibility dan klik semua footer links")
    void testFooterLinks() {
        footerPage.clickAllFooterLinks();
    }

}
