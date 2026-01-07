package com.proyek.tests.Tech;

import com.proyek.pages.Tech.ContactUsPageTech;
import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ContactUsPageTechTest {

    private WebDriver driver;
    private ContactUsPageTech contact;

    @BeforeAll
    void setup() {
        driver = new ChromeDriver();
        contact = new ContactUsPageTech(driver);
    }

    @BeforeEach
    void openPage() {
        contact.open(); // buka fresh page setiap test
    }

    @AfterAll
    void teardown() {
        if (driver != null) {
            driver.quit();
        }
    }

    // ================= PAGE =================

    @Test
    @DisplayName("Contact Us page loads correctly")
    void testContactUsPageLoads() {
        assertTrue(contact.isPageTitleVisible(),
                "Judul Contact Us harus tampil");
    }

    // ================= CTA =================

    @Test
    @DisplayName("Access Knowledge Base opens Teclib FAQ in new tab")
    void testKnowledgeBaseNavigation() {
        contact.clickKnowledgeBase();
        assertTrue(
                contact.getLastNavigatedUrl().contains("help"),
                "ACCESS KNOWLEDGE BASE harus ke faq.teclib.com");
    }

    @Test
    @DisplayName("Start Trial navigates to registration page")
    void testStartTrialNavigation() {
        contact.clickStartTrial();
        assertTrue(
                contact.getLastNavigatedUrl().contains("register"),
                "START TRIAL harus ke halaman register");
    }

    // ================= FORM =================

    @Test
    @DisplayName("Contact form is visible")
    void testContactFormVisible() {
        assertTrue(contact.isContactFormVisible(),
                "Form Contact Us harus tampil");
    }

    @Test
    @DisplayName("All required form fields are visible")
    void testAllRequiredFieldsVisible() {
        assertTrue(
                contact.areAllRequiredFieldsVisible(),
                "Semua field wajib harus tampil");
    }

    @Test
    @DisplayName("Send button is enabled")
    void testSendButtonEnabled() {
        assertTrue(contact.isSendButtonEnabled(),
                "Tombol Send harus enabled");
    }

    @Test
    @DisplayName("Form action URL is correct")
    void testFormActionUrl() {
        assertEquals(
                "https://glpi-network.cloud/contact-us/",
                contact.getFormActionUrl(),
                "Form action harus ke /contact-us/");
    }

    @Test
    @DisplayName("Test Contact Form Submission")
    public void testContactFormSubmission() {
        // cek form & tombol
        assertTrue(contact.isContactFormVisible(), "Form harus muncul");
        assertTrue(contact.areAllRequiredFieldsVisible(), "Semua field required harus terlihat");
        assertTrue(contact.isSendButtonEnabled(), "Button Send harus enable");

        // isi form
        contact.fillContactForm(
                "John Doe",
                "Tech Company",
                "United States (US)", // harus sama persis dengan option HTML
                "1234567890",
                "john.doe@example.com",
                "Demonstration",
                "Ini komentar test");

        // submit form
        contact.submitForm();

        // tunggu dan cek pesan sukses
        boolean submitted = contact.isFormSubmitted(); // tunggu pesan sukses muncul
        assertTrue(submitted, "Form harus berhasil dikirim");
    }

}
