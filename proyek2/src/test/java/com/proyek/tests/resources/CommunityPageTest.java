package com.proyek.tests.resources;

import org.junit.jupiter.api.AfterAll;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import com.proyek.pages.resources.CommunityPage;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CommunityPageTest {

    private WebDriver driver;
    private CommunityPage communityPage;

    @BeforeAll
    void setUp() {
        // pastikan chromedriver ada di PATH atau set via System.setProperty
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("https://www.glpi-project.org/en/community/");
        communityPage = new CommunityPage(driver);
    }

    @Test
    @Order(1)
    @DisplayName("Forum button should be clickable")
    void testClickForum() {
        communityPage.clickForum();
        System.out.println("Forum clicked successfully.");
        assertTrue(driver.getCurrentUrl().contains("glpi"), "Should stay on GLPI site");
    }

    @Test
    @Order(2)
    @DisplayName("All social media links should be clickable")
    void testSocialMediaLinks() {
        String[] socialNames = { "facebook", "youtube", "twitter", "telegram", "github", "reddit", "instagram",
                "linkedin", "threads" };
        for (String name : socialNames) {
            communityPage.clickSocialByName(name);
            System.out.println("Clicked social media link: " + name);
        }
    }

    @Test
    @Order(3)
    @DisplayName("Documentation slides should be openable")
    void testOpenDocumentationSlides() {
        for (int i = 0; i < 5; i++) {
            communityPage.openDocumentationByIndex(i);
            System.out.println("Opened documentation slide: " + (i + 1));
        }
    }

    @Test
    @Order(4)
    @DisplayName("Video tutorials should be openable")
    void testVideoTutorials() {
        for (int i = 0; i < 4; i++) {
            communityPage.openVideoByIndex(i);
            System.out.println("Opened video tutorial: " + (i + 1));
        }
        communityPage.clickViewAllTestimonials();
        System.out.println("Clicked View All Testimonials button.");
    }

    // @Test
    // @Order(5)
    // @DisplayName("Contact form should be fillable and submittable")
    // void testContactForm() {
    //     communityPage.fillContactForm(
    //             "Doe", "John", "Example Inc", "USA",
    //             "john.doe@example.com", "+1234567890", "This is a test message.");
    //     communityPage.submitContactForm();
    //     System.out.println("Contact form submitted successfully.");
    // }

    // @Test
    // @Order(6)
    // @DisplayName("Newsletter form should be fillable and submittable")
    // void testNewsletterForm() {
    //     // Scroll ke footer untuk memastikan newsletter form visible
    //     ((org.openqa.selenium.JavascriptExecutor) driver)
    //             .executeScript("window.scrollTo(0, document.body.scrollHeight);");
    //     try {
    //         Thread.sleep(1000);
    //     } catch (InterruptedException e) {
    //     }
    //     
    //     communityPage.fillNewsletter("testnewsletter@example.com");
    //     communityPage.submitNewsletter();
    //     System.out.println("Newsletter form submitted successfully.");
    // }

    // ===================== NEW BUTTON TESTS =====================
    // @Test
    // @Order(7)
    // @DisplayName("Explore Now button should be clickable")
    // void testExploreNowButton() {
    //     String currentUrl = driver.getCurrentUrl(); // simpan URL utama
    //     communityPage.clickExploreNow(); // klik Explore Now
    //     System.out.println("Explore Now button clicked successfully.");
    //     driver.navigate().to(currentUrl); // balik ke halaman utama
    // }

    // @Test
    // @Order(8)
    // @DisplayName("Start Now button should be clickable")
    // void testStartNowButton() {
    //     communityPage.clickStartNow();
    //     System.out.println("Start Now button clicked successfully.");
    // }

    // @Test
    // @Order(9)
    // @DisplayName("Newsletter CTA should be fillable and submittable")
    // void testNewsletterCTAButton() {
    //     // Scroll ke footer untuk memastikan newsletter CTA visible
    //     ((org.openqa.selenium.JavascriptExecutor) driver)
    //             .executeScript("window.scrollTo(0, document.body.scrollHeight);");
    //     try {
    //         Thread.sleep(1000);
    //     } catch (InterruptedException e) {
    //     }
    //     
    //     communityPage.fillAndSubmitNewsletterCTA("testnewslettercta@example.com");
    //     System.out.println("Newsletter CTA submitted successfully.");
    // }

    @AfterAll
    void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
