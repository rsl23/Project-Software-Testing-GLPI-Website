package com.proyek.pages;

import com.proyek.base.BasePage;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;

public class CmdbPage extends BasePage {

    private final String cmdbUrl = "https://www.glpi-project.org/en/cmdb/";

    public CmdbPage(WebDriver driver) {
        super(driver);
    }

    // ================= URL =================
    public void open() {
        driver.get(cmdbUrl);
        acceptCookieIfPresent();
        waitForPageLoad();
    }

    // ================= LOCATORS =================
    private final By heroTitle = By.tagName("h1");
    private final By newsletterEmail = By.cssSelector("input[type='email']");
    private final By newsletterSubmit = By.cssSelector("input[type='submit']");
    private final By socialLinks = By.cssSelector("footer a[target='_blank'][href^='http']");

    // ================= VISIBILITY =================
    public boolean isHeroVisible() {
        return isVisible(heroTitle);
    }

    // ================= BUTTON ACTIONS =================
    public void clickButtonByVisibleText(String textContains) {
        clickButtonGeneric(By.xpath("//a[contains(normalize-space(.),'" + textContains + "')]"));
    }

    public void clickLastButtonByText(String textContains) {
        List<WebElement> buttons = driver.findElements(
                By.xpath("//a[contains(normalize-space(.),'" + textContains + "')]"));
        if (!buttons.isEmpty()) {
            clickElementWithFallback(buttons.get(buttons.size() - 1));
        }
    }

    // Klik satu representative tombol "Access your GLPI instance"
    public void clickRepresentativeAccessInstance() {
        List<WebElement> instanceBtns = driver
                .findElements(By.xpath("//a[contains(text(),'Access your GLPI instance')]"));
        if (!instanceBtns.isEmpty()) {
            clickElementWithFallback(instanceBtns.get(0)); // cukup tombol pertama
        }
    }

    // ================= TUTORIAL IFRAME =================
    public void clickTutorialIframe() {
        String mainWindow = driver.getWindowHandle();
        try {
            WebElement iframe = wait.until(ExpectedConditions.presenceOfElementLocated(
                    By.cssSelector("iframe[title*='Inventaire']")));
            driver.switchTo().frame(iframe);

            while (true) {
                List<WebElement> buttons = driver.findElements(By.tagName("a"));
                WebElement nextBtn = buttons.stream()
                        .filter(b -> b.isDisplayed() && b.isEnabled())
                        .findFirst()
                        .orElse(null);

                if (nextBtn == null)
                    break;

                clickElementWithFallback(nextBtn);
            }

        } catch (TimeoutException e) {
            System.out.println("Tutorial iframe tidak muncul");
        } finally {
            driver.switchTo().defaultContent();
            driver.switchTo().window(mainWindow);
        }
    }

    // ================= NEWSLETTER =================
    public void fillNewsletter(String email) {
        scrollToFooter();

        WebElement emailInput = wait.until(ExpectedConditions.visibilityOfElementLocated(newsletterEmail));
        highlight(emailInput);
        emailInput.clear();
        emailInput.sendKeys(email);

        // Checkbox optional
        By newsletterCheckbox = By.cssSelector("input[type='checkbox'][name*='checkbox']");
        try {
            WebElement checkbox = driver.findElement(newsletterCheckbox);
            if (!checkbox.isSelected()) {
                scrollIntoView(checkbox);
                highlight(checkbox);
                checkbox.click();
            }
        } catch (NoSuchElementException ignored) {
        }

        driver.findElement(newsletterSubmit).click();
        waitForPageLoad();
    }

    // ================= SOCIAL =================
    public void clickAllSocialMediaLinks() {
        scrollToFooter();
        String main = driver.getWindowHandle();
        List<WebElement> links = driver.findElements(socialLinks);
        for (WebElement link : links) {
            if (link.isDisplayed() && link.isEnabled()) {
                clickElementWithFallback(link);
                closeNewTab(main);
            }
        }
    }

    // ================= FULL FLOW DEMO =================
    public void testAllElementsComplete() {
        clickButtonByVisibleText("Start managing your first asset"); // hero
        clickButtonByVisibleText("Start Now"); // inventory
        clickRepresentativeAccessInstance(); // GLPI instance
        clickTutorialIframe(); // tutorial
        clickButtonByVisibleText("View All Testimonials"); // testimonial
        clickButtonByVisibleText("Explore Now"); // explore features
        clickLastButtonByText("Start Now"); // start incident management
        fillNewsletter("demo@test.com"); // newsletter
        clickAllSocialMediaLinks(); // social
    }

    // ================= HELPERS =================
    private void clickElementWithFallback(WebElement element) {
        scrollIntoView(element);
        highlight(element);
        try {
            wait.until(ExpectedConditions.elementToBeClickable(element)).click();
        } catch (ElementClickInterceptedException | TimeoutException e) {
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
        }
        waitForPageLoad();
    }

    // ================= HELPERS =================
    private void closeNewTab(String main) {
        try {
            for (String win : driver.getWindowHandles()) {
                if (!win.equals(main)) {
                    driver.switchTo().window(win);
                    // optional wait atau demoSleep
                    driver.close();
                }
            }
            driver.switchTo().window(main);
        } catch (Exception e) {
            driver.switchTo().window(main);
        }
    }

    private void clickButtonGeneric(By locator) {
        WebElement btn = wait.until(ExpectedConditions.elementToBeClickable(locator));
        clickElementWithFallback(btn);
    }

    private void waitForPageLoad() {
        wait.until(webDriver -> ((JavascriptExecutor) webDriver)
                .executeScript("return document.readyState").equals("complete"));
    }

}
