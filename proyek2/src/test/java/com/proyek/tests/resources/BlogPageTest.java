package com.proyek.tests.resources;

import com.proyek.pages.resources.BlogPage;
import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import static org.junit.jupiter.api.Assertions.assertTrue;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class BlogPageTest {

    private WebDriver driver;
    private BlogPage blogPage;

    @BeforeAll
    void setupAll() {
        driver = new ChromeDriver();
        blogPage = new BlogPage(driver);
        blogPage.open();
    }

    @AfterAll
    void teardownAll() {
        if (driver != null)
            driver.quit();
    }

    @Test
    @Order(1)
    @DisplayName("Blog page is visible and first article is visible")
    void testBlogPageActions() {
        assertTrue(blogPage.isPageVisible(), "Blog page should be visible");
        assertTrue(blogPage.isArticleVisible(0), "First article should be visible");

        blogPage.clickFirstReadMore();
        driver.navigate().back(); // kembali ke halaman blog jika diperlukan

        blogPage.clickFirstCategoryLink();
        driver.navigate().back(); // kembali ke halaman blog jika diperlukan

        // BlogPage sudah handle tab baru
        assertTrue(blogPage.isPageVisible(), "Should be back at blog page after category link");
    }

    @Test
    @Order(2)
    @DisplayName("All blog categories can be clicked and active")
    void testAllBlogCategories() {
        String[] categories = { "All", "Community", "Versions", "Events", "Products", "Tutorials" };

        for (String cat : categories) {
            blogPage.clickCategory(cat);
            assertTrue(blogPage.isCategoryActive(cat), cat + " should be active");
            assertTrue(blogPage.hasArticles(), "There should be articles for " + cat);
        }
    }

    @Test
    @Order(3)
    @DisplayName("Load more articles works correctly")
    void testLoadMoreArticles() {
        int initialArticles = blogPage.getAllArticlesCount();
        blogPage.clickLoadMore();
        int afterLoadMore = blogPage.getAllArticlesCount();

        System.out.println("Initial articles count: " + initialArticles);
        System.out.println("Articles count after Load More: " + afterLoadMore);

        assertTrue(afterLoadMore > initialArticles, "There should be more articles after clicking Load More");
    }

    @Test
    @Order(4)
    @DisplayName("Contact form can be filled and submitted")
    void testContactForm() {
        blogPage.fillContactForm();
        assertTrue(blogPage.isPageVisible(), "Blog page tetap visible setelah submit contact form");
    }

    @Test
    @Order(5)
    @DisplayName("View all testimonials works")
    void testViewAllTestimonials() {
        blogPage.clickViewAllTestimonials();
        assertTrue(blogPage.isPageVisible(), "Blog page tetap visible setelah browse All Testimonials");
    }

    @Test
    @Order(6)
    @DisplayName("Explore Now button works")
    void testExploreNowButton() {
        blogPage.clickExploreNow();
        assertTrue(blogPage.isPageVisible(), "Blog page tetap visible setelah klik Explore Now");
    }

    @Test
    @Order(7)
    @DisplayName("Start Now button works")
    void testStartNowButton() {
        blogPage.clickStartNow();
        assertTrue(blogPage.isPageVisible(), "Blog page tetap visible setelah klik Start Now");
    }

    @Test
    @Order(8)
    @DisplayName("Newsletter subscription can be submitted")
    void testNewsletterSubscription() {
        blogPage.fillNewsletter("john.doe@test.com");
        assertTrue(blogPage.isPageVisible(), "Blog page tetap visible setelah submit newsletter");
    }

}
