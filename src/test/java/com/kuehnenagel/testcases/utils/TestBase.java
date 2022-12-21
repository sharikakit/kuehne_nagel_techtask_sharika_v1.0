package com.kuehnenagel.testcases.utils;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.concurrent.TimeUnit;

public class TestBase {

    public static WebDriver webDriver;
    private static final String BASEURL = "https://demoqa.com/login";

    @BeforeAll
    public static void launchApplication() {
        setChromeDriverroperty();
        webDriver = new ChromeDriver();
        webDriver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
        webDriver.get(BASEURL);
        webDriver.manage().window().maximize() ;
    }

    @AfterAll
    public static void closeBrowser() {
        webDriver.quit();
    }

    private static void setChromeDriverroperty() {
        System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir") + "/src/main/resources/chromedriver.exe");
    }

}
