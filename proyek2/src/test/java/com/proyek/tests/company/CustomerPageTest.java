package com.proyek.tests.company;

import com.proyek.pages.company.CustomerPage;
import com.proyek.base.BaseTest;
import org.junit.jupiter.api.*;
import org.openqa.selenium.*;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CustomerPageTest extends BaseTest {

    private CustomerPage customerPage;

    @BeforeEach
    public void setUpPage() {
        customerPage = new CustomerPage(driver);
        customerPage.open();
    }

    @Test
    @Order(1)
    @DisplayName("Verify Success Stories headline is visible")
    public void testSuccessStoriesVisible() {
        assertTrue(customerPage.isSuccessStoriesVisible(), "Success Stories headline should be visible");
    }

    @Test
    @Order(2)
    @DisplayName("Verify Success Video is visible")
    public void testSuccessVideoVisible() {
        assertTrue(customerPage.isSuccessVideoVisible(), "Success Video thumbnail should be visible");
    }

    @Test
    @Order(3)
    @DisplayName("Click Success Video and check lightbox")
    public void testClickSuccessVideo() {
        // scroll dan klik video
        customerPage.clickSuccessVideo();

        // verifikasi lightbox muncul
        assertTrue(customerPage.isVideoLightboxVisible(), "Video lightbox should be visible after click");
    }

    @Test
    @Order(4)
    @DisplayName("Check Success Video data-src")
    public void testSuccessVideoSrc() {
        String src = customerPage.getSuccessVideoSrc();
        assertTrue(src.contains("yNnYSwF7Xxo"), "Video data-src should contain video ID yNnYSwF7Xxo");
    }

    @Test
    @Order(5)
    @DisplayName("Click Watch our Youtube Channel button")
    public void testClickWatchYoutubeButton() {
        customerPage.clickWatchYoutube();

        // pindah ke tab baru
        String originalTab = driver.getWindowHandle();
        for (String handle : driver.getWindowHandles()) {
            if (!handle.equals(originalTab)) {
                driver.switchTo().window(handle);
            }
        }

        assertEquals("https://www.youtube.com/@glpi-network", customerPage.getCurrentUrl(),
                "Youtube Channel URL should be correct");
    }
}
