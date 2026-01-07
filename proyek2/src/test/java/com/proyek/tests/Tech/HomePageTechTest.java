package com.proyek.tests.Tech;

import com.proyek.pages.Tech.HomePageTech;
import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class HomePageTechTest {

    private WebDriver driver;
    private HomePageTech home;

    // ================= SETUP =================
    @BeforeAll
    void setup() {
        driver = new ChromeDriver();
        home = new HomePageTech(driver);
        home.open();
    }

    @AfterEach
    void resetPage() {
        home.open();
    }

    @AfterAll
    void teardown() {
        if (driver != null) {
            driver.quit();
        }
    }

    // ================= HERO =================
    @Test
    @DisplayName("Hero title should be visible")
    void testHeroTitleVisible() {
        assertTrue(home.isHeroVisible(), "Hero title tidak tampil");
    }

    @Test
    @DisplayName("Hero subtitle should be visible")
    void testHeroSubtitleVisible() {
        assertTrue(home.isSubtitleVisible(), "Hero subtitle tidak tampil");
    }

    // ================= CTA =================
    @Test
    @DisplayName("FREE DEMO navigates to Register page")
    void testFreeDemoNavigation() {
        home.clickFreeDemo();

        String url = home.getLastNavigatedUrl();
        assertNotNull(url, "FREE DEMO tidak membuka halaman apapun");
        assertTrue(
                url.contains("register"),
                "FREE DEMO harus ke halaman register, tapi ke: " + url);
    }

    @Test
    @DisplayName("START TRIAL navigates to Register page")
    void testStartTrialNavigation() {
        home.clickStartTrial();

        String url = home.getLastNavigatedUrl();
        assertNotNull(url, "START TRIAL tidak membuka halaman apapun");
        assertTrue(
                url.contains("register"),
                "START TRIAL harus ke halaman register, tapi ke: " + url);
    }

    // ================= PRICING =================
    @Test
    @DisplayName("Pricing tables should be displayed")
    void testPricingTablesExist() {
        assertEquals(
                3,
                home.getPricingTableCount(),
                "Jumlah pricing table tidak sesuai");
    }

    @Test
    @DisplayName("Pricing CTA navigates to Billing or Register page")
    void testPricingButtonsNavigation() {
        home.clickAllPricingButtons();

        String url = home.getLastNavigatedUrl();
        assertNotNull(url, "Pricing CTA tidak membuka halaman apapun");
        assertTrue(
                url.contains("contact")
                        || url.contains("billing")
                        || url.contains("register"),
                "Pricing CTA membuka halaman yang tidak valid: " + url);

    }

    // ================= KNOWLEDGE BASE =================
    @Test
    @DisplayName("Knowledge Base navigates to FAQ / Knowledge page")
    void testKnowledgeBaseNavigation() {
        home.clickKnowledgeBase();

        String url = home.getLastNavigatedUrl();
        assertNotNull(url, "Knowledge Base tidak membuka halaman apapun");
        assertTrue(
                url.contains("help.glpi-project.org"),
                "Knowledge Base harus ke Help Center, tapi ke: " + url);

    }

    // ================= REVIEWS =================
    @Test
    @DisplayName("Review badges should exist")
    void testReviewBadgesExist() {
        assertTrue(
                home.getReviewBadgeCount() > 0,
                "Badge review tidak ditemukan");
    }

    // ================= FORM =================
    @Test
    @DisplayName("Embedded form iframe should be visible")
    void testIframeFormVisible() {
        assertTrue(
                home.isIframeFormVisible(),
                "Iframe form tidak tampil");
    }
}
