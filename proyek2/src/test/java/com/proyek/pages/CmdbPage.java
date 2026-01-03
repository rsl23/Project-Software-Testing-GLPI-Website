package com.proyek.pages;

import com.proyek.base.BasePage;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;

public class CmdbPage extends BasePage {

    private final String cmdbUrl = "https://www.glpi-project.org/en/cmdb/";

    public CmdbPage(WebDriver driver) {
        super(driver);
    }

    // ================= URL =================
    public void open() {
        driver.get(cmdbUrl);
        acceptCookieIfPresent();
        demoSleep(1200);
    }

    // ================= HERO =================
    private final By heroTitle = By.tagName("h1");

    // ================= NEWSLETTER =================
    private final By newsletterEmail = By.cssSelector("input[type='email']");
    private final By newsletterSubmit = By.cssSelector("input[type='submit']");

    // ================= SOCIAL =================
    private final By socialLinks = By.cssSelector("footer a[target='_blank'][href^='http']");

    // ================= VISIBILITY =================
    public boolean isHeroVisible() {
        return isVisible(heroTitle);
    }

    // ================= ACTIONS DINAMIS =================
    public void clickButtonByVisibleText(String textContains) {
        String mainWindow = driver.getWindowHandle();
        try {
            WebElement btn = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//a[contains(normalize-space(.),'" + textContains + "')]")));
            scrollIntoView(btn);
            highlight(btn);
            demoSleep(600);
            try {
                btn.click();
            } catch (Exception e) {
                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", btn);
            }
            demoSleep(2000);
            closeNewTab(mainWindow);
        } catch (TimeoutException e) {
            System.out.println("Button not found: " + textContains);
        }
    }

    // klik tombol terakhir dengan teks tertentu (untuk Start Incident Management)
    public void clickLastButtonByText(String textContains) {
        String mainWindow = driver.getWindowHandle();
        try {
            List<WebElement> buttons = driver.findElements(
                    By.xpath("//a[contains(normalize-space(.),'" + textContains + "')]"));
            if (!buttons.isEmpty()) {
                WebElement btn = buttons.get(buttons.size() - 1); // ambil tombol terakhir
                scrollIntoView(btn);
                highlight(btn);
                demoSleep(600);
                try {
                    btn.click();
                } catch (Exception e) {
                    ((JavascriptExecutor) driver).executeScript("arguments[0].click();", btn);
                }
                demoSleep(2000);
                closeNewTab(mainWindow);
            }
        } catch (Exception e) {
            System.out.println("Button not found: " + textContains);
        }
    }

    // klik semua tombol "Access your GLPI instance"
    public void clickAllAccessGlpiInstances() {
        String mainWindow = driver.getWindowHandle();
        List<WebElement> instanceBtns = driver
                .findElements(By.xpath("//a[contains(text(),'Access your GLPI instance')]"));
        for (WebElement btn : instanceBtns) {
            try {
                if (btn.isDisplayed() && btn.isEnabled()) {
                    scrollIntoView(btn);
                    highlight(btn);
                    demoSleep(600);
                    btn.click();
                    demoSleep(2000);
                    closeNewTab(mainWindow);
                }
            } catch (Exception ignored) {
            }
        }
    }

    // ================= TUTORIAL IFRAME =================
    public void clickTutorialIframe() {
        String mainWindow = driver.getWindowHandle();
        try {
            WebElement iframe = wait.until(ExpectedConditions.presenceOfElementLocated(
                    By.cssSelector("iframe[title*='Inventaire']")));
            driver.switchTo().frame(iframe);
            demoSleep(800);

            while (true) {
                List<WebElement> buttons = driver.findElements(By.tagName("a"));

                // filter tombol yang bisa diklik
                WebElement nextBtn = null;
                for (WebElement btn : buttons) {
                    if (btn.isDisplayed() && btn.isEnabled()) {
                        nextBtn = btn;
                        break;
                    }
                }

                if (nextBtn == null)
                    break; // ga ada tombol lagi, keluar loop

                scrollIntoView(nextBtn);
                highlight(nextBtn);
                demoSleep(500);
                try {
                    nextBtn.click();
                } catch (Exception e) {
                    ((JavascriptExecutor) driver).executeScript("arguments[0].click();", nextBtn);
                }
                demoSleep(1000); // tunggu step berikutnya muncul
            }

        } catch (TimeoutException e) {
            System.out.println("Tutorial iframe tidak muncul");
        } finally {
            driver.switchTo().defaultContent();
            driver.switchTo().window(mainWindow);
        }
    }

    // ================= NEWSLETTER =================
    public void fillNewsletter(String email) {
        scrollToFooter();
        demoSleep(800);

        // isi email
        WebElement emailInput = wait.until(ExpectedConditions.visibilityOfElementLocated(newsletterEmail));
        highlight(emailInput);
        emailInput.clear();
        emailInput.sendKeys(email);
        demoSleep(600);

        // centang checkbox persetujuan jika belum dicentang
        By newsletterCheckbox = By.cssSelector("input[type='checkbox'][name*='checkbox']");
        try {
            WebElement checkbox = driver.findElement(newsletterCheckbox);
            if (!checkbox.isSelected()) {
                scrollIntoView(checkbox);
                highlight(checkbox);
                demoSleep(300);
                checkbox.click();
                demoSleep(300);
            }
        } catch (NoSuchElementException e) {
            System.out.println("Checkbox newsletter tidak ditemukan, lanjut submit");
        }

        // klik submit
        driver.findElement(newsletterSubmit).click();
        demoSleep(1000);
    }

    // ================= SOCIAL =================
    public void clickAllSocialMediaLinks() {
        scrollToFooter();
        demoSleep(800);
        String main = driver.getWindowHandle();
        List<WebElement> links = driver.findElements(socialLinks);
        for (WebElement link : links) {
            try {
                if (link.isDisplayed() && link.isEnabled()) {
                    scrollIntoView(link);
                    highlight(link);
                    demoSleep(600);
                    link.click();
                    demoSleep(2000);
                    closeNewTab(main);
                }
            } catch (Exception ignored) {
            }
        }
    }

    // ================= FULL DEMO FLOW =================
    public void testAllElementsComplete() {
        // urutan sesuai permintaan
        clickButtonByVisibleText("Start managing your first asset"); // hero top
        clickButtonByVisibleText("Start Now"); // Take Inventory Start Now
        clickAllAccessGlpiInstances(); // access GLPI instances
        clickTutorialIframe(); // tutorial iframe step by step
        clickButtonByVisibleText("View All Testimonials"); // testimonial
        clickButtonByVisibleText("Explore Now"); // explore features
        clickLastButtonByText("Start Now"); // Start Incident Management (tombol bawah)
        fillNewsletter("demo@test.com"); // newsletter
        clickAllSocialMediaLinks(); // social
    }

    // ================= HELPERS =================
    private void closeNewTab(String main) {
        try {
            for (String win : driver.getWindowHandles()) {
                if (!win.equals(main)) {
                    driver.switchTo().window(win);
                    demoSleep(1500);
                    driver.close();
                }
            }
            driver.switchTo().window(main);
            demoSleep(800);
        } catch (Exception e) {
            driver.switchTo().window(main);
        }
    }
}
