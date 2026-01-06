package com.proyek.pages.resources;

import com.proyek.base.BasePage;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import java.util.List;
import java.util.Set;

public class BlogPage extends BasePage {

    private final String blogUrl = "https://www.glpi-project.org/en/blog/";

    public BlogPage(WebDriver driver) {
        super(driver);
    }

    // ================= URL =================
    public void open() {
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(60));
        driver.get(blogUrl);
        driver.manage().window().maximize();
        acceptCookieIfPresent();
        wait.until(ExpectedConditions.presenceOfElementLocated(blogHeadline));
    }

    // ================= LOCATORS =================
    private final By blogHeadline = By.cssSelector("h1.ct-headline");
    public final By allArticles = By.cssSelector("#blog-archive .ct-div-block[id^='div_block-719']");
    private final By firstReadMore = By.cssSelector("#div_block-675-432338-1 a.ct-link-text.underline-offset");
    private final By firstCategoryLink = By.cssSelector("div.ct-text-block.category-color a");
    private final By loadMoreButton = By.cssSelector("button.wpgb-button.wpgb-load-more");
    public final By viewAllTestimonials = By.id("link_button-16-432311");
    public final By exploreNowButton = By.id("link_button-30-432311");
    public final By startNowButton = By.id("link_button-35-432311");

    // ================= VISIBILITY =================
    public boolean isPageVisible() {
        try {
            return wait.until(ExpectedConditions.visibilityOfElementLocated(blogHeadline)).isDisplayed();
        } catch (TimeoutException e) {
            return false;
        }
    }

    // ================= CONTACT FORM =================
    public final By contactLastName = By.name("your-name");
    public final By contactFirstName = By.name("your-first-name");
    public final By contactCompany = By.name("societe");
    public final By contactCountry = By.name("pays");
    public final By contactEmail = By.name("your-email");
    public final By contactPhone = By.name("your-phone");
    public final By contactMessage = By.name("your-message");
    public final By contactSubmitButton = By.cssSelector("input[type='submit'][value='Contact us']");

    public final By newsletterEmail = By.cssSelector("input[name='email']");
    public final By newsletterCheckbox = By.cssSelector("input[name='checkbox-75[]']");
    public final By newsletterSubmit = By.cssSelector("input.wpcf7-submit.btn-nl");

    public boolean isArticleVisible(int index) {
        try {
            List<WebElement> articles = driver.findElements(allArticles);
            return index < articles.size() && articles.get(index).isDisplayed();
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    // ================= ACTIONS =================
    public void clickFirstReadMore() {
        WebElement el = wait.until(ExpectedConditions.presenceOfElementLocated(firstReadMore));
        scrollAndHighlight(el);
        clickViaJS(el);

        // Tunggu headline / body muncul sebagai indikasi page terbuka
        By articleHeadline = By.cssSelector("h2.wp-block-heading");
        WebDriverWait longerWait = new WebDriverWait(driver, Duration.ofSeconds(30));
        longerWait.until(ExpectedConditions.visibilityOfElementLocated(articleHeadline));
    }

    public void clickFirstCategoryLink() {
        WebElement el = wait.until(ExpectedConditions.presenceOfElementLocated(firstCategoryLink));
        scrollAndHighlight(el);
        clickViaJS(el);

        String mainWindow = driver.getWindowHandle();

        // Tunggu sebentar untuk cek apakah tab baru muncul
        WebDriverWait shortWait = new WebDriverWait(driver, Duration.ofSeconds(5));
        try {
            shortWait.until(d -> d.getWindowHandles().size() > 1);
        } catch (TimeoutException e) {
            // Tidak ada tab baru, tetap di current page
        }

        Set<String> handles = driver.getWindowHandles();

        if (handles.size() > 1) {
            // Ada tab baru, switch ke tab tersebut
            for (String handle : handles) {
                if (!handle.equals(mainWindow)) {
                    driver.switchTo().window(handle);

                    // Tunggu category page terbuka
                    By categoryHeadline = By.cssSelector("h2.wp-block-heading");
                    WebDriverWait longerWait = new WebDriverWait(driver, Duration.ofSeconds(30));
                    longerWait.until(ExpectedConditions.visibilityOfElementLocated(categoryHeadline));

                    // Tutup tab baru & kembali ke main window
                    if (driver.getWindowHandles().contains(mainWindow)) {
                        driver.close(); // tutup tab baru
                        driver.switchTo().window(mainWindow);
                    }
                    break;
                }
            }
        } else {
            // Tidak ada tab baru, tunggu category page muncul di current page
            By categoryHeadline = By.cssSelector("h2.wp-block-heading");
            WebDriverWait longerWait = new WebDriverWait(driver, Duration.ofSeconds(30));
            longerWait.until(ExpectedConditions.visibilityOfElementLocated(categoryHeadline));

            // Optional: jika mau kembali ke blog, bisa pakai navigate().back() di test
        }
    }

    // ================= CATEGORY FILTER =================
    /**
     * Klik kategori berdasarkan label (misal: "All", "Community", dll)
     * Tunggu tombol muncul, terlihat, dan menjadi aktif.
     */
    public void clickCategory(String categoryLabel) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));

        // Tunggu container kategori muncul
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("ul.wpgb-inline-list")));

        boolean found = false;
        int retries = 3; // retry 3 kali jika stale
        while (!found && retries > 0) {
            try {
                List<WebElement> categoryButtons = driver
                        .findElements(By.cssSelector("ul.wpgb-inline-list div.wpgb-button"));
                for (WebElement button : categoryButtons) {
                    String label = button.findElement(By.cssSelector("span.wpgb-button-label")).getText().trim();
                    if (label.equalsIgnoreCase(categoryLabel)) {
                        scrollAndHighlight(button);

                        // pakai clickViaJS dengan ExpectedConditions.refreshed
                        wait.until(ExpectedConditions.refreshed(ExpectedConditions.elementToBeClickable(button)));
                        clickViaJS(button);

                        // tunggu tombol aktif
                        WebDriverWait shortWait = new WebDriverWait(driver, Duration.ofSeconds(10));
                        shortWait.until(d -> button.getAttribute("aria-pressed").equals("true"));

                        // tunggu minimal 1 artikel muncul
                        shortWait.until(d -> driver.findElements(allArticles).size() > 0);

                        found = true;
                        break;
                    }
                }
            } catch (StaleElementReferenceException e) {
                retries--;
                demoSleep(500); // tunggu sebentar sebelum retry
            }
        }

        if (!found)
            throw new NoSuchElementException(
                    "Category button '" + categoryLabel + "' not found or stale after retries");
    }

    /**
     * Cek apakah kategori tertentu sedang aktif
     */
    public boolean isCategoryActive(String categoryLabel) {
        List<WebElement> categoryButtons = driver.findElements(By.cssSelector("ul.wpgb-inline-list div.wpgb-button"));
        for (WebElement button : categoryButtons) {
            String label = button.findElement(By.cssSelector("span.wpgb-button-label")).getText().trim();
            if (label.equalsIgnoreCase(categoryLabel)) {
                return button.getAttribute("aria-pressed").equals("true");
            }
        }
        return false;
    }

    public boolean hasArticles() {
        List<WebElement> articles = driver.findElements(allArticles);
        return articles.size() > 0;
    }

    /**
     * Klik tombol "Load More Articles" jika ada, tunggu artikel baru muncul
     */
    /**
     * Klik tombol "Load More Articles" jika ada, tunggu artikel baru muncul
     */
    public void clickLoadMore() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        List<WebElement> initialArticles = driver.findElements(allArticles);
        boolean clicked = false;
        int retries = 3;

        while (!clicked && retries > 0) {
            try {
                // Ambil tombol load more lagi tiap retry
                WebElement loadMore = wait.until(ExpectedConditions.visibilityOfElementLocated(loadMoreButton));

                // Tunggu tombol clickable dan siap
                wait.until(ExpectedConditions.refreshed(ExpectedConditions.elementToBeClickable(loadMore)));

                scrollAndHighlight(loadMore);
                clickViaJS(loadMore);

                // Tunggu artikel baru muncul
                wait.until(d -> driver.findElements(allArticles).size() > initialArticles.size());

                clicked = true; // berhasil
            } catch (StaleElementReferenceException e) {
                retries--;
                demoSleep(500); // tunggu sebentar
            } catch (TimeoutException e) {
                System.out.println("Load More button not clickable or no more articles");
                break;
            }
        }

        if (!clicked)
            System.out.println("Failed to click Load More after retries");
    }

    public void fillContactForm() {
        type(contactLastName, "Doe");
        type(contactFirstName, "John");
        type(contactCompany, "ACME Corp");
        type(contactCountry, "Indonesia");
        type(contactEmail, "john.doe@test.com");
        type(contactPhone, "08123456789");
        type(contactMessage, "Need help regarding the blog");

        // tunggu tombol submit visible & scroll
        WebElement submit = wait.until(ExpectedConditions.presenceOfElementLocated(contactSubmitButton));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'});", submit);
        demoSleep(300);

        // klik via JS
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", submit);
        demoSleep(2000); // tunggu response form
    }

    public void clickViewAllTestimonials() {
        WebElement el = wait.until(ExpectedConditions.presenceOfElementLocated(viewAllTestimonials));
        scrollAndHighlight(el);
        demoSleep(500);

        // klik via JS
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", el);
        demoSleep(1000);

        // handle tab baru
        String main = driver.getWindowHandle();
        for (String handle : driver.getWindowHandles()) {
            if (!handle.equals(main)) {
                driver.switchTo().window(handle);
                demoSleep(1000);
                driver.close();
            }
        }
        driver.switchTo().window(main);
        wait.until(ExpectedConditions.visibilityOfElementLocated(blogHeadline));
    }

    public void clickExploreNow() {
        WebElement el = wait.until(ExpectedConditions.presenceOfElementLocated(exploreNowButton));
        scrollAndHighlight(el);
        demoSleep(500);

        // Klik via JS untuk aman
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", el);
        demoSleep(1000);

        // karena target _self, navigasi akan tetap di tab yang sama
        // tunggu page load / headline muncul
        wait.until(ExpectedConditions.visibilityOfElementLocated(blogHeadline));

        // opsional: kembali ke blog page
        driver.navigate().back();
        wait.until(ExpectedConditions.visibilityOfElementLocated(blogHeadline));
    }

    public void clickStartNow() {
        clickOpenNewTab(wait.until(ExpectedConditions.elementToBeClickable(startNowButton)));
    }

    public void fillNewsletter(String emailInput) {
        // Tunggu input email muncul & scroll
        WebElement emailEl = wait.until(ExpectedConditions.visibilityOfElementLocated(newsletterEmail));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'});", emailEl);
        demoSleep(500);
        emailEl.clear();
        emailEl.sendKeys(emailInput);

        // Checkbox wajib dicentang
        WebElement checkbox = wait.until(ExpectedConditions.elementToBeClickable(newsletterCheckbox));
        if (!checkbox.isSelected()) {
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", checkbox);
            demoSleep(300);
        }

        // Klik tombol submit
        WebElement submit = wait.until(ExpectedConditions.presenceOfElementLocated(newsletterSubmit));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'});", submit);
        demoSleep(400);
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", submit);
        demoSleep(1500); // tunggu response
    }

    // ================= HELPERS =================
    private void scrollAndHighlight(WebElement el) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", el);
        highlight(el);
    }

    protected void type(By locator, String text) {
        WebElement el = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        scrollAndHighlight(el);
        el.clear();
        el.sendKeys(text);
        demoSleep(400);
    }

    // di BlogPage.java
    public void safeClick(WebElement el) {
        scrollIntoView(el);
        highlight(el);
        demoSleep(400);
        try {
            el.click();
        } catch (Exception e) {
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", el);
        }
        demoSleep(800);
    }

    public final By pageTitle = By.xpath("//h1[contains(.,'Frequently Asked Questions')]");

    public void clickOpenNewTab(WebElement el) {
        String main = driver.getWindowHandle();
        safeClick(el);
        demoSleep(1500);
        for (String win : driver.getWindowHandles()) {
            if (!win.equals(main)) {
                driver.switchTo().window(win);
                demoSleep(1000); // tunggu page baru load
                driver.close(); // tutup tab baru
            }
        }
        driver.switchTo().window(main);
        demoSleep(600); // optional tunggu main page stabil
    }

    private void clickViaJS(WebElement el) {
        try {
            el.click();
        } catch (ElementClickInterceptedException | TimeoutException e) {
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", el);
        }
    }

    public int getAllArticlesCount() {
        return driver.findElements(allArticles).size();
    }

}

