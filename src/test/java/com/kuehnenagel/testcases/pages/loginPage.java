package com.kuehnenagel.testcases.pages;

import com.kuehnenagel.testcases.utils.TestBase;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class loginPage extends TestBase {

    public loginPage() {
        setWebDriver(webDriver);
    }

    protected static WebDriver driver;

    public void setWebDriver(WebDriver driver) {
        loginPage.driver = driver;
    }

    public profilePage login() throws IOException {
        WebElement userName = driver.findElement(By.id("userName"));
        WebElement password = driver.findElement(By.id("password"));
        WebElement loginBtn = driver.findElement(By.id("login"));

        Properties propFile = new Properties();
        FileInputStream readPropFile = new FileInputStream(System.getProperty("user.dir")+"/src/test/resources/configs/TestData.properties");
        propFile.load(readPropFile);

        userName.sendKeys(propFile.getProperty("username"));
        password.sendKeys(propFile.getProperty("password"));
        loginBtn.click();
        return new profilePage();
    }

}
