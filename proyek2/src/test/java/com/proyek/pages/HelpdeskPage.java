package com.proyek.pages;

import com.proyek.base.BasePage;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;
import java.util.Map;

public class HelpdeskPage extends BasePage {

    private final String helpdeskUrl = "https://www.glpi-project.org/en/helpdesk-software/";

    public HelpdeskPage(WebDriver driver) {
        super(driver);
    }

    // ================= URL =================
    public void open() {
        driver.get(helpdeskUrl);
        acceptCookieIfPresent();
        demoSleep(1200);
    }

    // ================= HERO =================
    private final By heroTitle = By.tagName("h1");

    private final Map<String, String> heroButtons = Map.of(
            "firstTicket", "open your first ticket",
            "startNowHero", "start now",
            "accessInstance", "access my glpi instance",
            "openMyInstance", "open my glpi instance");

    // ================= TUTORIAL =================
    private final List<String> tutorialButtonsText = List.of(
            "Discover the Forms", "Next Step", "Finish");

    // ================= TESTIMONIAL / CTA =================
    private final Map<String, String> ctaButtons = Map.of(
            "viewTestimonials", "View All Testimonials",
            "exploreNow", "Explore Now");

    // ================= FEATURES =================
    private final By startNowFeaturesBtn = By.cssSelector(
            "a.ct-link-button.btn-primary[href*='register.php']:not(.btn-m)");

    // ================= NEWSLETTER =================
    private final By newsletterEmail = By.cssSelector("input[type='email']");
    private final By newsletterSubmit = By.cssSelector("input[type='submit']");

    // ================= SOCIAL =================
    private final By socialLinks = By.cssSelector("footer a[target='_blank'][href^='http']");

    // ================= VISIBILITY =================
    public boolean isHeroVisible() {
        return isVisible(heroTitle);
    }

    // ================= ACTIONS GENERIK =================
    private void clickButtonByText(String buttonText) {
        String main = driver.getWindowHandle();
        try {
            WebElement el = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//a[contains(translate(.,'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqrstuvwxyz'),'"
                            + buttonText.toLowerCase() + "')]")));
            scrollIntoView(el);
            highlight(el);
            demoSleep(600);
            el.click();
            demoSleep(2000);
            closeNewTab(main);
        } catch (TimeoutException e) {
            System.out.println("Button not found: " + buttonText);
        }
    }

    public void clickHeroButton(String key) {
        if (heroButtons.containsKey(key)) {
            clickButtonByText(heroButtons.get(key));
        }
    }

    public void clickCtaButton(String key) {
        if (ctaButtons.containsKey(key)) {
            // Explore Now perlu kembali ke helpdesk
            if ("exploreNow".equals(key)) {
                clickExploreNowAndReturn(ctaButtons.get(key));
            } else {
                clickButtonByText(ctaButtons.get(key));
            }
        }
    }

    private void clickExploreNowAndReturn(String buttonText) {
        String main = driver.getWindowHandle();
        WebElement el = driver.findElement(
                By.xpath("//a[contains(text(),'" + buttonText + "')]"));
        scrollIntoView(el);
        highlight(el);
        demoSleep(600);
        el.click();
        demoSleep(2000);

        closeNewTab(main);

        driver.get(helpdeskUrl);
        demoSleep(1200);
    }

    // ================= TUTORIAL IFRAME =================
    public void clickTutorialIframe() {
        String mainWindow = driver.getWindowHandle();

        try {
            // Cari iframe
            WebElement iframe = wait.until(ExpectedConditions.presenceOfElementLocated(
                    By.cssSelector("iframe[title*='Inventaire']")));
            driver.switchTo().frame(iframe);
            demoSleep(800);

            // Loop sampai ga ada tombol lagi
            while (true) {
                WebElement nextBtn = null;

                // Cari tombol yang visible & clickable di step saat ini
                try {
                    List<WebElement> candidates = driver.findElements(By.xpath("//a | //button | //*[@role='button']"));
                    for (WebElement btn : candidates) {
                        if (btn.isDisplayed() && btn.isEnabled()) {
                            nextBtn = btn;
                            break;
                        }
                    }
                } catch (Exception ignored) {
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

    // ================= FEATURES =================
    public void clickStartNowFeatures() {
        String main = driver.getWindowHandle();
        try {
            WebElement btn = wait.until(ExpectedConditions.elementToBeClickable(startNowFeaturesBtn));
            scrollIntoView(btn);
            highlight(btn);
            demoSleep(600);
            btn.click();
            demoSleep(2000);
            closeNewTab(main);
        } catch (TimeoutException e) {
            System.out.println("Start Now Features button tidak ditemukan");
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
        // Hero section
        clickHeroButton("firstTicket");
        clickHeroButton("startNowHero");
        clickHeroButton("accessInstance");
        clickHeroButton("openMyInstance");

        // Tutorial / Try Ticketing
        clickTutorialIframe();

        // Testimonial / CTA
        clickCtaButton("viewTestimonials");
        clickCtaButton("exploreNow");

        // Features section: Start Now besar
        clickStartNowFeatures();

        // Newsletter
        fillNewsletter("demo@test.com");

        // Social links
        clickAllSocialMediaLinks();
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
