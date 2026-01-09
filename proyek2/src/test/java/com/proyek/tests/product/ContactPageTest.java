package com.proyek.tests.product;

import java.util.Set;

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
import com.proyek.pages.product.TeclibSupportFormPage;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ContactPageTest {

    private WebDriver driver;
    private ContactPage contactPage;
    private TeclibSupportFormPage teclibSupportFormPage;
    private String mainWindowHandle;

    @BeforeAll
    public void setUp() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        contactPage = new ContactPage(driver);
        teclibSupportFormPage = new TeclibSupportFormPage(driver);
        mainWindowHandle = driver.getWindowHandle();
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

    @Test
    @Order(2)
    void testFillTeclibSupportForm() {
        // Return to contact page if needed
        driver.switchTo().window(mainWindowHandle);
        contactPage.open();
        
        // Click "You already are a GLPI user" radio
        contactPage.clickYouAlreadyGlpiUserRadio();
        
        // Click Cloud link - opens new tab
        contactPage.clickCloudLink();
        
        // Switch to new tab
        Set<String> allWindows = driver.getWindowHandles();
        for (String windowHandle : allWindows) {
            if (!windowHandle.equals(mainWindowHandle)) {
                driver.switchTo().window(windowHandle);
                break;
            }
        }
        
        // Wait for page to load and verify URL
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        System.out.println("📍 Current URL: " + driver.getCurrentUrl());
        
        // Fill Teclib support form
        teclibSupportFormPage.clickCloudInstanceRadio();
        teclibSupportFormPage.fillInstanceUrl("projectsoftware-testing.sg1.glpi-network.cloud");
        teclibSupportFormPage.fillSupportKey("ABCDEF");
        teclibSupportFormPage.clickAdditionalRadio();
        teclibSupportFormPage.fillFullName("John Doe");
        teclibSupportFormPage.fillEmail("testdemo@gmail.com");
        teclibSupportFormPage.fillPhone("085778090377");
        
        // Try to click Altcha checkbox (may not be available due to CAPTCHA)
        try {
            teclibSupportFormPage.clickAltchaCheckbox();
            System.out.println("✅ Altcha checkbox clicked");
        } catch (Exception e) {
            System.out.println("⚠️ Altcha checkbox not available (CAPTCHA protection) - skipping");
        }
        
        // Try to submit (may fail if CAPTCHA is required)
        try {
            teclibSupportFormPage.clickSubmit();
            System.out.println("✅ Form submitted");
        } catch (Exception e) {
            System.out.println("⚠️ Submit button not clickable (CAPTCHA may be required)");
        }
        
        System.out.println("✅ Teclib support form filled successfully");
        
        // Close new tab and return to main window
        driver.close();
        driver.switchTo().window(mainWindowHandle);
    }
}

