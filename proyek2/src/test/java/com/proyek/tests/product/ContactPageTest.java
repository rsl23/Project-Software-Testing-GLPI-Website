package com.proyek.tests.product;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import com.proyek.pages.product.ContactPage;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ContactPageTest {

    private WebDriver driver;
    private ContactPage contactPage;

    @BeforeAll
    public void setUp() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        contactPage = new ContactPage(driver);
    }

    @AfterAll
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    @Order(1)
    void testFillAndSubmitContactForm() {
        contactPage.open();
        
        // Click radio buttons
        contactPage.clickBecomeUserRadio();
        contactPage.clickOpenCloudInstanceRadio();
        contactPage.clickBetween1And25Radio();
        
        // Fill form inputs
        contactPage.fillLastName("Doe");
        contactPage.fillFirstName("John");
        contactPage.fillCompany("ISTTS");
        contactPage.fillCountry("Indonesia");
        contactPage.fillEmail("testdemo@gmail.com");
        contactPage.fillPhone("6285778909388");
        contactPage.fillMessage("I would like to become a partner");
        
        // Submit form
        contactPage.clickSendMessage();
        
        System.out.println("✅ Contact form filled and submitted successfully");
    }
}

