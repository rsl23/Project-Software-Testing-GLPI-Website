package com.proyek.tests;

import com.proyek.pages.product.IntegrationsPage;
import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class IntegrationsPageTest {

    private WebDriver driver;
    private IntegrationsPage page;

    @BeforeAll
    void setup() {
        driver = new ChromeDriver();
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
    @DisplayName("Integrations page should load")
    void testPageLoaded() {
        assertTrue(page.isPageTitleVisible(), "Judul halaman Integrations tidak tampil");
    }

    @Test
    @DisplayName("Integration cards should be visible")
    void testCardsVisible() {
        assertTrue(page.areIntegrationCardsVisible(), "Card integrations tidak tampil");
    }

    @Test
    @DisplayName("Search API should show cards containing keyword API")
    void testSearchIntegration() {
        page.searchIntegration("API");

        assertTrue(page.getVisibleCardCount() > 0, "Tidak ada card setelah search API");
        assertTrue(page.atLeastOneCardContainsApiKeyword(), "Tidak ada card yang mengandung kata API setelah search");
    }

    @Test
    @DisplayName("Click first integration card opens valid integration page")
    void testIntegrationCardOpensValidPage() {
        assertTrue(page.clickFirstIntegrationAndVerifyPage(), "Halaman integration yang dibuka tidak valid");
    }

    @Test
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
    @DisplayName("Load More Articles button should load more cards")
    void testLoadMoreArticles() {
        int before = page.getVisibleCardCount();

        page.clickLoadMoreArticles();

        int after = page.getVisibleCardCount();

        assertTrue(after > before, "Jumlah card tidak bertambah setelah klik Load More");
    }

}



