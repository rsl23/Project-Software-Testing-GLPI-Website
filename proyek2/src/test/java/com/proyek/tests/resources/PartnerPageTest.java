package com.proyek.tests.resources;

import java.time.Duration;

import org.junit.jupiter.api.AfterAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
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
import org.openqa.selenium.support.ui.WebDriverWait;

import com.proyek.pages.resources.PartnerPage;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class PartnerPageTest {

    private WebDriver driver;
    private PartnerPage partnerPage;
    private WebDriverWait wait; // <-- ini tambahan penting

    @BeforeAll
    void setupAll() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        partnerPage = new PartnerPage(driver);
        wait = new WebDriverWait(driver, Duration.ofSeconds(10)); // inisialisasi wait
        partnerPage.open();
    }

    @AfterAll
    void teardownAll() {
        if (driver != null)
            driver.quit();
    }

    // ================= PAGE LOAD =================
    @Test
    @Order(1)
    @DisplayName("Partner page loads successfully")
    void testPageLoad() {
        assertTrue(driver.getTitle().toLowerCase().contains("partner")
                || driver.getCurrentUrl().toLowerCase().contains("partner"),
                "Partner page tidak terbuka!");
    }

    // ================= SEARCH + FILTER =================
    @Test
    @Order(2)
    @DisplayName("Search 'Eoris' + Status 'Platinium' + Country 'France' shows correct card")
    void testSearchFilterDynamicList() {
        partnerPage.typeSearch("Eoris");
        assertEquals("Eoris", partnerPage.getSearchValue(), "Search input value tidak sesuai");

        partnerPage.selectStatus("Platinium");
        assertEquals("Platinium", partnerPage.getSelectedStatus(), "Status dropdown tidak sesuai");

        partnerPage.selectCountry("France");
        assertEquals("France", partnerPage.getSelectedCountry(), "Country dropdown tidak sesuai");

        boolean isCardVisible = partnerPage.isPartnerCardVisible("Eoris");
        assertTrue(isCardVisible, "Partner 'Eoris' tidak muncul di hasil filter");
    }

    // ================= SEE WEBSITE =================
    @Test
    @Order(3)
    @DisplayName("Click 'See the website' opens correct partner URL")
    void testClickSeeWebsite() {
        String partnerUrl = partnerPage.clickSeeWebsite("Eoris");
        assertTrue(partnerUrl.contains("eoris.fr"), "URL website partner tidak sesuai");
    }

    @Test
    @Order(4)
    @DisplayName("Click 'I want to become a partner' redirects correctly")
    void testClickBecomePartnerButton() {
        String url = partnerPage.clickBecomePartnerButton();
        assertTrue(url.contains("/contact/"), "URL redirect tidak sesuai");
    }

}
