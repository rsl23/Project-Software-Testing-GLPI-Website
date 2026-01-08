package com.proyek.tests.company;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import com.proyek.base.BaseTest;
import com.proyek.pages.company.CustomerPage;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CustomerPageTest extends BaseTest {

    private CustomerPage customerPage;

    @BeforeEach
    public void setUpPage() {
        customerPage = new CustomerPage(driver);
        customerPage.open();
        sleep(1000); // Tunggu halaman load
    }

    @Test
    @Order(1)
    @DisplayName("Verify Success Stories headline is visible")
    public void testSuccessStoriesVisible() {
        sleep(500);
        assertTrue(customerPage.isSuccessStoriesVisible(), "Success Stories headline should be visible");
    }

    @Test
    @Order(2)
    @DisplayName("Verify Success Video is visible")
    public void testSuccessVideoVisible() {
        sleep(500);
        assertTrue(customerPage.isSuccessVideoVisible(), "Success Video thumbnail should be visible");
    }

    @Test
    @Order(3)
    @DisplayName("Click Success Video and check lightbox")
    public void testClickSuccessVideo() {
        sleep(500);
        // scroll dan klik video
        customerPage.clickSuccessVideo();
        sleep(1000); // Tunggu lightbox muncul

        // verifikasi lightbox muncul
        assertTrue(customerPage.isVideoLightboxVisible(), "Video lightbox should be visible after click");
        sleep(500);
    }

    @Test
    @Order(4)
    @DisplayName("Check Success Video data-src")
    public void testSuccessVideoSrc() {
        sleep(500);
        String src = customerPage.getSuccessVideoSrc();
        assertTrue(src.contains("yNnYSwF7Xxo"), "Video data-src should contain video ID yNnYSwF7Xxo");
    }

    @Test
    @Order(5)
    @DisplayName("Click Watch our Youtube Channel button")
    public void testClickWatchYoutubeButton() {
        sleep(500);
        customerPage.clickWatchYoutube();
        sleep(1000); // Tunggu tab baru terbuka

        // pindah ke tab baru
        String originalTab = driver.getWindowHandle();
        for (String handle : driver.getWindowHandles()) {
            if (!handle.equals(originalTab)) {
                driver.switchTo().window(handle);
            }
        }
        sleep(500); // Tunggu halaman youtube load

        assertEquals("https://www.youtube.com/@glpi-network", customerPage.getCurrentUrl(),
                "Youtube Channel URL should be correct");
    }

    // Helper method untuk sleep
    private void sleep(int milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
