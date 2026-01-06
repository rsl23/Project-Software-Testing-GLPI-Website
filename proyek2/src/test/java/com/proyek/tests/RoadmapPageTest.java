package com.proyek.tests;

import com.proyek.pages.product.RoadmapPage;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class RoadmapPageTest {

    private WebDriver driver;
    private RoadmapPage roadmap;

    @BeforeAll
    void setupAll() {
        driver = new ChromeDriver();
        roadmap = new RoadmapPage(driver);
        roadmap.open();
    }

    @AfterAll
    void teardownAll() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    @DisplayName("Roadmap page should load successfully")
    void testRoadmapPageLoaded() {
        assertEquals(
                "https://www.glpi-project.org/en/roadmap/",
                driver.getCurrentUrl(),
                "Halaman roadmap tidak terbuka dengan benar");
    }

    @Test
    @DisplayName("Contribute button should redirect to contact page")
    void testContributeButton() {
        roadmap.clickContributeButton();

        assertTrue(
                driver.getCurrentUrl().contains("/contact"),
                "Klik Contribute tidak mengarahkan ke halaman Contact");

        // reset state
        roadmap.open();
    }

    @Test
    @DisplayName("Productivity filter checkbox should be selectable")
    void testProductivityCheckboxCanBeSelected() {
        String filterId = "Productivité";

        roadmap.clickCheckbox(filterId);

        WebElement checkbox = driver.findElement(By.id(filterId));
        assertTrue(
                checkbox.isSelected(),
                "Checkbox Productivity seharusnya terpilih setelah diklik");
    }

    @Test
    @DisplayName("Productivity filter should display roadmap cards")
    void testFilteredCardsAppear() {
        String filterId = "Productivité";

        roadmap.clickCheckbox(filterId);

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(d -> d.findElements(By.cssSelector(".repeater-item")).size() > 0);

        List<WebElement> cards = driver.findElements(By.cssSelector(".repeater-item"));

        assertFalse(
                cards.isEmpty(),
                "Tidak ada roadmap card yang muncul setelah filter Productivity dipilih");
    }
}



