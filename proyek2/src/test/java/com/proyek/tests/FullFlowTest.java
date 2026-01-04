package com.proyek.tests;

import org.junit.jupiter.api.Test;

import com.proyek.base.BaseTest;
import com.proyek.pages.TrainingPage;

public class FullFlowTest extends BaseTest {

    @Test
    void fullDemoFlow() {
        // 1️⃣ Home page
        // HomePage home = new HomePage(driver);
        // home.open();
        // home.testAllElementsComplete();

        // 2️⃣ Helpdesk page
        // HelpdeskPage helpdesk = new HelpdeskPage(driver);
        // helpdesk.open();
        // helpdesk.testAllElementsComplete();

        // 3️⃣ CMDB page
        // CmdbPage cmdb = new CmdbPage(driver);
        // cmdb.open();
        // cmdb.testAllElementsComplete();

        // 4️⃣ Finance page
        // FinancePage finance = new FinancePage(driver);
        // finance.open();
        // finance.testAllElementsComplete();

        // 5️⃣ Administration page
        // AdministrationPage administration = new AdministrationPage(driver);
        // administration.open();
        // administration.testAllElementsComplete();

        // 6️⃣ Prices page
        // PricesPage prices = new PricesPage(driver);
        // prices.open();
        // prices.testAllElementsComplete();

        // 7️⃣ Download page
        // DownloadPage download = new DownloadPage(driver);
        // download.open();
        // download.testAllElementsComplete();

        // 8️⃣ FAQ page
        // FaqPage faq = new FaqPage(driver);
        // faq.open();
        // faq.testAllElementsComplete();

        // 9️⃣ Training page
        TrainingPage training = new TrainingPage(driver);
        training.open();
        training.testAllElementsComplete();
    }
}
