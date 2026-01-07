package com.proyek.tests.Tech;

import com.proyek.pages.Tech.PricesPageTech;
import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class PricesPageTechTest {

    private WebDriver driver;
    private PricesPageTech prices;

    @BeforeAll
    void setup() {
        driver = new ChromeDriver();
        prices = new PricesPageTech(driver);
        prices.open();
    }

    @AfterEach
    void resetPage() {
        prices.open();
    }

    @AfterAll
    void teardown() {
        driver.quit();
    }

    @Test
    void testPublicCloudStartInstance() {
        prices.clickPublicCloudStart();
        assertTrue(
                prices.getLastNavigatedUrl().contains("register"),
                "Start instance harus ke register");
    }

    @Test
    void testPrivateCloudContactSales() {
        prices.clickPrivateCloudContact();
        assertTrue(
                prices.getLastNavigatedUrl().contains("contact"),
                "Private Cloud harus ke Contact Sales");
    }

    @Test
    void testCustomPricesContactUs() {
        prices.clickCustomPricesContact();
        assertTrue(
                prices.getLastNavigatedUrl().contains("contact-us"),
                "Custom Prices harus ke Contact Us");
    }

    @Test
    void testPublicCloudCalculatorUpdatesTotal() {
        prices.enablePublicCloud(5);
        assertNotEquals(
                "€ 0.00",
                prices.getPublicCloudTotal(),
                "Total Public Cloud harus berubah");
    }

    @Test
    @DisplayName("Detailed description navigates to Private Cloud page")
    void testDetailedDescription() {
        prices.clickDetailedDescription();
        assertTrue(
                prices.getLastNavigatedUrl().contains("private-cloud"),
                "Detailed description harus ke halaman Private Cloud");
    }

    @Test
    @DisplayName("Contact Sales navigates to GLPI Project")
    void testContactSales() {
        prices.clickContactSales();
        assertTrue(
                prices.getLastNavigatedUrl().contains("glpi-project.org"),
                "CONTACT SALES harus ke GLPI Project");
    }

    @Test
    @DisplayName("Read More opens Teclib FAQ in new tab")
    void testReadMoreKnowledgeBase() {
        prices.clickReadMoreKnowledgeBase();
        assertTrue(
                prices.getLastNavigatedUrl().contains("help"),
                "READ MORE harus ke FAQ Teclib");
    }

}
