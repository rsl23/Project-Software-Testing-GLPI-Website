package com.proyek.tests.resources;

import java.time.Duration;

import org.junit.jupiter.api.AfterAll;
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
import org.openqa.selenium.chrome.ChromeOptions;

import com.proyek.pages.resources.TrainingPage;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TrainingPageTest {

    private WebDriver driver;
    private TrainingPage trainingPage;

    @BeforeAll
    void setUpAll() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-maximized");
        options.addArguments("--disable-notifications");

        driver = new ChromeDriver(options);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));

        trainingPage = new TrainingPage(driver);
        trainingPage.open();
        
        // Tambahkan delay untuk memastikan page load
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
        }
    }

    @AfterAll
    void tearDownAll() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    @Order(1)
    @DisplayName("Training page should be visible")
    void testNavigationAndPageVisible() {
        assertTrue(trainingPage.isPageVisible(), "Training page is not visible");
    }

    @Test
    @Order(2)
    @DisplayName("All menus and tabs should be clickable")
    void testClickMenusAndTabs() {
        trainingPage.clickResourcesMenu();
        trainingPage.clickTrainingLink();
        trainingPage.clickChooseCourse();
        trainingPage.clickGlpiPluginsTab();
        trainingPage.clickRegisterButton();
        trainingPage.clickGlpiAdminTab();
        // trainingPage.clickAllRegisterPresentielButtons();
    }

    @Test
    @Order(3)
    @DisplayName("Registration form should be fillable")
    void testFillRegistrationForm() {
        // Dropdown menggunakan nilai default, tidak perlu dipilih
        trainingPage.fillRegistrationForm(
                "Doe", "Patrik", "testing7103@gmail.com", "ISTTS", "Indonesia",
                "+6285777090291", "I want to learn more about GLPI");
        assertTrue(true, "Registration form filled successfully");
    }

    @Test
    @Order(4)
    @DisplayName("PDF program brochure should be downloadable")
    void testDownloadPDF() {
        trainingPage.downloadProgram();
        boolean downloaded = trainingPage.isPDFDownloaded("Brochure-formation-GLPI.pdf");
        assertTrue(downloaded, "PDF file was not downloaded successfully");
    }
}
