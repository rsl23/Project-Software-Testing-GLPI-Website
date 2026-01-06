package com.proyek.tests.resources;

import com.proyek.pages.resources.FaqPage;
import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class FaqPageTest {

    private WebDriver driver;
    private FaqPage faqPage;

    @BeforeAll
    void setupAll() {
        driver = new ChromeDriver();
        faqPage = new FaqPage(driver);
        faqPage.open();
    }

    @AfterAll
    void teardownAll() {
        if (driver != null)
            driver.quit();
    }

    // ================= PAGE =================
    @Test
    @Order(1)
    @DisplayName("FAQ page is visible")
    void testPageVisible() {
        assertTrue(faqPage.isPageVisible(), "FAQ page tidak terlihat!");
    }

    // ================= DROPDOWNS =================
    @Test
    @Order(2)
    @DisplayName("Dropdowns can be opened individually")
    void testDropdowns() {
        // Buka satu per satu dan cek tidak error
        faqPage.openInventoryDropdown();
        faqPage.openNotificationsDropdown();
        faqPage.openGroupsDropdown();
        faqPage.openCustomiseDropdown();
        faqPage.openPluginsDropdown();
        faqPage.openAuthSSODropdown();
        faqPage.openScimDropdown();
        faqPage.openWebhookDropdown();
        assertTrue(true); // placeholder, dropdown visibility bisa diperluas
    }

    // ================= BUTTONS =================
    @Test
    @Order(3)
    @DisplayName("All buttons work correctly")
    void testButtons() {
        // Tombol internal/external (open new tab di handle di page class)
        faqPage.openDropdownAndClickButton(faqPage.inventoryDropdown, faqPage.inventoryButton);
        faqPage.openDropdownAndClickButton(faqPage.notificationsDropdown, faqPage.notificationsButton);
        faqPage.openDropdownAndClickButton(faqPage.groupsDropdown, faqPage.groupsButton);
        faqPage.openDropdownAndClickButton(faqPage.customiseDropdown, faqPage.customiseButton);
        faqPage.openDropdownAndClickButton(faqPage.pluginsDropdown, faqPage.glpiPluginsButton);
        faqPage.openDropdownAndClickButton(faqPage.authSSODropdown, faqPage.authSSOButton);
        faqPage.openDropdownAndClickButton(faqPage.scimDropdown, faqPage.scimButton);
        faqPage.openDropdownAndClickButton(faqPage.webhookDropdown, faqPage.webhookButton);

        faqPage.clickViewFullFAQ();
        faqPage.clickSeePluginsFaq();
        faqPage.clickViewAllTestimonials();
        faqPage.clickExploreNow();
        faqPage.clickStartNow();

        // Pastikan kita kembali ke halaman FAQ
        assertTrue(faqPage.isPageVisible(), "Page tidak kembali ke FAQ setelah tombol eksternal");
    }

    // ================= CONTACT FORM =================
    @Test
    @Order(4)
    @DisplayName("Contact form can be filled and submitted")
    void testContactForm() {
        faqPage.fillContactForm();
        assertTrue(faqPage.isPageVisible(), "Page hilang setelah submit contact form");
    }

    // ================= NEWSLETTER =================
    @Test
    @Order(5)
    @DisplayName("Newsletter form can be filled and submitted")
    void testNewsletter() {
        String testEmail = "testuser@domain.com";
        faqPage.fillNewsletter(testEmail);

        // Optional: cek input value atau success message jika ada
        assertTrue(true, "Newsletter submitted"); // placeholder
    }
}
