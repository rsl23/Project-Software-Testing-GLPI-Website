package com.proyek.tests.index;

import com.proyek.pages.index.MyApplication;
import com.proyek.pages.auth.LoginPage;
import com.proyek.pages.index.DashboardPage;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)

public class MyApplicationTest {

    private WebDriver driver;
    private LoginPage loginPage;
    private DashboardPage dashboardPage;
    private MyApplication myApplicationPage;

    private final String USERNAME = "testdemo222@gmail.com";
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
        new org.openqa.selenium.support.ui.WebDriverWait(driver, java.time.Duration.ofSeconds(10))
                .until(webDriver -> webDriver.getCurrentUrl().contains("mainmenu=home"));

        // 3️⃣ DashboardPage
        dashboardPage = new DashboardPage(driver);

        // 5️⃣ MyApplicationPage
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

    // ================= VISIBILITY TESTS =================
    @Test
    @Order(1)
    @DisplayName("Check visibility of main elements")
    void testElementsVisibility() {
        assertTrue(myApplicationPage.isInstanceLinkVisible(), "Instance link tidak terlihat");
        assertTrue(myApplicationPage.isResourcesTabVisible(), "Resources tab tidak terlihat");
        assertTrue(myApplicationPage.isDomainTabVisible(), "Domain tab tidak terlihat");
        assertTrue(myApplicationPage.isCloseInstanceTabVisible(), "Close instance tab tidak terlihat");
        assertTrue(myApplicationPage.isUpgradePlanLinkVisible(), "Upgrade plan link tidak terlihat");
        assertTrue(myApplicationPage.isAddPaymentModeLinkVisible(), "Add payment mode link tidak terlihat");
        assertTrue(myApplicationPage.isMoreOptionsDivVisible(), "More options div tidak terlihat");
        assertTrue(myApplicationPage.isAddAnotherInstanceLinkVisible(), "Add another instance link tidak terlihat");
    }

    // ================= LINKS & TABS TEST =================
    @Test
    @Order(3)
    @DisplayName("Click instance link")
    void testClickInstanceLink() {
        myApplicationPage.clickInstanceLink();
        // assert URL mengandung instance
        assertTrue(
                driver.getCurrentUrl().contains("mode=instances") || driver.getCurrentUrl().contains("blankinstance"),
                "URL tidak mengarah ke instance baru");
    }

    @Test
    @Order(2)
    @DisplayName("Click Resources tab")
    void testClickResourcesTab() {
        // klik tab Resources
        myApplicationPage.clickResourcesTab();

        // assert konten resources muncul
        WebElement tabContent = driver.findElement(By.cssSelector("div.areaforresources"));
        assertTrue(tabContent.isDisplayed(), "Konten Resources tidak muncul");
    }

    @Test
    @Order(6)
    @DisplayName("Click Domain tab")
    void testClickDomainTab() {
        // Klik tab Domain
        myApplicationPage.clickDomainTab();

        // Ambil contractId dari halaman (dari hidden input di form)
        WebElement domainTab = new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.elementToBeClickable(
                        By.xpath("//a[contains(text(),'Domain')]")));

        domainTab.click();

        // Tunggu konten tab muncul (class tab-pane active)
        WebElement tabContent = new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.visibilityOfElementLocated(
                        By.cssSelector("div.tab-pane.active")));

        assertTrue(tabContent.isDisplayed(), "Konten Domain tidak muncul setelah klik tab");

        // Opsional: cek bahwa teks Domain ada di konten
        String text = tabContent.getText().toLowerCase();
        assertTrue(text.contains("url") || text.contains("domain"), "Konten Domain tidak sesuai");

        System.out.println("Domain tab berhasil diklik dan konten muncul");
    }

    @Test
    @Order(7)
    @DisplayName("Click Close Instance tab")
    void testClickCloseInstanceTab() {
        // ambil contractId dari halaman
        String contractId = driver.findElement(By.name("contractid")).getAttribute("value");

        // klik tab Close Instance
        WebElement tab = driver.findElement(By.id("a_tab_danger_" + contractId));
        tab.click();

        // tunggu konten tab muncul, berdasarkan input text unik di dalamnya
        By tabContentLocator = By.cssSelector("input.urlofinstancetodestroy");

        WebElement tabContent = new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.visibilityOfElementLocated(tabContentLocator));

        assertTrue(tabContent.isDisplayed(), "Konten Close Instance tidak muncul");
    }

    @Test
    @Order(5)
    @DisplayName("Click Upgrade Plan link")
    void testClickUpgradePlan() {
        myApplicationPage.clickUpgradePlan();
        // assert URL berubah ke change plan
        assertTrue(driver.getCurrentUrl().contains("action=changeplan"),
                "Upgrade plan tidak diarahkan ke halaman yang benar");
    }

    @Test
    @Order(10)
    @DisplayName("Click Add Payment Mode link")
    void testClickAddPaymentMode() {
        myApplicationPage.clickAddPaymentMode();
        // assert URL berubah ke register payment mode
        assertTrue(driver.getCurrentUrl().contains("mode=registerpaymentmode"),
                "Add Payment Mode link tidak diarahkan ke halaman yang benar");
    }

    @Test
    @Order(4)
    @DisplayName("Click More Options div")
    void testClickMoreOptions() {
        myApplicationPage.clickMoreOptions();

        // tunggu opsi muncul, karena mungkin ada efek render dengan delay
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement optionsDiv = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div.divdolibarrmoreoptions")));

        assertTrue(optionsDiv.isDisplayed(), "More options tidak aktif setelah diklik");
    }

    @Test
    @Order(8)
    @DisplayName("Click Add Another Instance link")
    void testClickAddAnotherInstance() {
        myApplicationPage.clickAddAnotherInstance();
        // assert form create instance muncul
        WebElement form = driver.findElement(By.cssSelector("div.portlet.light section#selectDomain"));
        assertTrue(form.isDisplayed(), "Form create instance tidak muncul setelah klik Add Another Instance");
    }

    // ================= FORM FILLING TEST =================
    @Test
    @Order(9)
    @DisplayName("Fill new instance form")
    void testFillNewInstanceForm() {
        // myApplicationPage.clickAddAnotherInstance();
        myApplicationPage.fillNewInstanceForm(
                "GLPI 11", // service sesuai dropdown
                "Password123!", // password
                "mynewinstance", // subdomain
                ".us5.glpi-network.cloud" // TLD valid
        );
        myApplicationPage.clickCreate();

        // tunggu instalasi selesai
        myApplicationPage.waitForInstallationComplete();

        // ambil link instance baru
        WebElement instanceLink = driver.findElement(By.cssSelector("p.well a"));
        String instanceUrl = instanceLink.getAttribute("href");

        assertNotNull(instanceUrl, "URL instance baru tidak ditemukan");
        assertTrue(instanceUrl.contains("glpi-network.cloud"), "URL instance baru tidak valid");

        System.out.println("Instance berhasil dibuat: " + instanceUrl);
        myApplicationPage.open();
    }

}
