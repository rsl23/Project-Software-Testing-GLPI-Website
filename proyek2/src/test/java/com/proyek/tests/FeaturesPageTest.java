package com.proyek.tests;

import com.proyek.pages.FeaturesPage;
import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class FeaturesPageTest {

    private WebDriver driver;
    private FeaturesPage featuresPage;

    @BeforeAll
    void setupAll() {
        driver = new ChromeDriver();
        featuresPage = new FeaturesPage(driver);
        featuresPage.open();
    }

    @AfterAll
    void teardownAll() {
        if (driver != null) {
            driver.quit();
        }
    }

    // ================= BASIC PAGE LOAD =================

    @Test
    @DisplayName("Features page should load successfully")
    void testFeaturesPageLoaded() {
        assertTrue(
                featuresPage.isPageTitleVisible(),
                "Judul halaman Features tidak tampil, halaman gagal dimuat");
    }

    // ================= CTA BUTTONS =================

    @Test
    @DisplayName("Discover Now button should be visible")
    void testDiscoverNowButtonVisible() {
        assertTrue(
                featuresPage.isDiscoverNowVisible(),
                "Button Discover Now tidak terlihat di halaman Features");
    }

    @Test
    @DisplayName("Discover Now button should open correct page")
    void testDiscoverNowRedirect() {
        String openedUrl = featuresPage.clickDiscoverNowAndGetUrl();

        assertFalse(
                openedUrl.isEmpty(),
                "Tidak ada halaman yang terbuka setelah klik Discover Now");

        assertTrue(
                openedUrl.contains("features")
                        || openedUrl.contains("discover")
                        || openedUrl.contains("glpi"),
                "Halaman yang dibuka tidak sesuai, URL: " + openedUrl);
    }

    @Test
    @DisplayName("Free Instance button should be visible")
    void testFreeInstanceButtonVisible() {
        assertTrue(
                featuresPage.isFreeInstanceVisible(),
                "Button Free Instance tidak terlihat di halaman Features");
    }

    @Test
    @DisplayName("Free Instance button should open correct page")
    void testFreeInstanceRedirect() {
        String openedUrl = featuresPage.clickFreeInstanceAndGetUrl();

        assertFalse(
                openedUrl.isEmpty(),
                "Tidak ada halaman yang terbuka setelah klik Free Instance");

        assertTrue(
                openedUrl.contains("demo")
                        || openedUrl.contains("free")
                        || openedUrl.contains("cloud"),
                "Halaman yang dibuka tidak sesuai, URL: " + openedUrl);
    }

    // ================= ACCORDION =================

    @Test
    @DisplayName("Accordion Inventory should expand and show content")
    void testAccordionInventoryCanBeOpened() {
        String accordionTitle = "Inventory";
        String accordionBodyId = "body-pro-accordion-24-432322";

        assertTrue(
                featuresPage.isAccordionVisible(accordionTitle),
                "Accordion Inventory tidak terlihat di halaman Features");

        featuresPage.openAccordion(accordionTitle);

        assertTrue(
                featuresPage.isAccordionBodyVisible(accordionBodyId),
                "Isi accordion Inventory tidak muncul setelah accordion dibuka");
    }
}
