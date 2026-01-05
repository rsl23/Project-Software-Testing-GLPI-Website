package com.proyek.utils;

import java.time.Duration;

import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import io.github.bonigarcia.wdm.WebDriverManager;

public class DriverFactory {

    public static WebDriver createDriver() {
        WebDriverManager.chromedriver().setup();

        ChromeOptions options = new ChromeOptions();
        options.setPageLoadStrategy(PageLoadStrategy.EAGER);
        // Cek apakah Brave Browser terinstall
        String bravePath = "C:\\Program Files\\BraveSoftware\\Brave-Browser\\Application\\brave.exe";
        java.io.File braveFile = new java.io.File(bravePath);

        if (braveFile.exists()) {
            options.setBinary(bravePath);
            System.out.println("Using Brave Browser");
        } else {
            // Gunakan Chrome default (tidak perlu set binary)
            System.out.println("Using Chrome Browser");
        }

        // Performance options
        options.addArguments("--start-maximized");
        options.addArguments("--disable-notifications");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");

        ChromeDriver driver = new ChromeDriver(options);

        // Set reasonable timeouts
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(20));
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));

        return driver;
    }
}