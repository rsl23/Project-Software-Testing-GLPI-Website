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
 * Testing halaman Company GLPI
 * - Sub halaman: Teclib, R&D, Clients, Employments
 * - Validasi informasi perusahaan
 * - Delay 1000 ms
 * - Screenshot
 */
public class CompanyPage {

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
    // TEST 1: COMPANY - TECLIB
    // =====================================
    @Test(priority = 1)
    public void testCompanyTeclib() {
        driver.findElement(By.linkText("Company")).click();
        delay();
        driver.findElement(By.id("text_block-897-10")).click();
        delay();

        Assert.assertTrue(
                driver.findElement(By.tagName("h1")).isDisplayed(),
                "Informasi Teclib tidak tampil"
        );
        Assert.assertEquals(
            driver.findElement(By.id("headline-3-433254")).getText(),
            "Teclib"
        );

        takeScreenshot("Company_Teclib");
        delay();
    }

    // =====================================
    // TEST 2: COMPANY - R&D
    // =====================================
    @Test(priority = 2)
    public void testCompanyRnD() {
        driver.findElement(By.linkText("Company")).click();
        delay();
        driver.findElement(By.id("text_block-902-10")).click();
        delay();

        Assert.assertTrue(
                driver.getPageSource().toLowerCase().contains("research")
                        || driver.getPageSource().toLowerCase().contains("development"),
                "Informasi R&D tidak ditemukan"
        );
        Assert.assertEquals(
            driver.findElement(By.id("headline-5-433330")).getText(),
            "Research & development"
        );

        takeScreenshot("Company_RnD");
        delay();
    }

    // =====================================
    // TEST 3: COMPANY - CLIENTS
    // =====================================
    @Test(priority = 3)
    public void testCompanyClients() {
        driver.findElement(By.linkText("Company")).click();
        delay();
        driver.findElement(By.id("text_block-908-10")).click();
        delay();

        Assert.assertTrue(
                driver.getPageSource().toLowerCase().contains("client")
                        || driver.getPageSource().toLowerCase().contains("customer"),
                "Informasi Clients tidak ditemukan"
        );
        Assert.assertEquals(
            driver.findElement(By.id("headline-5-432366")).getText(),
            "Customers"
        );

        takeScreenshot("Company_Clients");
        delay();
    }

    // =====================================
    // TEST 4: COMPANY - EMPLOYMENTS
    // =====================================
    @Test(priority = 4)
    public void testCompanyEmployments() {
        driver.findElement(By.linkText("Company")).click();
        delay();

        // Simpan window utama
        String mainWindow = driver.getWindowHandle();

        // Klik Employments (buka tab baru)
        driver.findElement(By.id("text_block-913-10")).click();
        delay();

        // Pindah ke window baru
        for (String windowHandle : driver.getWindowHandles()) {
            if (!windowHandle.equals(mainWindow)) {
                driver.switchTo().window(windowHandle);
                break;
            }
        }

        delay();

        // Validasi halaman Employments
        Assert.assertTrue(
                driver.getPageSource().toLowerCase().contains("job")
                        || driver.getPageSource().toLowerCase().contains("career"),
                "Informasi Employments tidak ditemukan"
        );

        // Screenshot halaman BARU
        takeScreenshot("Company_Employments_New_Tab");
        delay();

        // Tutup tab baru dan kembali ke tab utama
        driver.close();
        driver.switchTo().window(mainWindow);
    }


    @AfterClass
    public void tearDown() {
        delay();
        driver.quit();
    }
}
