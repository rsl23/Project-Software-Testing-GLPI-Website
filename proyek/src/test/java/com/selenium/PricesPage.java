package com.selenium;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import io.qameta.allure.*;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.time.Duration;

/**
 * Testing halaman Prices GLPI
 * - Cloud & Self Hosted
 * - Delay 1000 ms
 * - Screenshot
 */
public class PricesPage {

    WebDriver driver;

    @BeforeClass
    public void setup() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();

        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

        driver.get("https://www.glpi-project.org/");
        delay();
    }

    // =========================
    // DELAY 1000 ms
    // =========================
    private void delay() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    // =========================
    // SCREENSHOT
    // =========================
    private void takeScreenshot(String fileName) {
        File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        File dest = new File("screenshots/" + fileName + ".png");

        try {
            dest.getParentFile().mkdirs();
            Files.copy(src.toPath(), dest.toPath());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // =====================================
    // TEST: PRICES - CLOUD & SELF HOSTED
    // =====================================
    @Test
    public void testPricesCloudAndSelfHosted() {

        // Masuk ke Prices
        driver.findElement(By.linkText("Prices")).click();
        delay();

        // ======================
        // CLOUD (Default)
        // ======================
        Assert.assertTrue(
                driver.getPageSource().toLowerCase().contains("cloud"),
                "Mode Cloud tidak tampil"
        );

        Assert.assertTrue(
                driver.getPageSource().contains("€")
                        || driver.getPageSource().contains("$"),
                "Harga Cloud tidak ditemukan"
        );

        takeScreenshot("Prices_Cloud");
        delay();

        // ======================
        // SWITCH TO SELF-HOSTED
        // ======================
        driver.findElement(By.id("div_block-121-432270")).click();
        delay();

        // Validasi Self Hosted aktif
        Assert.assertTrue(
                driver.getPageSource().toLowerCase().contains("self"),
                "Mode Self Hosted tidak tampil"
        );

        // Validasi fitur Self Hosted
        Assert.assertTrue(
                driver.getPageSource().toLowerCase().contains("support")
                        || driver.getPageSource().toLowerCase().contains("license")
                        || driver.getPageSource().toLowerCase().contains("on-premise"),
                "Fitur Self Hosted tidak ditemukan"
        );

        takeScreenshot("Prices_Self_Hosted");
        delay();
    }

    @AfterClass
    public void tearDown() {
        delay();
        driver.quit();
    }
}
