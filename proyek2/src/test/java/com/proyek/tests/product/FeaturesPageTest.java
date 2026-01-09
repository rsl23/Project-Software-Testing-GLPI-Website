package com.proyek.tests.product;

import org.junit.jupiter.api.AfterAll;
import static org.junit.jupiter.api.Assertions.assertFalse;
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

import com.proyek.pages.product.FeaturesPage;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class FeaturesPageTest {

        private WebDriver driver;
        private FeaturesPage featuresPage;

        @BeforeAll
        void setupAll() {
                driver = new ChromeDriver();
                driver.manage().window().maximize();
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
        @Order(1)
        @DisplayName("Features page should load successfully")
        void testFeaturesPageLoaded() {
                assertTrue(
                                featuresPage.isPageTitleVisible(),
                                "Judul halaman Features tidak tampil, halaman gagal dimuat");
        }

        // ================= CTA BUTTONS =================

        @Test
        @Order(2)
        @DisplayName("Discover Now button should be visible")
        void testDiscoverNowButtonVisible() {
                assertTrue(
                                featuresPage.isDiscoverNowVisible(),
                                "Button Discover Now tidak terlihat di halaman Features");
        }

        @Test
        @Order(3)
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
        @Order(4)
        @DisplayName("Free Instance button should be visible")
        void testFreeInstanceButtonVisible() {
                assertTrue(
                                featuresPage.isFreeInstanceVisible(),
                                "Button Free Instance tidak terlihat di halaman Features");
        }

        @Test
        @Order(5)
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
        @Order(6)
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
