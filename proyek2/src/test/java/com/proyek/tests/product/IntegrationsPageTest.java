package com.proyek.tests.product;

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

import com.proyek.pages.product.IntegrationsPage;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class IntegrationsPageTest {

    private WebDriver driver;
    private IntegrationsPage page;

    @BeforeAll
    void setup() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        page = new IntegrationsPage(driver);
        page.open();
    }

    @AfterAll
    void teardown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    @Order(1)
    @DisplayName("Integrations page should load")
    void testPageLoaded() {
        assertTrue(page.isPageTitleVisible(), "Judul halaman Integrations tidak tampil");
    }

    @Test
    @Order(2)
    @DisplayName("Integration cards should be visible")
    void testCardsVisible() {
        assertTrue(page.areIntegrationCardsVisible(), "Card integrations tidak tampil");
    }

    @Test
    @Order(3)
    @DisplayName("Search API should show cards containing keyword API")
    void testSearchIntegration() {
        page.searchIntegration("API");

        assertTrue(page.getVisibleCardCount() > 0, "Tidak ada card setelah search API");
        assertTrue(page.atLeastOneCardContainsApiKeyword(), "Tidak ada card yang mengandung kata API setelah search");
    }

    @Test
    @Order(4)
    @DisplayName("Click first integration card opens valid integration page")
    void testIntegrationCardOpensValidPage() {
        assertTrue(page.clickFirstIntegrationAndVerifyPage(), "Halaman integration yang dibuka tidak valid");
    }

    @Test
    @Order(5)
    @DisplayName("Filter API should show cards containing keyword API and reset with All")
    void testApiFilter() {

        // 1️⃣ Reset dulu klik All
        page.clickFilterAndWait(page.apiFilterButton);

        // 2️⃣ Klik filter API

        // 3️⃣ Cek card muncul dan minimal satu ada 'API'
        assertTrue(page.getVisibleCardCount() > 0, "Tidak ada card setelah filter API");
        assertTrue(page.atLeastOneCardContainsApiKeyword(),
                "Tidak ada card yang mengandung kata API setelah filter API");

        // 4️⃣ Reset klik All lagi
        page.clickFilterAndWait(page.allFilterButton);

        assertTrue(page.getVisibleCardCount() > 0, "Card tidak kembali setelah klik All");
    }

    @Test
    @Order(6)
    @DisplayName("Load More Articles button should load more cards")
    void testLoadMoreArticles() {
        int before = page.getVisibleCardCount();

        page.clickLoadMoreArticles();

        int after = page.getVisibleCardCount();

        assertTrue(after > before, "Jumlah card tidak bertambah setelah klik Load More");
    }

}
