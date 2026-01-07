package com.proyek.tests.Tech;

import com.proyek.pages.Tech.FeaturesPageTech;
import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class FeaturesPageTechTest {

    private WebDriver driver;
    private FeaturesPageTech features;

    @BeforeAll
    void setup() {
        driver = new ChromeDriver();
        features = new FeaturesPageTech(driver);
        features.open();
    }

    @AfterEach
    void reset() {
        features.open();
    }

    @AfterAll
    void teardown() {
        driver.quit();
    }

    // ================= CONTENT =================
    @Test
    @DisplayName("Features page title should be visible")
    void testPageTitleVisible() {
        assertTrue(
                features.isPageTitleVisible(),
                "Judul halaman Features tidak tampil");
    }

    @Test
    @DisplayName("Meet GLPI Network Cloud section should be visible")
    void testMeetCloudSectionVisible() {
        assertTrue(
                features.isMeetCloudSectionVisible(),
                "Section Meet GLPI Network Cloud tidak tampil");
    }

    @Test
    @DisplayName("Comparison table should be visible")
    void testComparisonTableVisible() {
        assertTrue(
                features.isComparisonTableVisible(),
                "Table perbandingan tidak tampil");
    }

    // ================= CTA =================
    @Test
    @DisplayName("Download Features Guide opens PDF")
    void testDownloadGuide() {
        features.clickDownloadGuide();

        String url = features.getLastNavigatedUrl();
        assertNotNull(url, "Download guide tidak membuka apapun");
        assertTrue(
                url.endsWith(".pdf"),
                "Download guide harus PDF, tapi ke: " + url);
    }

    @Test
    @DisplayName("Start Trial opens register page")
    void testStartTrial() {
        features.clickStartTrial();

        String url = features.getLastNavigatedUrl();
        assertNotNull(url, "Start Trial tidak membuka apapun");
        assertTrue(
                url.contains("register"),
                "Start Trial harus ke register, tapi ke: " + url);
    }
}
