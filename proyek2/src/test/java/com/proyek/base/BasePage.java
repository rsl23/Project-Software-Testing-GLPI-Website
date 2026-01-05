package com.proyek.base;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class BasePage {

    protected WebDriver driver;
    protected WebDriverWait wait;

    // MODE DEMO (true = kelihatan pelan, false = ngebut)
    protected boolean DEMO_MODE = true;
    
    // Delay multiplier (1.0 = normal, 2.0 = 2x lebih lambat)
    protected double DELAY_MULTIPLIER = 1.5;

    public BasePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    // ================= CLICK HELPER =================
    protected void waitAndClick(By locator) {
        WebElement element = wait.until(ExpectedConditions.elementToBeClickable(locator));

        scrollIntoView(element);
        highlight(element);
        demoSleep(600);

        element.click();
        demoSleep(700);
    }

    // ================= VISIBLE HELPER =================
    protected boolean isVisible(By locator) {
        try {
            WebElement el = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
            scrollIntoView(el);
            highlight(el);
            demoSleep(500);
            return el.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    // ================= SCROLL =================
    protected void scrollIntoView(WebElement element) {
        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].scrollIntoView({block:'center', behavior:'smooth'});",
                element);
        demoSleep(400);
    }

    protected void scrollBy(int y) {
        ((JavascriptExecutor) driver)
                .executeScript("window.scrollBy(0, arguments[0])", y);
        demoSleep(500);
    }

    protected void scrollToFooter() {
        ((JavascriptExecutor) driver)
                .executeScript("window.scrollTo(0, document.body.scrollHeight)");
        demoSleep(800);
    }

    protected void scrollToTop() {
        ((JavascriptExecutor) driver)
                .executeScript("window.scrollTo(0, 0)");
        demoSleep(600);
    }

    // ================= HIGHLIGHT =================
    protected void highlight(WebElement element) {
        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].style.border='3px solid red'", element);
    }

    // ================= COOKIE =================
    protected void acceptCookieIfPresent() {
        try {
            By cookieBtn = By.xpath("//button[contains(text(),'Accept') or contains(text(),'accept')]");
            WebElement btn = wait.until(ExpectedConditions.elementToBeClickable(cookieBtn));
            highlight(btn);
            demoSleep(400);
            btn.click();
            demoSleep(500);
        } catch (Exception ignored) {
            // cookie tidak muncul → lanjut
        }
    }

    // ================= SLEEP =================
    protected void demoSleep(int ms) {
        if (DEMO_MODE) {
            try {
                // Delay dengan multiplier untuk memperlambat test
                Thread.sleep((long)(ms * DELAY_MULTIPLIER));
            } catch (InterruptedException ignored) {
            }
        }
    }

    protected void type(By locator, String text) {
        WebElement el = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        scrollIntoView(el);
        highlight(el);
        el.clear();
        el.sendKeys(text);
        demoSleep(400);
    }
}
