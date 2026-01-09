package com.proyek.tests.index;

import java.time.Duration;

import org.junit.jupiter.api.AfterAll;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.proyek.pages.auth.LoginPage;
import com.proyek.pages.index.MyAppLoginPage;
import com.proyek.pages.index.MyApplication;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class MyAppLoginTest {

    private WebDriver driver;
    private LoginPage loginPage;
    private MyApplication myApplicationPage;

    private final String USERNAME = "demoo@test.com";
    private final String PASSWORD = "NewPassword123!";

    @BeforeAll
    void setupAll() {
        driver = new ChromeDriver();

        // 1️⃣ LoginPage
        loginPage = new LoginPage(driver);
        loginPage.open();
        loginPage.fillCompleteLoginForm(USERNAME, PASSWORD);
        loginPage.clickSubmit();

        // 2️⃣ Tunggu sampai dashboard muncul
        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(webDriver -> webDriver.getCurrentUrl().contains("mainmenu=home"));

        // 3️⃣ MyApplicationPage
        myApplicationPage = new MyApplication(driver);
        myApplicationPage.open();

        // Pastikan di halaman MyApplication
        assertTrue(myApplicationPage.isOnMyApplicationPage(), "Tidak diarahkan ke halaman My Application");
    }

    @AfterAll
    void teardownAll() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    @DisplayName("Open and Verify Instance Link Application Page")
    void testOpenApplicationPage() {
        // Step 1: Simpan main window handle
        String mainWindow = driver.getWindowHandle();
        
        // Step 2: Klik instance link (akan membuka tab baru)
        myApplicationPage.clickInstanceLink();
        
        // Step 3: Tunggu tab baru muncul
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.numberOfWindowsToBe(2));
        
        // Step 4: Switch ke tab baru
        String newWindow = driver.getWindowHandles().stream()
                .filter(handle -> !handle.equals(mainWindow))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Tab baru tidak muncul"));
        
        driver.switchTo().window(newWindow);
        
        // Step 5: Tunggu halaman login GLPI app load
        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("login_name")));
        
        // Step 6: Create MyAppLoginPage dan isi form
        MyAppLoginPage appLoginPage = new MyAppLoginPage(driver);
        
        // Verifikasi form login tampil
        assertTrue(appLoginPage.isLoginNameFieldVisible(), "Login name field tidak terlihat");
        assertTrue(appLoginPage.isLoginPasswordFieldVisible(), "Login password field tidak terlihat");
        assertTrue(appLoginPage.isSignInButtonVisible(), "Sign in button tidak terlihat");

        // Step 7: Login ke GLPI app
        appLoginPage.fillLoginName("demooo@test.com");
        appLoginPage.fillLoginPassword("NewPassword123!");
        appLoginPage.clickSignIn();
        
        // Step 8: Tunggu redirect atau error message muncul
        WebDriverWait longWait = new WebDriverWait(driver, Duration.ofSeconds(20));
        try {
            longWait.until(webDriver -> {
                String url = webDriver.getCurrentUrl();
                
                // Cek apakah ada error message (bug case)
                try {
                    WebElement errorDiv = webDriver.findElement(By.xpath("//div[contains(text(), 'The requested item has not been found')]"));
                    if (errorDiv.isDisplayed()) {
                        return true; // Error message ditemukan, stop waiting
                    }
                } catch (Exception e) {
                    // Error message tidak ada, lanjutkan cek redirect
                }
                
                // Cek normal redirect (success case)
                return !url.equals("about:blank") && !url.contains("login.php");
            });
        } catch (Exception e) {
            System.out.println("⚠️ Timeout waiting for redirect or error message. Current URL: " + driver.getCurrentUrl());
            // Don't throw, continue to bug detection
        }
        
        // Step 9: Verifikasi apakah ada bug - error message "The requested item has not been found"
        String currentUrl = driver.getCurrentUrl();
        System.out.println("Current URL after login: " + currentUrl);
        
        try {
            // Cek apakah ada error message
            WebElement errorDiv = driver.findElement(By.xpath("//div[contains(text(), 'The requested item has not been found')]"));
            
            if (errorDiv.isDisplayed()) {
                // BUG DITEMUKAN!
                System.out.println("⚠️ BUG DETECTED! Error message muncul setelah login:");
                System.out.println("   Error: 'The requested item has not been found'");
                System.out.println("   URL: " + currentUrl);
                
                // Step 10: Klik link "Return to previous page" untuk kembali ke login page
                try {
                    WebElement returnLink = driver.findElement(By.xpath("//a[contains(@href, 'glpi-network.cloud') and contains(text(), 'Return to previous page')]"));
                    if (returnLink.isDisplayed()) {
                        System.out.println("   Klik 'Return to previous page' untuk kembali ke login page");
                        returnLink.click();
                        
                        // Tunggu kembali ke login page
                        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("login_name")));
                        
                        String loginPageUrl = driver.getCurrentUrl();
                        System.out.println("   Berhasil kembali ke login page: " + loginPageUrl);
                        
                        // Verifikasi berada di login page GLPI app (tidak close window)
                        assertTrue(loginPageUrl.contains("glpi-network.cloud"),
                            "Tidak berhasil kembali ke login page. Current URL: " + loginPageUrl);
                    }
                } catch (Exception e) {
                    System.out.println("   Error saat klik Return to previous page: " + e.getMessage());
                }
                
                // Assert gagal untuk menandai ini sebagai bug
                assertTrue(false, 
                    "BUG: Login berhasil tetapi halaman menampilkan error 'The requested item has not been found'. " +
                    "URL: " + currentUrl + ". " +
                    "Expected: Redirect ke dashboard/central page. " +
                    "Actual: Error page dengan message 'The requested item has not been found'.");
            }
        } catch (Exception e) {
            // Error message tidak ditemukan, artinya tidak ada bug
            System.out.println("✅ No error message found - login successful");
        }
        
        // Step 11: Verifikasi login berhasil (jika tidak ada bug)
        assertTrue(currentUrl.contains("glpi-network.cloud") && 
                   currentUrl.contains("/front/"),
                "Login ke GLPI app gagal atau redirect tidak sesuai. Current URL: " + currentUrl);
        
        System.out.println("Berhasil login ke GLPI app: " + currentUrl);
        
        // Step 12: Tutup tab baru dan kembali ke main window
        driver.close();
        driver.switchTo().window(mainWindow);
        
        // Verifikasi kembali ke main window
        assertTrue(driver.getCurrentUrl().contains("mode=instances"), 
            "Tidak kembali ke halaman My Application");
    }

    @Test
    @DisplayName("Click Forgot Password and Verify Error")
    void testForgotPasswordError() {
        // CATATAN: Test ini akan dijalankan setelah testOpenApplicationPage
        // Jika testOpenApplicationPage menemukan bug dan klik "Return to previous page",
        // maka kita sudah berada di login page GLPI app (tab baru masih terbuka)
        // Jika testOpenApplicationPage tidak menemukan bug (login sukses),
        // test ini akan gagal karena tidak ada tab kedua
        
        // Cek jumlah window
        if (driver.getWindowHandles().size() < 2) {
            System.out.println("⚠️ SKIPPED: Test ini memerlukan tab GLPI app terbuka dari test sebelumnya");
            System.out.println("   Ini terjadi jika bug 'The requested item has not been found' tidak muncul.");
            return; // Skip test ini
        }
        
        // Step 1: Get main window handle
        String mainWindow = driver.getWindowHandles().stream()
                .filter(handle -> {
                    driver.switchTo().window(handle);
                    return driver.getCurrentUrl().contains("mode=instances");
                })
                .findFirst()
                .orElse(null);
        
        // Step 2: Switch ke tab GLPI app (yang masih terbuka dari test sebelumnya)
        String glpiWindow = driver.getWindowHandles().stream()
                .filter(handle -> !handle.equals(mainWindow))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Tab GLPI app tidak ditemukan"));
        
        driver.switchTo().window(glpiWindow);
        
        // Step 3: Verifikasi berada di login page
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("login_name")));
        
        System.out.println("Current URL sebelum klik Forgot Password: " + driver.getCurrentUrl());
        
        // Step 4: Create MyAppLoginPage
        MyAppLoginPage appLoginPage = new MyAppLoginPage(driver);
        
        // Step 5: Verifikasi Forgot Password link tampil
        assertTrue(appLoginPage.isForgotPasswordLinkVisible(), "Forgot Password link tidak terlihat");
        
        // Step 6: Klik Forgot Password link
        appLoginPage.clickForgotPassword();
        
        // Step 7: Tunggu redirect ke lostpassword.php atau error message muncul
        WebDriverWait longWait = new WebDriverWait(driver, Duration.ofSeconds(20));
        try {
            longWait.until(webDriver -> {
                String url = webDriver.getCurrentUrl();
                
                // Cek apakah ada error message (bug case)
                try {
                    WebElement errorDiv = webDriver.findElement(By.xpath("//div[contains(text(), 'The requested item has not been found')]"));
                    if (errorDiv.isDisplayed()) {
                        return true; // Error message ditemukan, stop waiting
                    }
                } catch (Exception e) {
                    // Error message tidak ada, lanjutkan cek redirect
                }
                
                // Cek normal redirect ke lostpassword page
                return url.contains("lostpassword.php");
            });
        } catch (Exception e) {
            System.out.println("⚠️ Timeout waiting for lostpassword page or error message. Current URL: " + driver.getCurrentUrl());
            // Don't throw, continue to bug detection
        }
        
        // Step 8: Verifikasi apakah ada bug - error message "The requested item has not been found"
        String currentUrl = driver.getCurrentUrl();
        System.out.println("Current URL after clicking Forgot Password: " + currentUrl);
        
        try {
            // Cek apakah ada error message
            WebElement errorDiv = driver.findElement(By.xpath("//div[contains(text(), 'The requested item has not been found')]"));
            
            if (errorDiv.isDisplayed()) {
                // BUG DITEMUKAN!
                System.out.println("⚠️ BUG DETECTED! Error message muncul setelah klik Forgot Password:");
                System.out.println("   Error: 'The requested item has not been found'");
                System.out.println("   URL: " + currentUrl);
                
                // Cek juga apakah ada link "Return to previous page"
                try {
                    WebElement returnLink = driver.findElement(By.xpath("//a[contains(@href, 'glpi-network.cloud') and contains(text(), 'Return to previous page')]"));
                    if (returnLink.isDisplayed()) {
                        System.out.println("   Link 'Return to previous page' ditemukan");
                    }
                } catch (Exception e) {
                    // Link tidak ditemukan
                }
                
                // Assert gagal untuk menandai ini sebagai bug
                assertTrue(false, 
                    "BUG: Forgot Password link berhasil diklik tetapi halaman menampilkan error 'The requested item has not been found'. " +
                    "URL: " + currentUrl + ". " +
                    "Expected: Redirect ke lostpassword.php page dengan form reset password. " +
                    "Actual: Error page dengan message 'The requested item has not been found'.");
            }
        } catch (Exception e) {
            // Error message tidak ditemukan, artinya tidak ada bug
            System.out.println("✅ No error message found - Forgot Password page loaded successfully");
        }
        
        // Step 9: Verifikasi halaman lostpassword berhasil dimuat (jika tidak ada bug)
        assertTrue(currentUrl.contains("glpi-network.cloud") && 
                   currentUrl.contains("lostpassword.php"),
                "Forgot Password tidak redirect ke halaman yang benar. Current URL: " + currentUrl);
        
        System.out.println("Berhasil membuka halaman Forgot Password: " + currentUrl);
        
        // Step 10: Tutup tab GLPI app dan kembali ke main window
        driver.close();
        driver.switchTo().window(mainWindow);
        
        // Verifikasi kembali ke main window
        assertTrue(driver.getCurrentUrl().contains("mode=instances"), 
            "Tidak kembali ke halaman My Application");
    }

}
