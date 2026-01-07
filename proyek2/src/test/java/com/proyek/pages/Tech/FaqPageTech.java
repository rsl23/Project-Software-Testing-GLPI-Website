package com.proyek.pages.Tech;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class FaqPageTech {

    private WebDriver driver;
    private WebDriverWait wait;

    public FaqPageTech(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    // Tunggu halaman FAQ load penuh
    public void waitForPageLoad() {
        wait.until(driver -> ((JavascriptExecutor) driver)
                .executeScript("return document.readyState").equals("complete"));
    }

    // Tunggu tombol muncul
    public WebElement getButtonByText(String text) {
        return wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//a[contains(@class,'et_pb_button') and contains(text(),'" + text + "')]")));
    }

    // Klik tombol dan tunggu redirect
    public void clickButton(String text) {
        WebElement button = new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(driver -> {
                    try {
                        WebElement el = driver.findElement(
                                By.xpath("//a[contains(@class,'et_pb_button') and contains(text(),'" + text + "')]"));
                        return (el.isDisplayed()) ? el : null;
                    } catch (Exception e) {
                        return null;
                    }
                });

        // scroll ke tombol + klik pakai JS
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'});", button);
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", button);

        // jika tombol buka tab baru, jangan tunggu URL berubah
        String target = button.getAttribute("target");
        if (!"_blank".equals(target)) {
            // tunggu URL berubah untuk internal
            new WebDriverWait(driver, Duration.ofSeconds(10))
                    .until(d -> !d.getCurrentUrl().contains("/faq/"));
        } else {
            // eksternal: cukup delay sebentar
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ignored) {
            }
        }
    }

}
