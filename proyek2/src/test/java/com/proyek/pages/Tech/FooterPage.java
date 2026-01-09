package com.proyek.pages.Tech;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.JavascriptExecutor;

// import java.time.Duration;
import java.util.List;

public class FooterPage {
    private WebDriver driver;

    public FooterPage(WebDriver driver) {
        this.driver = driver;
    }

    // Ambil semua link di footer
    public List<WebElement> getAllFooterLinks() {
        return driver.findElements(By.cssSelector("footer#main-footer a"));
    }

    // Cek visibility link
    public boolean isFooterLinkVisible(WebElement link) {
        try {
            return link.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    // Klik link tanpa tunggu clickable (lebih aman jika langsung redirect)
    public void clickFooterLink(WebElement link) {
        try {
            // Scroll ke link biar terlihat
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", link);

            String href = link.getAttribute("href");
            System.out.println("Klik link: " + href);

            // Klik link
            link.click();

            // Tunggu sedikit, atau bisa pakai validasi URL
            Thread.sleep(1000); // optional, bisa diganti explicit wait sesuai kebutuhan

        } catch (Exception e) {
            System.out.println("Gagal klik link: " + e.getMessage());
        }
    }

    // Klik semua link di footer (sosmed cukup 1 icon, sisanya link text)
    public void clickAllFooterLinks() {
        boolean socialChecked = false;
        String faqUrl = "https://glpi-network.cloud/faq/";

        List<WebElement> links = getAllFooterLinks();
        int totalLinks = links.size();

        for (int i = 0; i < totalLinks; i++) {
            // Ambil elemen fresh setiap loop untuk menghindari StaleElementReference
            links = getAllFooterLinks();
            WebElement link = links.get(i);

            if (!isFooterLinkVisible(link)) {
                continue;
            }

            String href = link.getAttribute("href");
            if (href == null || href.isEmpty()) {
                continue;
            }

            // Jika link sosial media, klik 1 saja
            if ((href.contains("linkedin") || href.contains("facebook") ||
                    href.contains("twitter") || href.contains("youtube") ||
                    href.contains("telegram") || href.contains("reddit") ||
                    href.contains("github"))) {

                if (!socialChecked) {
                    clickFooterLink(link);
                    driver.get(faqUrl); // kembali ke FAQ
                    socialChecked = true;
                }
            } else {
                // klik link biasa
                clickFooterLink(link);
                driver.get(faqUrl); // kembali ke FAQ
            }
        }
    }

    public String clickFooterLinkAndGetUrl(WebElement link) {
        try {
            ((JavascriptExecutor) driver)
                    .executeScript("arguments[0].scrollIntoView(true);", link);

            link.click();
            Thread.sleep(1500);

            return driver.getCurrentUrl();
        } catch (Exception e) {
            throw new RuntimeException("Gagal klik footer link", e);
        }
    }

    public List<String> getAllFooterHrefs() {
        return driver.findElements(By.cssSelector("footer#main-footer a"))
                .stream()
                .map(e -> e.getAttribute("href"))
                .filter(h -> h != null && !h.isEmpty())
                .distinct()
                .toList();
    }

    public boolean isExternalProductLink(String href) {
        return href.toLowerCase().contains("glpi");
    }

    public boolean isExpectedExternalProductUrl(String href, String currentUrl) {
        if (href.toLowerCase().contains("glpi"))
            return currentUrl.contains("glpi-project.org");
        return false;
    }

}
