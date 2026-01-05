package com.proyek.tests;

import com.proyek.pages.CommunityPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class CommunityPageTest {

    private WebDriver driver;
    private CommunityPage communityPage;

    @BeforeClass
    public void setUp() {
        // pastikan chromedriver ada di PATH atau set via System.setProperty
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("https://www.glpi-project.org/en/community/");
        communityPage = new CommunityPage(driver);
    }

    @Test(priority = 1)
    public void testClickForum() {
        communityPage.clickForum();
        System.out.println("Forum clicked successfully.");
    }

    @Test(priority = 2)
    public void testSocialMediaLinks() {
        String[] socialNames = { "facebook", "youtube", "twitter", "telegram", "github", "reddit", "instagram",
                "linkedin", "threads" };
        for (String name : socialNames) {
            communityPage.clickSocialByName(name);
            System.out.println("Clicked social media link: " + name);
        }
    }

    @Test(priority = 3)
    public void testOpenDocumentationSlides() {
        for (int i = 0; i < 5; i++) {
            communityPage.openDocumentationByIndex(i);
            System.out.println("Opened documentation slide: " + (i + 1));
        }
    }

    @Test(priority = 4)
    public void testVideoTutorials() {
        for (int i = 0; i < 4; i++) {
            communityPage.openVideoByIndex(i);
            System.out.println("Opened video tutorial: " + (i + 1));
        }
        communityPage.clickViewAllTestimonials();
        System.out.println("Clicked View All Testimonials button.");
    }

    @Test(priority = 5)
    public void testContactForm() {
        communityPage.fillContactForm(
                "Doe", "John", "Example Inc", "USA",
                "john.doe@example.com", "+1234567890", "This is a test message.");
        communityPage.submitContactForm();
        System.out.println("Contact form submitted successfully.");
    }

    @Test(priority = 6)
    public void testNewsletterForm() {
        communityPage.fillNewsletter("testnewsletter@example.com");
        communityPage.submitNewsletter();
        System.out.println("Newsletter form submitted successfully.");
    }

    // ===================== NEW BUTTON TESTS =====================
    @Test(priority = 7)
    public void testExploreNowButton() {
        String currentUrl = driver.getCurrentUrl(); // simpan URL utama
        communityPage.clickExploreNow(); // klik Explore Now
        System.out.println("Explore Now button clicked successfully.");
        driver.navigate().to(currentUrl); // balik ke halaman utama
    }

    @Test(priority = 8)
    public void testStartNowButton() {
        communityPage.clickStartNow();
        System.out.println("Start Now button clicked successfully.");
    }

    @Test(priority = 9)
    public void testNewsletterCTAButton() {
        communityPage.fillAndSubmitNewsletterCTA("testnewslettercta@example.com");
        System.out.println("Newsletter CTA submitted successfully.");
    }

    @AfterClass
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
