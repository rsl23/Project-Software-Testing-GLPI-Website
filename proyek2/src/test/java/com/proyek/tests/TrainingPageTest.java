package com.proyek.tests;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.Duration;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.WebDriver;

import com.proyek.pages.resources.TrainingPage;

public class TrainingPageTest {

    private static WebDriver driver;
    private static TrainingPage trainingPage;

    @BeforeAll
    public static void setUpAll() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-maximized");
        options.addArguments("--disable-notifications");

        driver = new ChromeDriver(options);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));

        trainingPage = new TrainingPage(driver);
        trainingPage.open();
    }

    @AfterAll
    public static void tearDownAll() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    public void testNavigationAndPageVisible() {
        assertTrue(trainingPage.isPageVisible(), "Training page is not visible");
    }

    @Test
    public void testClickMenusAndTabs() {
        trainingPage.clickResourcesMenu();
        trainingPage.clickTrainingLink();
        trainingPage.clickChooseCourse();
        trainingPage.clickGlpiPluginsTab();
        trainingPage.clickRegisterButton();
        trainingPage.clickGlpiAdminTab();
        trainingPage.clickAllRegisterPresentielButtons();
    }

    @Test
    public void testFillRegistrationForm() {
        boolean dropdownSelected = trainingPage.fillRegistrationForm(
                "Doe", "Patrik", "testing7103@gmail.com", "ISTTS", "Indonesia",
                "+6285777090291", "I want to learn more about GLPI", "28/11/2025");
        assertTrue(dropdownSelected, "Dropdown 'formation_en_ligne' value not selectable");
    }

    @Test
    public void testDownloadPDF() {
        trainingPage.downloadProgram();
        boolean downloaded = trainingPage.isPDFDownloaded("Brochure-formation-GLPI.pdf");
        assertTrue(downloaded, "PDF file was not downloaded successfully");
    }
}



