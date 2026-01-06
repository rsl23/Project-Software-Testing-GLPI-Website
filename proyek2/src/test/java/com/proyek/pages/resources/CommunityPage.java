    package com.proyek.pages.resources;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;
import java.util.Set;

public class CommunityPage {

    private WebDriver driver;
    private WebDriverWait wait;
    private JavascriptExecutor js;

    public CommunityPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        this.js = (JavascriptExecutor) driver;
    }

    // ===================== Forum =====================
    private By forumButton = By.cssSelector("#link_button-13-120");

    public void clickForum() {
        WebElement button = wait.until(ExpectedConditions.elementToBeClickable(forumButton));
        openLinkSafely(button);
    }

    // ===================== Social Media =====================
    private By socialLinks = By.cssSelector("#div_block-211-120 a.ct-link");

    public void clickSocialByName(String name) {
        List<WebElement> links = driver.findElements(socialLinks);
        for (WebElement link : links) {
            String href = link.getAttribute("href");
            if (href.toLowerCase().contains(name.toLowerCase())) {
                openLinkSafely(link);
                break;
            }
        }
    }

    // ===================== Documentation Slides =====================
    private By documentationSlides = By.cssSelector(".slide-documentation a.ct-link");

    public void openDocumentationByIndex(int index) {
        List<WebElement> docs = driver.findElements(documentationSlides);
        if (index >= 0 && index < docs.size()) {
            openLinkSafely(docs.get(index));
        }
    }

    // ===================== Video Tutorials =====================
    private By videoThumbnails = By.cssSelector(".perfmatters-lazy-youtube");

    public void openVideoByIndex(int index) {
        List<WebElement> videos = driver.findElements(videoThumbnails);
        if (index >= 0 && index < videos.size()) {
            openLinkSafely(videos.get(index));
        }
    }

    public void clickViewAllTestimonials() {
        WebElement button = driver.findElement(By.id("link_button-152-120"));
        openLinkSafely(button);
    }

    // ===================== Contact Form =====================
    private By contactName = By.name("your-name");
    private By contactFirstName = By.name("your-first-name");
    private By contactCompany = By.name("societe");
    private By contactCountry = By.name("pays");
    private By contactEmail = By.name("your-email");
    private By contactPhone = By.name("your-phone");
    private By contactMessage = By.name("your-message");
    private By contactSubmit = By.cssSelector("input.wpcf7-submit");

    public void fillContactForm(String name, String firstName, String company, String country,
            String email, String phone, String message) {
        waitAndSendKeys(contactName, name);
        waitAndSendKeys(contactFirstName, firstName);
        waitAndSendKeys(contactCompany, company);
        waitAndSendKeys(contactCountry, country);
        waitAndSendKeys(contactEmail, email);
        waitAndSendKeys(contactPhone, phone);
        waitAndSendKeys(contactMessage, message);
    }

    public void submitContactForm() {
        WebElement submit = wait.until(ExpectedConditions.elementToBeClickable(contactSubmit));
        scrollAndClickWithRetry(submit);
    }

    // ===================== Newsletter Form =====================
    private By newsletterEmail = By.name("email");
    private By newsletterSubmit = By.cssSelector("input.btn-nl");

    public void fillNewsletter(String email) {
        waitAndSendKeys(newsletterEmail, email);
    }

    public void submitNewsletter() {
        WebElement submit = wait.until(ExpectedConditions.elementToBeClickable(newsletterSubmit));
        scrollAndClickWithRetry(submit);
    }

    // ===================== Explore Now / Start Now / Newsletter CTA
    // =====================
    private By exploreNowButton = By.id("link_button-178-120");
    private By startNowButton = By.id("link_button-183-120");
    private By newsletterCTAInput = By.cssSelector("div.newsletter-form input[name='email']");
    private By newsletterCTASubmit = By.cssSelector("div.newsletter-form input.btn-nl");

    public void clickExploreNow() {
        WebElement button = wait.until(ExpectedConditions.elementToBeClickable(exploreNowButton));
        openLinkSafely(button);
    }

    public void clickStartNow() {
        WebElement button = wait.until(ExpectedConditions.elementToBeClickable(startNowButton));
        openLinkSafely(button);
    }

    public void fillAndSubmitNewsletterCTA(String email) {
        WebElement input = wait.until(ExpectedConditions.visibilityOfElementLocated(newsletterCTAInput));
        scrollIntoView(input);
        input.clear();
        input.sendKeys(email);

        WebElement submit = wait.until(ExpectedConditions.elementToBeClickable(newsletterCTASubmit));
        scrollAndClickWithRetry(submit);
    }

    // ===================== Utility Methods =====================
    private void scrollAndClickWithRetry(WebElement element) {
        int attempts = 0;
        while (attempts < 3) {
            try {
                js.executeScript("arguments[0].scrollIntoView({block:'center', behavior:'smooth'});", element);
                js.executeScript("arguments[0].click();", element);
                break;
            } catch (StaleElementReferenceException e) {
                System.out.println("[Warning] StaleElementReferenceException caught, retrying...");
                attempts++;
                sleep(500);
            }
        }
    }

    private void waitAndSendKeys(By locator, String text) {
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        scrollIntoView(element);
        element.clear();
        element.sendKeys(text);
    }

    private void scrollIntoView(WebElement element) {
        js.executeScript("arguments[0].scrollIntoView({block:'center', behavior:'smooth'});", element);
    }

    private void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException ignored) {
        }
    }

    // ===================== Open Link Safely (Tab Handling) =====================
    private void openLinkSafely(WebElement element) {
        String mainTab = driver.getWindowHandle();
        scrollAndClickWithRetry(element); // JS click aman
        sleep(1000);

        Set<String> allTabs = driver.getWindowHandles();
        if (allTabs.size() > 1) {
            for (String tab : allTabs) {
                if (!tab.equals(mainTab)) {
                    driver.switchTo().window(tab);
                    sleep(1000); // tunggu halaman load
                    System.out.println("Opened URL: " + driver.getCurrentUrl());
                    driver.close();
                }
            }
            driver.switchTo().window(mainTab);
        }
    }
}

