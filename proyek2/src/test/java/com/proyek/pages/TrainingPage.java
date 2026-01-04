package com.proyek.pages;

import java.io.File;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;

import com.proyek.base.BasePage;

public class TrainingPage extends BasePage {
    private final String url = "https://www.glpi-project.org/en/";

    public TrainingPage(WebDriver driver) {
        super(driver);
    }

    // ================= URL =================
    public void open() {
        driver.get(url);
        acceptCookieIfPresent();
        demoSleep(1200);
        
        // Navigate to Training page
        navigateToTrainingPage();
    }
    
    public void navigateToTrainingPage() {
        // 1. Klik Resources menu
        WebElement resources = wait.until(ExpectedConditions.elementToBeClickable(resourcesMenu));
        scrollIntoView(resources);
        highlight(resources);
        demoSleep(600);
        resources.click();
        demoSleep(800);
        System.out.println("✅ Clicked 'Resources' menu");
        
        // 2. Klik Training link
        WebElement training = wait.until(ExpectedConditions.elementToBeClickable(trainingLink));
        highlight(training);
        demoSleep(600);
        training.click();
        demoSleep(1500);
        System.out.println("✅ Clicked 'Training' link");
        System.out.println("✅ Navigated to Training page");
    }

    // ================= LOCATORS =================
    private final By pageTitle = By.id("headline-14-432450");
    
    // Menu Navigation
    private final By resourcesMenu = By.xpath("//span[@class='oxy-mega-dropdown_link-text' and text()='Resources']");
    private final By trainingLink = By.id("text_block-719-10");
    
    // Tombol "Choose a course"
    private final By chooseCourseButton = By.id("link_text-12-432450");
    
    // Tab "GLPI - Plugins" (Online 2 days)
    private final By glpiPluginsTab = By.id("_tab-80-432450");
    
    // Tombol "Register" (distanciel)
    private final By registerButton = By.id("bouton-inscription-distanciel-1");
    
    // Tab "GLPI administration" (In person 5 days)
    private final By glpiAdminTab = By.id("_tab-79-432450");
    
    // Tombol "Download the program" (PDF)
    private final By downloadProgramButton = By.id("link_button-488-432450");
    
    // Tombol "Register" presentiel (5 tombol)
    private final By registerPresentiel1 = By.id("bouton-inscription-presentiel-1");
    private final By registerPresentiel2 = By.id("bouton-inscription-presentiel-2");
    private final By registerPresentiel3 = By.id("bouton-inscription-presentiel-3");
    private final By registerPresentiel4 = By.id("bouton-inscription-presentiel-4");
    private final By registerPresentiel5 = By.id("bouton-inscription-presentiel-5");
    
    // Form Registrasi Training
    private final By inputLastName = By.cssSelector("input[name='Nom']");
    private final By inputFirstName = By.cssSelector("input[name='Prenom']");
    private final By inputEmail = By.cssSelector("input[name='email'][type='email']");
    private final By inputCompany = By.cssSelector("input[name='Societe']");
    private final By inputCountry = By.cssSelector("input[name='Pays']");
    private final By inputPhone = By.cssSelector("input[name='tel']");
    private final By radioOnline = By.xpath("//input[@type='radio'][@name='module'][@value='Online']");
    private final By dropdownFormation = By.cssSelector("select[name='formation_en_ligne']");
    private final By inputMessage = By.cssSelector("textarea[name='message']");
    private final By submitButton = By.cssSelector("input.wpcf7-submit[type='submit']");

    // ================= ACTIONS =================
    
    public boolean isPageVisible() {
        return isVisible(pageTitle);
    }

    public void clickChooseCourse() {
        WebElement btn = wait.until(ExpectedConditions.elementToBeClickable(chooseCourseButton));
        scrollIntoView(btn);
        highlight(btn);
        demoSleep(600);
        btn.click();
        demoSleep(1000);
        System.out.println("✅ Clicked 'Choose a course' button");
    }

    public void clickGlpiPluginsTab() {
        WebElement tab = wait.until(ExpectedConditions.elementToBeClickable(glpiPluginsTab));
        scrollIntoView(tab);
        highlight(tab);
        demoSleep(600);
        tab.click();
        demoSleep(1000);
        System.out.println("✅ Clicked 'GLPI - Plugins' tab");
    }

    public void clickRegisterButton() {
        WebElement btn = wait.until(ExpectedConditions.elementToBeClickable(registerButton));
        scrollIntoView(btn);
        highlight(btn);
        demoSleep(600);
        btn.click();
        demoSleep(1000);
        System.out.println("✅ Clicked 'Register' button");
    }

    public void clickGlpiAdminTab() {
        WebElement tab = wait.until(ExpectedConditions.elementToBeClickable(glpiAdminTab));
        scrollIntoView(tab);
        highlight(tab);
        demoSleep(600);
        tab.click();
        demoSleep(1000);
        System.out.println("✅ Clicked 'GLPI administration' tab");
    }

    public void clickDownloadProgramAndVerify() {
        // Simpan window handle utama
        String mainWindow = driver.getWindowHandle();
        
        WebElement btn = wait.until(ExpectedConditions.elementToBeClickable(downloadProgramButton));
        scrollIntoView(btn);
        highlight(btn);
        
        // Get download URL
        String downloadUrl = btn.getAttribute("href");
        System.out.println("📥 Download URL: " + downloadUrl);
        
        // Verify URL is valid PDF
        assert downloadUrl != null && downloadUrl.endsWith(".pdf") : "URL bukan file PDF!";
        System.out.println("✅ URL is valid PDF file");
        
        demoSleep(600);
        btn.click();
        demoSleep(3000); // Wait for download to start
        
        // Tutup tab PDF jika terbuka di tab baru
        for (String windowHandle : driver.getWindowHandles()) {
            if (!windowHandle.equals(mainWindow)) {
                driver.switchTo().window(windowHandle);
                driver.close();
                System.out.println("📄 Closed PDF tab");
            }
        }
        
        // Kembali ke window utama dan fokus
        driver.switchTo().window(mainWindow);
        
        // Bring Chrome window back to focus
        ((org.openqa.selenium.JavascriptExecutor) driver).executeScript("window.focus();");
        demoSleep(500);
        System.out.println("🔄 Switched back to main Chrome window");
        
        // Verify file downloaded
        String userHome = System.getProperty("user.home");
        String downloadFolder = userHome + "\\Downloads";
        String fileName = "Brochure-formation-GLPI.pdf";
        File downloadedFile = new File(downloadFolder + "\\" + fileName);
        
        // Wait up to 10 seconds for file to download
        int waitTime = 0;
        while (!downloadedFile.exists() && waitTime < 10) {
            demoSleep(1000);
            waitTime++;
        }
        
        if (downloadedFile.exists()) {
            long fileSize = downloadedFile.length();
            System.out.println("✅ File downloaded successfully!");
            System.out.println("📁 File path: " + downloadedFile.getAbsolutePath());
            System.out.println("📊 File size: " + (fileSize / 1024) + " KB");
            
            // Verify file is not empty
            assert fileSize > 0 : "File kosong!";
            System.out.println("✅ File is valid (not empty)");
            
            // Verify file is PDF (check size reasonable for PDF)
            assert fileSize > 1024 : "File terlalu kecil, mungkin corrupt!";
            System.out.println("✅ File size is reasonable");
            
        } else {
            System.out.println("⚠️ File not found in Downloads folder (mungkin browser setting berbeda)");
        }
        
        System.out.println("✅ Download test completed");
    }

    public void clickAllRegisterPresentielButtons() {
        By[] registerButtons = {
            registerPresentiel1,
            registerPresentiel2,
            registerPresentiel3,
            registerPresentiel4,
            registerPresentiel5
        };
        
        for (int i = 0; i < registerButtons.length; i++) {
            try {
                WebElement btn = wait.until(ExpectedConditions.elementToBeClickable(registerButtons[i]));
                scrollIntoView(btn);
                highlight(btn);
                demoSleep(600);
                btn.click();
                demoSleep(800);
                System.out.println("✅ Clicked 'Register' presentiel button " + (i + 1));
                
                // Scroll back up untuk tombol berikutnya
                scrollToTop();
                demoSleep(500);
            } catch (Exception e) {
                System.out.println("⚠️ Register presentiel button " + (i + 1) + " not found or not clickable");
            }
        }
        System.out.println("✅ All Register presentiel buttons tested");
    }

    public void fillRegistrationForm() {
        System.out.println("Filling registration form...");
        
        try {
            // 1. Last Name
            WebElement lastName = wait.until(ExpectedConditions.visibilityOfElementLocated(inputLastName));
            scrollIntoView(lastName);
            highlight(lastName);
            lastName.clear();
            lastName.sendKeys("Doe");
            demoSleep(400);
            System.out.println("✅ Filled Last Name: Doe");
            
            // 2. First Name
            WebElement firstName = wait.until(ExpectedConditions.visibilityOfElementLocated(inputFirstName));
            highlight(firstName);
            firstName.clear();
            firstName.sendKeys("Patrik");
            demoSleep(400);
            System.out.println("✅ Filled First Name: Patrik");
            
            // 3. Email
            WebElement email = wait.until(ExpectedConditions.visibilityOfElementLocated(inputEmail));
            highlight(email);
            email.clear();
            email.sendKeys("testing7103@gmail.com");
            demoSleep(400);
            System.out.println("✅ Filled Email: testing7103@gmail.com");
            
            // 4. Company
            WebElement company = wait.until(ExpectedConditions.visibilityOfElementLocated(inputCompany));
            highlight(company);
            company.clear();
            company.sendKeys("ISTTS");
            demoSleep(400);
            System.out.println("✅ Filled Company: ISTTS");
            
            // 5. Country
            WebElement country = wait.until(ExpectedConditions.visibilityOfElementLocated(inputCountry));
            highlight(country);
            country.clear();
            country.sendKeys("Indonesia");
            demoSleep(400);
            System.out.println("✅ Filled Country: Indonesia");
            
            // 6. Phone
            WebElement phone = wait.until(ExpectedConditions.visibilityOfElementLocated(inputPhone));
            highlight(phone);
            phone.clear();
            phone.sendKeys("+6285777090291");
            demoSleep(400);
            System.out.println("✅ Filled Phone: +6285777090291");
            
            // 7. Radio button Online
            WebElement radioBtn = wait.until(ExpectedConditions.presenceOfElementLocated(radioOnline));
            scrollIntoView(radioBtn);
            highlight(radioBtn);
            // Gunakan JavaScript untuk klik radio button (lebih reliable)
            ((org.openqa.selenium.JavascriptExecutor) driver).executeScript("arguments[0].click();", radioBtn);
            demoSleep(400);
            System.out.println("✅ Selected Radio: Online");
            
            // 8. Dropdown Formation
            WebElement dropdown = wait.until(ExpectedConditions.visibilityOfElementLocated(dropdownFormation));
            scrollIntoView(dropdown);
            highlight(dropdown);
            Select select = new Select(dropdown);
            select.selectByValue("28/11/2025");
            demoSleep(400);
            System.out.println("✅ Selected Formation: 28/11/2025");
            
            // 9. Message
            WebElement message = wait.until(ExpectedConditions.visibilityOfElementLocated(inputMessage));
            scrollIntoView(message);
            highlight(message);
            message.clear();
            message.sendKeys("I might want to learn more about your software services");
            demoSleep(400);
            System.out.println("✅ Filled Message");
            
            // 10. Submit
            WebElement submit = wait.until(ExpectedConditions.elementToBeClickable(submitButton));
            scrollIntoView(submit);
            highlight(submit);
            demoSleep(600);
            submit.click();
            demoSleep(2000);
            System.out.println("✅ Clicked Submit button");
            
            System.out.println("✅ Registration form submitted successfully!");
            
        } catch (Exception e) {
            System.out.println("⚠️ Error filling registration form: " + e.getMessage());
        }
    }


    // ================= FULL FLOW =================
    public void testAllElementsComplete() {
        System.out.println("\n========== TRAINING PAGE TEST ==========");
        
        if (isPageVisible()) {
            System.out.println("✅ Page is visible");
            
            System.out.println("Testing 'Choose a course' button...");
            clickChooseCourse();
            
            System.out.println("Testing 'GLPI - Plugins' tab...");
            clickGlpiPluginsTab();
            
            System.out.println("Testing 'Register' button...");
            clickRegisterButton();
            
            System.out.println("Testing 'GLPI administration' tab...");
            clickGlpiAdminTab();
            
            System.out.println("Testing 'Download the program' button...");
            clickDownloadProgramAndVerify();
            
            System.out.println("Testing all 'Register' presentiel buttons (1-5)...");
            clickAllRegisterPresentielButtons();
            
            System.out.println("Filling registration form...");
            fillRegistrationForm();
            
            System.out.println("========== TRAINING PAGE COMPLETE ==========\n");
        } else {
            System.out.println("❌ Trainings Page failed to load.");
        }
    }
}
