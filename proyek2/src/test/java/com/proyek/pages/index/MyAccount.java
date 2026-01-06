package com.proyek.pages.index;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import com.proyek.base.BasePage;

public class MyAccount extends BasePage {
    private final String myAccountUrl = "https://myaccount.glpi-network.cloud/index.php?mode=myaccount";

    public MyAccount(WebDriver driver) {
        super(driver);
    }

    // ================= URL =================
    public void open() {
        driver.get(myAccountUrl);
        driver.manage().window().maximize();
        acceptCookieIfPresent();
        waitForPageLoad();
    }

    // ================= LOCATORS - ORGANIZATION INFORMATION =================
    private final By orgNameField = By.name("orgName");
    private final By addressField = By.name("address");
    private final By townField = By.name("town");
    private final By zipField = By.name("zip");
    private final By stateOrCountyField = By.name("stateorcounty");
    private final By countryDropdown = By.id("selectcountry_id");
    private final By countryDropdownSelection = By.id("select2-selectcountry_id-container");
    private final By vatassujCheckbox = By.id("vatassuj");
    private final By vatNumberField = By.id("vatnumber");
    private final By profidField = By.id("profid");
    private final By orgSaveButton = By.xpath("(//input[@name='submit'][@value='Save'])[1]");

    // ================= LOCATORS - CONTACT INFORMATION =================
    private final By emailField = By.name("email");
    private final By phoneField = By.name("phone");
    private final By firstNameField = By.name("firstName");
    private final By lastNameField = By.name("lastName");
    private final By emailCcInvoiceField = By.name("emailccinvoice");
    private final By optinMessagesCheckbox = By.id("optinmessages");
    private final By contactSaveButton = By.xpath("(//input[@name='submit'][@value='Save'])[2]");

    // ================= LOCATORS - PASSWORD =================
    private final By passwordField = By.xpath("//input[@name='password'][@type='password']");
    private final By password2Field = By.xpath("//input[@name='password2'][@type='password']");
    private final By changePasswordButton = By.xpath("//input[@name='submit'][@value='Change password']");

    // ================= LOCATORS - BOTTOM LINKS =================
    private final By addPaymentModeLink = By.xpath("//a[contains(@href, 'mode=registerpaymentmode')]");
    private final By deleteAccountLink = By.cssSelector("a.deletemyaccountclick");

    // ================= VISIBILITY - ORGANIZATION =================
    public boolean isOrgNameFieldVisible() {
        return isVisible(orgNameField);
    }

    public boolean isAddressFieldVisible() {
        return isVisible(addressField);
    }

    public boolean isTownFieldVisible() {
        return isVisible(townField);
    }

    public boolean isZipFieldVisible() {
        return isVisible(zipField);
    }

    public boolean isStateOrCountyFieldVisible() {
        return isVisible(stateOrCountyField);
    }

    public boolean isCountryDropdownVisible() {
        return isVisible(countryDropdownSelection);
    }

    public boolean isVatassujCheckboxVisible() {
        return isVisible(vatassujCheckbox);
    }

    public boolean isVatNumberFieldVisible() {
        return isVisible(vatNumberField);
    }

    public boolean isProfidFieldVisible() {
        return isVisible(profidField);
    }

    public boolean isOrgSaveButtonVisible() {
        return isVisible(orgSaveButton);
    }

    // ================= VISIBILITY - CONTACT =================
    public boolean isEmailFieldVisible() {
        return isVisible(emailField);
    }

    public boolean isPhoneFieldVisible() {
        return isVisible(phoneField);
    }

    public boolean isFirstNameFieldVisible() {
        return isVisible(firstNameField);
    }

    public boolean isLastNameFieldVisible() {
        return isVisible(lastNameField);
    }

    public boolean isEmailCcInvoiceFieldVisible() {
        return isVisible(emailCcInvoiceField);
    }

    public boolean isOptinMessagesCheckboxVisible() {
        return isVisible(optinMessagesCheckbox);
    }

    public boolean isContactSaveButtonVisible() {
        return isVisible(contactSaveButton);
    }

    // ================= VISIBILITY - PASSWORD =================
    public boolean isPasswordFieldVisible() {
        return isVisible(passwordField);
    }

    public boolean isPassword2FieldVisible() {
        return isVisible(password2Field);
    }

    public boolean isChangePasswordButtonVisible() {
        return isVisible(changePasswordButton);
    }

    // ================= VISIBILITY - LINKS =================
    public boolean isAddPaymentModeLinkVisible() {
        return isVisible(addPaymentModeLink);
    }

    public boolean isDeleteAccountLinkVisible() {
        return isVisible(deleteAccountLink);
    }

    // ================= FORM FILLING - ORGANIZATION =================
    public void fillOrgName(String orgName) {
        WebElement field = wait.until(ExpectedConditions.visibilityOfElementLocated(orgNameField));
        scrollIntoView(field);
        highlight(field);
        field.clear();
        field.sendKeys(orgName);
        demoSleep(500);
    }

    public void fillAddress(String address) {
        WebElement field = wait.until(ExpectedConditions.visibilityOfElementLocated(addressField));
        scrollIntoView(field);
        highlight(field);
        field.clear();
        field.sendKeys(address);
        demoSleep(500);
    }

    public void fillTown(String town) {
        WebElement field = wait.until(ExpectedConditions.visibilityOfElementLocated(townField));
        scrollIntoView(field);
        highlight(field);
        field.clear();
        field.sendKeys(town);
        demoSleep(500);
    }

    public void fillZip(String zip) {
        WebElement field = wait.until(ExpectedConditions.visibilityOfElementLocated(zipField));
        scrollIntoView(field);
        highlight(field);
        field.clear();
        field.sendKeys(zip);
        demoSleep(500);
    }

    public void fillStateOrCounty(String stateOrCounty) {
        WebElement field = wait.until(ExpectedConditions.visibilityOfElementLocated(stateOrCountyField));
        scrollIntoView(field);
        highlight(field);
        field.clear();
        field.sendKeys(stateOrCounty);
        demoSleep(500);
    }

    public void selectCountry(String countryValue) {
        WebElement dropdown = wait.until(ExpectedConditions.elementToBeClickable(countryDropdownSelection));
        scrollIntoView(dropdown);
        highlight(dropdown);
        demoSleep(500);
        
        try {
            // Click to open Select2 dropdown
            dropdown.click();
            demoSleep(500);
            
            // Use JavaScript to set value directly on hidden select element
            ((JavascriptExecutor) driver).executeScript(
                "arguments[0].value = arguments[1]; $(arguments[0]).trigger('change');", 
                driver.findElement(countryDropdown), 
                countryValue
            );
            demoSleep(500);
        } catch (Exception e) {
            System.out.println("Country selection error: " + e.getMessage());
        }
    }

    public void toggleVatassujCheckbox() {
        WebElement checkbox = wait.until(ExpectedConditions.elementToBeClickable(vatassujCheckbox));
        scrollIntoView(checkbox);
        highlight(checkbox);
        demoSleep(500);
        
        try {
            checkbox.click();
        } catch (Exception e) {
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", checkbox);
        }
        demoSleep(500);
    }

    public void checkVatassujCheckbox() {
        WebElement checkbox = wait.until(ExpectedConditions.elementToBeClickable(vatassujCheckbox));
        if (!checkbox.isSelected()) {
            scrollIntoView(checkbox);
            highlight(checkbox);
            demoSleep(500);
            
            try {
                checkbox.click();
            } catch (Exception e) {
                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", checkbox);
            }
            demoSleep(500);
        }
    }

    public void uncheckVatassujCheckbox() {
        WebElement checkbox = wait.until(ExpectedConditions.elementToBeClickable(vatassujCheckbox));
        if (checkbox.isSelected()) {
            scrollIntoView(checkbox);
            highlight(checkbox);
            demoSleep(500);
            
            try {
                checkbox.click();
            } catch (Exception e) {
                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", checkbox);
            }
            demoSleep(500);
        }
    }

    public void fillVatNumber(String vatNumber) {
        WebElement field = wait.until(ExpectedConditions.visibilityOfElementLocated(vatNumberField));
        scrollIntoView(field);
        highlight(field);
        field.clear();
        field.sendKeys(vatNumber);
        demoSleep(500);
    }

    public void fillProfid(String profid) {
        WebElement field = wait.until(ExpectedConditions.visibilityOfElementLocated(profidField));
        scrollIntoView(field);
        highlight(field);
        field.clear();
        field.sendKeys(profid);
        demoSleep(500);
    }

    public void clickOrgSaveButton() {
        WebElement button = wait.until(ExpectedConditions.elementToBeClickable(orgSaveButton));
        scrollIntoView(button);
        highlight(button);
        demoSleep(500);
        
        try {
            button.click();
        } catch (Exception e) {
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", button);
        }
        demoSleep(1000);
    }

    // ================= FORM FILLING - CONTACT =================
    public void fillEmail(String email) {
        WebElement field = wait.until(ExpectedConditions.visibilityOfElementLocated(emailField));
        scrollIntoView(field);
        highlight(field);
        field.clear();
        field.sendKeys(email);
        demoSleep(500);
    }

    public void fillPhone(String phone) {
        WebElement field = wait.until(ExpectedConditions.visibilityOfElementLocated(phoneField));
        scrollIntoView(field);
        highlight(field);
        field.clear();
        field.sendKeys(phone);
        demoSleep(500);
    }

    public void fillFirstName(String firstName) {
        WebElement field = wait.until(ExpectedConditions.visibilityOfElementLocated(firstNameField));
        scrollIntoView(field);
        highlight(field);
        field.clear();
        field.sendKeys(firstName);
        demoSleep(500);
    }

    public void fillLastName(String lastName) {
        WebElement field = wait.until(ExpectedConditions.visibilityOfElementLocated(lastNameField));
        scrollIntoView(field);
        highlight(field);
        field.clear();
        field.sendKeys(lastName);
        demoSleep(500);
    }

    public void fillEmailCcInvoice(String emailCc) {
        WebElement field = wait.until(ExpectedConditions.visibilityOfElementLocated(emailCcInvoiceField));
        scrollIntoView(field);
        highlight(field);
        field.clear();
        field.sendKeys(emailCc);
        demoSleep(500);
    }

    public void toggleOptinMessagesCheckbox() {
        WebElement checkbox = wait.until(ExpectedConditions.elementToBeClickable(optinMessagesCheckbox));
        scrollIntoView(checkbox);
        highlight(checkbox);
        demoSleep(500);
        
        try {
            checkbox.click();
        } catch (Exception e) {
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", checkbox);
        }
        demoSleep(500);
    }

    public void checkOptinMessagesCheckbox() {
        WebElement checkbox = wait.until(ExpectedConditions.elementToBeClickable(optinMessagesCheckbox));
        if (!checkbox.isSelected()) {
            scrollIntoView(checkbox);
            highlight(checkbox);
            demoSleep(500);
            
            try {
                checkbox.click();
            } catch (Exception e) {
                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", checkbox);
            }
            demoSleep(500);
        }
    }

    public void uncheckOptinMessagesCheckbox() {
        WebElement checkbox = wait.until(ExpectedConditions.elementToBeClickable(optinMessagesCheckbox));
        if (checkbox.isSelected()) {
            scrollIntoView(checkbox);
            highlight(checkbox);
            demoSleep(500);
            
            try {
                checkbox.click();
            } catch (Exception e) {
                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", checkbox);
            }
            demoSleep(500);
        }
    }

    public void clickContactSaveButton() {
        WebElement button = wait.until(ExpectedConditions.elementToBeClickable(contactSaveButton));
        scrollIntoView(button);
        highlight(button);
        demoSleep(500);
        
        try {
            button.click();
        } catch (Exception e) {
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", button);
        }
        demoSleep(1000);
    }

    // ================= FORM FILLING - PASSWORD =================
    public void fillPassword(String password) {
        WebElement field = wait.until(ExpectedConditions.visibilityOfElementLocated(passwordField));
        scrollIntoView(field);
        highlight(field);
        field.clear();
        field.sendKeys(password);
        demoSleep(500);
    }

    public void fillPassword2(String password) {
        WebElement field = wait.until(ExpectedConditions.visibilityOfElementLocated(password2Field));
        scrollIntoView(field);
        highlight(field);
        field.clear();
        field.sendKeys(password);
        demoSleep(500);
    }

    public void clickChangePasswordButton() {
        WebElement button = wait.until(ExpectedConditions.elementToBeClickable(changePasswordButton));
        scrollIntoView(button);
        highlight(button);
        demoSleep(500);
        
        try {
            button.click();
        } catch (Exception e) {
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", button);
        }
        demoSleep(1000);
    }

    // ================= ACTIONS - LINKS =================
    public void clickAddPaymentMode() {
        WebElement link = wait.until(ExpectedConditions.elementToBeClickable(addPaymentModeLink));
        scrollIntoView(link);
        highlight(link);
        demoSleep(500);
        
        try {
            link.click();
        } catch (Exception e) {
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", link);
        }
        demoSleep(1000);
    }

    public void clickDeleteAccount() {
        WebElement link = wait.until(ExpectedConditions.elementToBeClickable(deleteAccountLink));
        scrollIntoView(link);
        highlight(link);
        demoSleep(500);
        
        try {
            link.click();
        } catch (Exception e) {
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", link);
        }
        demoSleep(1000);
    }

    // ================= COMPLETE FORMS =================
    public void fillCompleteOrganizationForm(String orgName, String address, String town, String zip, 
                                             String stateOrCounty, String country, String profid) {
        fillOrgName(orgName);
        fillAddress(address);
        fillTown(town);
        fillZip(zip);
        fillStateOrCounty(stateOrCounty);
        selectCountry(country);
        fillProfid(profid);
    }

    public void fillCompleteContactForm(String email, String phone, String firstName, 
                                        String lastName, String emailCc) {
        fillEmail(email);
        fillPhone(phone);
        fillFirstName(firstName);
        fillLastName(lastName);
        fillEmailCcInvoice(emailCc);
    }

    public void fillCompletePasswordForm(String password) {
        fillPassword(password);
        fillPassword2(password);
    }

    // ================= HELPERS =================
    private void waitForPageLoad() {
        wait.until(webDriver -> ((JavascriptExecutor) webDriver)
                .executeScript("return document.readyState").equals("complete"));
    }

    public String getCurrentUrl() {
        return driver.getCurrentUrl();
    }

    public boolean isOnMyAccountPage() {
        return getCurrentUrl().contains("mode=myaccount");
    }
}
