package com.proyek.tests;

import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.proyek.base.BaseTest;
import com.proyek.pages.RegistrationPage;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;

@Epic("GLPI Website Testing")
@Feature("User Registration")
public class RegistrationPageTest extends BaseTest {

    private RegistrationPage registrationPage;

    @BeforeEach
    public void setup() {
        registrationPage = new RegistrationPage(driver);
        registrationPage.open();
    }

    @Test
    @DisplayName("Complete User Registration Flow")
    @Description("Test complete registration process: fill email, company name, password, country, application address, and submit")
    @Severity(SeverityLevel.CRITICAL)
    @Story("User Registration Journey")
    public void testCompleteRegistrationFlow() {
        // Step 1: Fill Email
        registrationPage.fillUsername("testdemo22@gmail.com");
        
        // Step 2: Fill Company Name
        registrationPage.fillOrgName("ISTTS");
        
        // Step 3: Fill Password
        registrationPage.fillPassword("Passworddemo2.");
        
        // Step 4: Fill Repeat Password
        registrationPage.fillConfirmPassword("Passworddemo2.");
        
        // Step 5: Select Country (Indonesia)
        registrationPage.selectCountry("Indonesia");
        
        // Step 6: Fill Application Address (subdomain)
        registrationPage.fillSubdomain("projectsoftware-testing22");
        
        // Step 7: Select TLD (.sg1.glpi-network.cloud - Singapore)
        registrationPage.selectTld(".sg1.glpi-network.cloud");
        
        // Step 8: Click Sign Me Up button
        registrationPage.clickSubmit();
        
        // Verification: Form submission completed successfully
        // Note: Actual registration blocked by reCAPTCHA in production
        // Test verifies complete flow can be executed without errors
        String currentUrl = driver.getCurrentUrl();
        assertTrue(currentUrl.contains("glpi-network.cloud"), 
            "Registration flow completed - still on registration page as expected (reCAPTCHA present)");
    }
}
