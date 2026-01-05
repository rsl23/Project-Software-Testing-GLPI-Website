package com.selenium;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;

/**
 * Testing halaman Product & Features GLPI
 * Delay 1000ms + Screenshot setiap halaman
 */
public class ProductPage {

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
    // DELAY 1000ms
    // =========================
    private void delay() {
        try {
            Thread.sleep(1000); // 1000 ms
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    // =========================
    // SCREENSHOT METHOD
    // =========================
    private void takeScreenshot(String fileName) {
        File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        File dest = new File("screenshots/" + fileName + ".png");

        try {
            dest.getParentFile().mkdirs(); // create folder if not exists
            Files.copy(src.toPath(), dest.toPath());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // =====================================
    // TEST 1: PRODUCT - HELPDESK
    // =====================================
    @Test(priority = 1)
    public void testProductHelpdesk() {
        driver.findElement(By.linkText("Product")).click();
        delay();
        driver.findElement(By.id("text_block-317-10")).click();
        delay();

        Assert.assertTrue(driver.findElement(By.id("headline-6-57")).isDisplayed());
        Assert.assertEquals(
            driver.findElement(By.id("link_text-11-57")).getText(),
            "Open your first ticket"
        );
        // Assert.assertTrue(driver.findElement(By.tagName("footer")).isDisplayed());

        takeScreenshot("Product_Helpdesk");
        delay();
    }

    // =====================================
    // TEST 2: PRODUCT - FINANCIAL MANAGEMENT
    // =====================================
    @Test(priority = 2)
    public void testProductFinancialManagement() {
        driver.findElement(By.linkText("Product")).click();
        delay();
        driver.findElement(By.id("text_block-266-10")).click();
        delay();

        Assert.assertTrue(driver.findElement(By.id("headline-297-99")).isDisplayed());
        Assert.assertEquals(
            driver.findElement(By.id("link_text-11-57")).getText(),
            "Track your expenses"
        );
        // Assert.assertTrue(driver.findElement(By.tagName("footer")).isDisplayed());

        takeScreenshot("Product_Financial_Management");
        delay();
    }

    // =====================================
    // TEST 3: PRODUCT - CMDB
    // =====================================
    @Test(priority = 3)
    public void testProductCMDB() {
        driver.findElement(By.linkText("Product")).click();
        delay();
        driver.findElement(By.id("text_block-277-10")).click();
        delay();

        Assert.assertTrue(driver.findElement(By.id("headline-6-57")).isDisplayed());
        Assert.assertEquals(
            driver.findElement(By.id("link_text-11-57")).getText(),
            "Start managing your first asset"
        );
        // Assert.assertTrue(driver.findElement(By.tagName("footer")).isDisplayed());

        takeScreenshot("Product_CMDB");
        delay();
    }

    // =====================================
    // TEST 4: PRODUCT - ADMINISTRATION
    // =====================================
    @Test(priority = 4)
    public void testProductAdministration() {
        driver.findElement(By.linkText("Product")).click();
        delay();
        driver.findElement(By.id("text_block-282-10")).click();
        delay();

        Assert.assertTrue(driver.findElement(By.id("headline-6-57")).isDisplayed());
        Assert.assertEquals(
            driver.findElement(By.id("link_text-11-57")).getText(),
            "Manage your users"
        );
        // Assert.assertTrue(driver.findElement(By.tagName("footer")).isDisplayed());

        takeScreenshot("Product_Administration");
        delay();
    }

    @AfterClass
    public void tearDown() {
        delay();
        driver.quit();
    }
}
