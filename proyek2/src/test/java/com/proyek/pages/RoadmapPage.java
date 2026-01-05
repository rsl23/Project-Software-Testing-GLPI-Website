package com.proyek.pages;

import com.proyek.base.BasePage;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class RoadmapPage extends BasePage {

    private final String roadmapUrl = "https://www.glpi-project.org/en/roadmap/";

    public RoadmapPage(WebDriver driver) {
        super(driver);
    }

    public List<String> getVisibleCardCategories() {
        List<WebElement> cards = driver.findElements(By.cssSelector("#backlog-results .card.repeater-item"));
        List<String> categories = new ArrayList<>();
        for (WebElement card : cards) {
            String category = card.findElement(By.cssSelector("div.ct-text-block span")).getText();
            categories.add(category);
        }
        return categories;
    }

    // ================= URL =================
    public void open() {
        driver.get(roadmapUrl);
        driver.manage().window().maximize();
        acceptCookieIfPresent();
    }

    // ================= LOCATORS =================
    private final By contributeButton = By.id("link_text-5012-435430");
    private final By allCards = By.cssSelector(".repeater-item"); // semua card
    private final By cardCategory = By.cssSelector(".txt-roadmap-button.color-primary");

    // ================= ACTIONS =================
    public void clickContributeButton() {
        WebElement btn = wait.until(ExpectedConditions.elementToBeClickable(contributeButton));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", btn);
        highlight(btn);
        btn.click();

        // tunggu URL berubah sesuai link
        wait.until(ExpectedConditions.urlToBe("https://www.glpi-project.org/fr/contact/"));
    }

    public java.util.List<WebElement> getAllCards() {
        return driver.findElements(allCards);
    }

    public String getCardCategory(WebElement card) {
        try {
            List<WebElement> spans = card.findElements(By.tagName("span"));

            // span terakhir biasanya kategori
            if (!spans.isEmpty()) {
                return spans.get(spans.size() - 1).getText().trim();
            }

            return "";
        } catch (Exception e) {
            return "";
        }
    }

    public WebElement getCheckbox(String name) {
        return driver.findElement(By.id(name));
    }

    public void clickCheckbox(String checkboxId) {
        WebElement label = wait.until(ExpectedConditions.elementToBeClickable(
                By.cssSelector("label[for='" + checkboxId + "']")));
        ((JavascriptExecutor) driver)
                .executeScript("arguments[0].scrollIntoView({block:'center'});", label);
        highlight(label);
        label.click();
    }

}
