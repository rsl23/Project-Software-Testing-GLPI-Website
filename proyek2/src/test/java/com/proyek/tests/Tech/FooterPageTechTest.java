package com.proyek.tests.Tech;

import com.proyek.pages.Tech.FooterPage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.time.Duration;
import java.util.List;

// import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class FooterPageTechTest {

    private WebDriver driver;
    private FooterPage footerPage;
    private final String rootUrl = "https://glpi-network.cloud/";

    @BeforeEach
    void setup() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(30));
        driver.get(rootUrl);

        footerPage = new FooterPage(driver);
    }

    @Test
    @DisplayName("Footer links bisa diklik dan redirect dengan benar (efisien)")
    void testFooterLinksEfficient() {

        List<String> hrefs = footerPage.getAllFooterHrefs();
        String mainWindow = driver.getWindowHandle();
        boolean socialChecked = false;

        for (String href : hrefs) {

            driver.get(rootUrl);

            WebElement link = driver.findElement(
                    By.cssSelector("footer#main-footer a[href='" + href + "']"));

            // ===== ASSERT 1: LINK ADA & VISIBLE =====
            assertTrue(link.isDisplayed(), "Footer link tidak visible: " + href);

            boolean isSocial = href.matches(
                    ".*(linkedin|facebook|twitter|x\\.com|youtube|reddit|github|t\\.me|telegram).*");

            // Sosial cukup sekali
            if (isSocial && socialChecked)
                continue;
            if (isSocial)
                socialChecked = true;

            int beforeWindow = driver.getWindowHandles().size();
            String beforeUrl = driver.getCurrentUrl();

            footerPage.clickFooterLink(link);

            try {
                Thread.sleep(1500);
            } catch (InterruptedException ignored) {
            }

            int afterWindow = driver.getWindowHandles().size();

            if (afterWindow > beforeWindow) {
                // ===== SOCIAL / NEW TAB =====
                assertTrue(afterWindow > beforeWindow,
                        "Social link tidak membuka tab baru: " + href);

                for (String w : driver.getWindowHandles()) {
                    if (!w.equals(mainWindow)) {
                        driver.switchTo().window(w);
                        break;
                    }
                }

                // Page load
                assertTrue(driver.getTitle() != null && !driver.getTitle().isEmpty(),
                        "Page sosial gagal load: " + href);

                driver.close();
                driver.switchTo().window(mainWindow);

            } else {
                // ===== SAME TAB =====
                assertTrue(
                        !driver.getCurrentUrl().equals(beforeUrl),
                        "URL tidak berubah setelah klik footer link: " + href);

                assertTrue(
                        driver.getTitle() != null && !driver.getTitle().isEmpty(),
                        "Page tujuan gagal load: " + href);
            }
        }
    }

}
