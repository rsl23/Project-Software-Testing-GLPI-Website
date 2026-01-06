package com.proyek.tests;

import com.proyek.pages.resources.PartnerPage;
import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class PartnerPageTest {

    private WebDriver driver;
    private PartnerPage partnerPage;
    private WebDriverWait wait; // <-- ini tambahan penting

    @BeforeAll
    void setupAll() {
        driver = new ChromeDriver();
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
    @DisplayName("Click 'I want to become a partner' redirects correctly")
    void testClickBecomePartnerButton() {
        String url = partnerPage.clickBecomePartnerButton();
        assertTrue(url.contains("/contact/"), "URL redirect tidak sesuai");
    }

}



