package com.proyek.pages.Tech;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class MainHeaderPage {

    private WebDriver driver;
    private WebDriverWait wait;
    private Actions actions;

    public MainHeaderPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        this.actions = new Actions(driver);
    }

    // ---------------- Logo ----------------
    public boolean isLogoVisible() {
        WebElement logoImg = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.cssSelector("div.logo_container picture img")));
        return logoImg.isDisplayed();
    }

    public String getLogoHref() {
        WebElement logoLink = driver.findElement(By.cssSelector("div.logo_container a"));
        return logoLink.getAttribute("href");
    }

    public void clickLogo() {
        WebElement logoLink = wait.until(ExpectedConditions.elementToBeClickable(
                By.cssSelector("div.logo_container a")));
        logoLink.click();
        waitForPageLoad();
    }

    // ---------------- Menu Desktop ----------------
    public List<WebElement> getTopMenuItems() {
        return wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(
                By.cssSelector("ul#top-menu > li > a")));
    }

    public void clickTopMenuItem(String menuText) {
        WebElement menu = driver.findElement(By.xpath("//ul[@id='top-menu']//a[normalize-space()='" + menuText + "']"));
        menu.click();
        waitForPageLoad();
    }

    public String getCurrentMenuItem() {
        WebElement current = driver.findElement(By.cssSelector("ul#top-menu li.current-menu-item > a"));
        return current.getText().trim();
    }

    // ---------------- Dropdown Sample ----------------
    public void hoverInfoMenu() {
        WebElement infoMenu = driver.findElement(By.cssSelector("ul#top-menu li.menu-item-has-children > a"));
        actions.moveToElement(infoMenu).perform();
    }

    public void clickDropdownStatus() {
        WebElement statusItem = wait.until(ExpectedConditions.elementToBeClickable(
                By.cssSelector(
                        "ul#top-menu li.menu-item-has-children ul.sub-menu li a[href*='status.glpi-network.cloud']")));
        statusItem.click();
        waitForPageLoad();
    }

    // ---------------- WPML Flags ----------------
    public List<WebElement> getWpmlFlags() {
        return driver.findElements(By.cssSelector("li.menu-item.wpml-ls-item img"));
    }

    public void clickWpmlFlag(String alt) {
        if (alt.equals("English")) {
            // English flag: langsung klik
            WebElement flagImg = driver.findElement(By.cssSelector("img.wpml-ls-flag[alt='English']"));
            WebElement flagLink = flagImg.findElement(By.xpath("./.."));
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", flagLink);
        } else {
            // sub-menu: hover parent
            WebElement parent = driver.findElement(By.cssSelector("li.menu-item-has-children"));
            new Actions(driver).moveToElement(parent).perform();

            // tunggu sub-menu muncul
            WebElement flagImg = new WebDriverWait(driver, Duration.ofSeconds(10))
                    .until(driver -> parent.findElement(By.cssSelector("img.wpml-ls-flag[alt='" + alt + "']")));

            WebElement flagLink = flagImg.findElement(By.xpath("./.."));

            // scroll ke view biar pasti klik
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", flagLink);

            // klik pake JS
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", flagLink);
        }
    }

    // ---------------- Mobile Menu ----------------
    public void openMobileMenu() {
        WebElement toggle = driver.findElement(By.cssSelector("span.mobile_menu_bar_toggle"));
        if (driver.findElement(By.cssSelector("div.mobile_nav")).getAttribute("class").contains("closed")) {
            toggle.click();
            wait.until(ExpectedConditions.attributeContains(
                    By.cssSelector("div.mobile_nav"), "class", "opened"));
        }
    }

    public List<WebElement> getMobileMenuItems() {
        return wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(
                By.cssSelector("ul#mobile_menu > li > a")));
    }

    public void clickMobileMenuItem(String menuText) {
        WebElement menu = driver
                .findElement(By.xpath("//ul[@id='mobile_menu']//a[normalize-space()='" + menuText + "']"));
        menu.click();
        waitForPageLoad();
    }

    // ---------------- Utility ----------------
    private void waitForPageLoad() {
        wait.until(driver -> ((String) ((org.openqa.selenium.JavascriptExecutor) driver)
                .executeScript("return document.readyState")).equals("complete"));
    }
}
