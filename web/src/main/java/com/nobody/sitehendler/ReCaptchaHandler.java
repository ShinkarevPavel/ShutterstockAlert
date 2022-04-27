//package com.nobody.sitehendler;
//
//import org.openqa.selenium.By;
//import org.openqa.selenium.WebElement;
//import org.openqa.selenium.chrome.ChromeDriver;
//import org.openqa.selenium.chrome.ChromeOptions;
//import org.openqa.selenium.support.ui.ExpectedConditions;
//import org.openqa.selenium.support.ui.WebDriverWait;
//
//import java.time.Duration;
//
//
//public class ReCaptchaHandler {
//    private final String EARNINGS_PAGE = "https://submit.shutterstock.com/earnings";
//
//
//    public String click() {
//        System.setProperty("webdriver.chrome.driver", "C:\\Utility\\BrowserDrivers\\chromedriver.exe");
//
//        ChromeOptions options = new ChromeOptions();
//        options.addArguments("start-maximized");
//        options.addArguments("disable-infobars");
//        options.addArguments("--disable-extensions");
//
//        ChromeDriver driver = new ChromeDriver(options);
//
//
////        driver.get(EARNINGS_PAGE);
////        WebDriver driver = new ShutterRemoteDriver(options);
//
//
//
//        driver.get(EARNINGS_PAGE);
//        WebElement username = driver.findElement(By.id("login-username"));
//        WebElement password = driver.findElement(By.id("login-password"));
//        username.sendKeys("pavelshynkarou");
//        password.sendKeys("2Load2Reload2");
//
//        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(8).toMillis());
//
//        /*ReCaptcha workaround!!!!!!!!!!!!!!!!!!!!!!!!!!!!*/
//
////        wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(
////                By.xpath("//iframe[starts-with(@name, 'a-') and starts-with(@src, 'https://www.google.com/recaptcha')]")));
//
//
////        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("div.recaptcha-checkbox-border")))
////                .click();
////        System.out.println("Clicked the checkbox");
//        try {
//            Thread.sleep(Duration.ofSeconds(8).toMillis());
//        } catch (InterruptedException e) {
//            System.out.println("Error " + e);
//        }
//        System.out.println(driver.getPageSource());
//        wait.until(ExpectedConditions.elementToBeClickable(
//                By.cssSelector("div.submit-group"))).click();
//        return driver.getPageSource();
//    }
//}
