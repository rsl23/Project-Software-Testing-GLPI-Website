package com.proyek.pages;

import java.io.File;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;

import com.proyek.base.BasePage;

public class TrainingPage extends BasePage {
    private final String url = "https://www.glpi-project.org/en/trainings/";

    public TrainingPage(WebDriver driver) {
        super(driver);
    }

    // ================= URL =================
    public void open() {
        driver.get(url);
        acceptCookieIfPresent();
        waitForPageLoad();
    }

    // ================= LOCATORS =================
    private final By pageTitle = By.id("headline-14-432450");

    // Menu Navigation
    private final By resourcesMenu = By.xpath("//span[@class='oxy-mega-dropdown_link-text' and text()='Resources']");
    private final By trainingLink = By.id("text_block-719-10");

    // Buttons & Tabs
    private final By chooseCourseButton = By.id("link_text-12-432450");
    private final By glpiPluginsTab = By.id("_tab-80-432450");
    private final By registerButton = By.id("bouton-inscription-distanciel-1");
    private final By glpiAdminTab = By.id("_tab-79-432450");
    private final By downloadProgramButton = By.id("link_button-488-432450");
    private final By registerPresentiel1 = By.id("bouton-inscription-presentiel-1");
    private final By registerPresentiel2 = By.id("bouton-inscription-presentiel-2");
    private final By registerPresentiel3 = By.id("bouton-inscription-presentiel-3");
    private final By registerPresentiel4 = By.id("bouton-inscription-presentiel-4");
    private final By registerPresentiel5 = By.id("bouton-inscription-presentiel-5");

    // Form
    private final By inputLastName = By.cssSelector("input[name='Nom']");
    private final By inputFirstName = By.cssSelector("input[name='Prenom']");
    private final By inputEmail = By.cssSelector("input[name='email'][type='email']");
    private final By inputCompany = By.cssSelector("input[name='Societe']");
    private final By inputCountry = By.cssSelector("input[name='Pays']");
    private final By inputPhone = By.cssSelector("input[name='tel']");
    private final By radioOnline = By.xpath("//input[@type='radio'][@name='module'][@value='Online']");
    private final By dropdownFormation = By.cssSelector("select[name='formation_en_ligne']");
    private final By inputMessage = By.cssSelector("textarea[name='message']");
    private final By submitButton = By.cssSelector("input.wpcf7-submit[type='submit']");

    // ================= PAGE VISIBILITY =================
    public boolean isPageVisible() {
        return isVisible(pageTitle);
    }

    // ================= HELPER METHODS =================
    private void waitForPageLoad() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(pageTitle));
    }

    public void clickElement(By locator) {
        WebElement el = wait.until(ExpectedConditions.elementToBeClickable(locator));
        scrollIntoView(el);
        highlight(el);
        el.click();
    }

    public void fillInput(By locator, String value) {
        WebElement el = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        scrollIntoView(el);
        highlight(el);
        el.clear();
        el.sendKeys(value);
    }

    public void selectRadio(By locator) {
        WebElement el = wait.until(ExpectedConditions.elementToBeClickable(locator));
        if (!el.isSelected()) {
            el.click();
        }
    }

    public boolean selectDropdownByValue(By locator, String value) {
        try {
            WebElement el = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
            scrollIntoView(el);
            highlight(el);
            Select select = new Select(el);
            select.selectByValue(value);
            return true;
        } catch (Exception e) {
            System.out.println("⚠️ Dropdown not found or value not available: " + value);
            return false;
        }
    }

    // ================= BUTTON ACTIONS =================
    public void clickResourcesMenu() {
        clickElement(resourcesMenu);
    }

    public void clickTrainingLink() {
        clickElement(trainingLink);
    }

    public void clickChooseCourse() {
        clickElement(chooseCourseButton);
    }

    public void clickGlpiPluginsTab() {
        clickElement(glpiPluginsTab);
    }

    public void clickRegisterButton() {
        clickElement(registerButton);
    }

    public void clickGlpiAdminTab() {
        clickElement(glpiAdminTab);
    }

    public void clickDownloadProgram() {
        clickElement(downloadProgramButton);
    }

    public void clickAllRegisterPresentielButtons() {
        By[] buttons = { registerPresentiel1, registerPresentiel2, registerPresentiel3,
                registerPresentiel4, registerPresentiel5 };
        for (By btn : buttons) {
            try {
                clickElement(btn);
            } catch (Exception e) {
                System.out.println("⚠️ Register button not clickable: " + btn);
            }
        }
    }

    // ================= FORM ACTIONS =================
    public boolean fillRegistrationForm(String lastName, String firstName, String email, String company,
            String country, String phone, String message, String formationDate) {
        try {
            fillInput(inputLastName, lastName);
            fillInput(inputFirstName, firstName);
            fillInput(inputEmail, email);
            fillInput(inputCompany, company);
            fillInput(inputCountry, country);
            fillInput(inputPhone, phone);
            selectRadio(radioOnline);
            boolean dropdownSelected = selectDropdownByValue(dropdownFormation, formationDate);
            fillInput(inputMessage, message);
            clickElement(submitButton);
            return dropdownSelected;
        } catch (Exception e) {
            System.out.println("⚠️ Error filling registration form: " + e.getMessage());
            return false;
        }
    }

    // ================= DOWNLOAD UTILITY =================
    public boolean isPDFDownloaded(String expectedFileName) {
        String userHome = System.getProperty("user.home");
        File downloadedFile = new File(userHome + "\\Downloads\\" + expectedFileName);
        int waitTime = 0;
        while (!downloadedFile.exists() && waitTime < 10) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
            }
            waitTime++;
        }
        return downloadedFile.exists() && downloadedFile.length() > 1024;
    }

    public void downloadProgram() {
        String mainWindow = driver.getWindowHandle();
        WebElement btn = wait.until(ExpectedConditions.elementToBeClickable(downloadProgramButton));
        ((JavascriptExecutor) driver)
                .executeScript("arguments[0].scrollIntoView({block: 'center'}); window.scrollBy(0,-100);", btn);
        highlight(btn);
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", btn);

        for (String handle : driver.getWindowHandles()) {
            if (!handle.equals(mainWindow)) {
                driver.switchTo().window(handle);
                driver.close();
            }
        }
        driver.switchTo().window(mainWindow);
    }
}
