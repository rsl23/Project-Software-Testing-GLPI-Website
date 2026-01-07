package com.proyek.tests.Tech;

import com.proyek.pages.Tech.FaqPageTech;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertTrue;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class FaqPageTechTest {

    private WebDriver driver;
    private FaqPageTech faqPage;

    @BeforeAll
    void setup() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("https://glpi-network.cloud/faq/");
        faqPage = new FaqPageTech(driver);
        faqPage.waitForPageLoad();
    }

    @AfterAll
    void teardown() {
        driver.quit();
    }

    @Test
    @DisplayName("Klik tombol ACCESS KNOWLEDGE BASE")
    void testAccessKnowledgeBase() {
        WebElement button = faqPage.getButtonByText("ACCESS KNOWLEDGE BASE");
        String href = button.getAttribute("href"); // ambil link
        assertTrue(href.contains("faq.teclib.com"), "Redirect harus ke faq.teclib.com");

        faqPage.clickButton("ACCESS KNOWLEDGE BASE");

        // kembali ke FAQ untuk next test
        driver.get("https://glpi-network.cloud/faq/");
        faqPage.waitForPageLoad();
    }

    @Test
    @DisplayName("Klik tombol START TRIAL")
    void testStartTrial() {
        // Refetch tombol dan ambil href
        WebElement button = new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(d -> d.findElement(By.xpath(
                        "//a[contains(@class,'et_pb_button') and contains(text(),'START TRIAL')]")));

        String href = button.getAttribute("href");
        assertTrue(href.contains("myaccount.glpi-network.cloud/register.php"),
                "Redirect harus ke halaman Start Trial");

        // Klik tombol (refetch & tunggu clickable)
        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.elementToBeClickable(button))
                .click();

        // Tunggu redirect / page load
        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(d -> d.getCurrentUrl().contains("myaccount.glpi-network.cloud/register.php"));

        // Kembali ke FAQ untuk next test
        driver.get("https://glpi-network.cloud/faq/");
        faqPage.waitForPageLoad();
    }

}
