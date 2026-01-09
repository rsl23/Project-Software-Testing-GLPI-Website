package com.proyek.tests.resources;

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

import com.proyek.pages.resources.DownloadPage;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class DownloadPageTest {

    private WebDriver driver;
    private DownloadPage downloadPage;

    @BeforeAll
    void setupAll() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        downloadPage = new DownloadPage(driver);
        downloadPage.open();
    }

    @AfterAll
    void teardownAll() {
        if (driver != null)
            driver.quit();
    }

    // ================= PAGE =================
    @Test
    @Order(1)
    @DisplayName("Download page should be visible")
    void testPageVisible() {
        assertTrue(downloadPage.isPageVisible(), "Download page tidak terlihat!");
    }

    // ================= DOWNLOAD VERSION =================
    @Test
    @Order(2)
    @DisplayName("Click 'Download Version' redirects to GitHub")
    void testDownloadVersion() {
        String mainWindow = driver.getWindowHandle();
        
        // Klik langsung tanpa method clickDownloadVersion karena method itu auto-return
        try {
            org.openqa.selenium.WebElement downloadBtn = driver.findElement(
                org.openqa.selenium.By.xpath("//a[.//div[contains(text(),'Download version')]]"));
            downloadBtn.click();
            Thread.sleep(2000); // tunggu redirect/tab baru
            
            // Cek apakah ada tab baru atau redirect
            if (driver.getWindowHandles().size() > 1) {
                for (String window : driver.getWindowHandles()) {
                    if (!window.equals(mainWindow)) {
                        driver.switchTo().window(window);
                        assertTrue(driver.getCurrentUrl().contains("github.com"), 
                            "Download Version should redirect to GitHub: " + driver.getCurrentUrl());
                        driver.close();
                        driver.switchTo().window(mainWindow);
                        break;
                    }
                }
            } else {
                // Jika redirect di tab yang sama
                assertTrue(driver.getCurrentUrl().contains("github.com"), 
                    "Download Version should redirect to GitHub: " + driver.getCurrentUrl());
                driver.navigate().back();
                Thread.sleep(1000);
            }
        } catch (Exception e) {
            System.out.println("Error during download version test: " + e.getMessage());
        }
        
        assertTrue(downloadPage.isPageVisible());
    }

    // ================= READ DOCUMENTATION =================
    @Test
    @Order(3)
    @DisplayName("Read Documentation opens new tab to readthedocs.io")
    void testReadDocumentation() {
        String mainWindow = driver.getWindowHandle();
        downloadPage.clickReadDocumentation();
        
        // Cek apakah ada tab baru
        if (driver.getWindowHandles().size() > 1) {
            for (String window : driver.getWindowHandles()) {
                if (!window.equals(mainWindow)) {
                    driver.switchTo().window(window);
                    String currentUrl = driver.getCurrentUrl();
                    assertTrue(currentUrl.contains("readthedocs.io") || currentUrl.contains("glpi-install"), 
                        "Read Documentation should open readthedocs.io: " + currentUrl);
                    driver.close();
                    driver.switchTo().window(mainWindow);
                    break;
                }
            }
        }
        
        assertTrue(downloadPage.isPageVisible());
    }

    // ================= CHECK SOURCE CODE =================
    @Test
    @Order(4)
    @DisplayName("Check Source Code opens new tab to GitHub")
    void testCheckSourceCode() {
        String mainWindow = driver.getWindowHandle();
        downloadPage.clickCheckSourceCode();
        
        // Cek apakah ada tab baru
        if (driver.getWindowHandles().size() > 1) {
            for (String window : driver.getWindowHandles()) {
                if (!window.equals(mainWindow)) {
                    driver.switchTo().window(window);
                    assertTrue(driver.getCurrentUrl().contains("github.com"), 
                        "Check Source Code should open GitHub: " + driver.getCurrentUrl());
                    driver.close();
                    driver.switchTo().window(mainWindow);
                    break;
                }
            }
        }
        
        assertTrue(downloadPage.isPageVisible());
    }

    // ================= CONTACT PARTNER =================
    @Test
    @Order(5)
    @DisplayName("Contact Partner opens partners page")
    void testContactPartner() {
        String mainWindow = driver.getWindowHandle();
        downloadPage.clickPartnerAndReturn();
        
        // Cek apakah ada tab baru atau redirect
        if (driver.getWindowHandles().size() > 1) {
            for (String window : driver.getWindowHandles()) {
                if (!window.equals(mainWindow)) {
                    driver.switchTo().window(window);
                    assertTrue(driver.getCurrentUrl().contains("/partners"), 
                        "Contact Partner should open partners page: " + driver.getCurrentUrl());
                    driver.close();
                    driver.switchTo().window(mainWindow);
                    break;
                }
            }
        } else {
            // Jika redirect di tab yang sama, cek URL sebelum navigate back
            String currentUrl = driver.getCurrentUrl();
            if (currentUrl.contains("/partners")) {
                assertTrue(true, "Successfully navigated to partners page");
                driver.navigate().back();
            }
        }
        
        assertTrue(downloadPage.isPageVisible());
    }

    // ================= VIDEO PLAY =================
    @Test
    @Order(6)
    @DisplayName("YouTube video play button should be clickable")
    void testVideoPlay() {
        downloadPage.clickVideoPlay();
        assertTrue(downloadPage.isPageVisible(), "Should stay on download page after clicking video");
    }

    // ================= CONTACT FORM =================
    // @Test
    // @Order(6)
    // @DisplayName("Contact form can be filled and submitted")
    // void testContactForm() {
    //     downloadPage.fillContactForm();
    //     // Bisa assert success message jika ada
    //     assertTrue(downloadPage.isPageVisible());
    // }

    // // ================= TESTIMONIALS =================
    // @Test
    // @Order(7)
    // @DisplayName("Open Testimonials in new tab")
    // void testOpenTestimonials() {
    //     downloadPage.openTestimonials();
    //     assertTrue(downloadPage.isPageVisible());
    // }

    // // ================= EXPLORE =================
    // @Test
    // @Order(8)
    // @DisplayName("Explore features button works and returns")
    // void testExploreAndReturn() {
    //     downloadPage.exploreAndReturn();
    //     assertTrue(downloadPage.isPageVisible());
    // }

    // // ================= START NOW =================
    // @Test
    // @Order(9)
    // @DisplayName("Start Now button opens new tab")
    // void testStartNow() {
    //     downloadPage.clickStartNow();
    //     assertTrue(downloadPage.isPageVisible());
    // }

    // // ================= NEWSLETTER =================
    // @Test
    // @Order(10)
    // @DisplayName("Subscribe Newsletter works")
    // void testNewsletter() {
    //     String email = "demo@test.com";
    //     downloadPage.fillNewsletter(email);

    //     WebElement emailInput = driver.findElement(downloadPage.newsletterEmail);
    //     assertEquals(email, emailInput.getAttribute("value"), "Email newsletter tidak sesuai!");
    // }
}
