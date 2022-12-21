package com.kuehnenagel.testcases.pages;

import com.kuehnenagel.testcases.utils.TestBase;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.concurrent.TimeUnit;

import static org.awaitility.Awaitility.await;

public class bookStorePage extends TestBase {

    public bookStorePage(){
        setWebDriver(webDriver);
    }

    protected static WebDriver driver;

    public void setWebDriver(WebDriver driver) {
        bookStorePage.driver = driver;
    }

    String bookStorePageTitleXPath = "//*[@id=\"app\"]/div/div/div[1]/div";
    String searchBarID = "searchBox";
    String searchIconID = "basic-addon2";
    String addToCollectionBtnXPath = "//button[text()='Add To Your Collection']";

    WebDriverWait wait;

    public void verifyBookStorePageTitle(){
    if(driver.findElement(By.xpath(bookStorePageTitleXPath)).getText().equalsIgnoreCase("Book Store")){
        System.out.println("User is on Book Store page");
    } else {
        System.out.println("Book Store page is unavailable");
      }
    }

    public void searchBookTitle(String bookTitle){
        driver.findElement(By.id(searchBarID)).click();
        driver.findElement(By.id(searchBarID)).sendKeys(bookTitle);
        driver.findElement(By.id(searchIconID)).click();
    }

    public String verifyBookName(String bookTitle){
        String bookName = driver.findElement(By.xpath("//a[text()='"+bookTitle+"']")).getText();
        return bookName;
    }

    public void clickBookTitle(String bookTitle){
        driver.findElement(By.xpath("//a[text()='"+bookTitle+"']")).click();
    }

    public void addBookToCollection() {
        wait = new WebDriverWait(driver, 10);
        await("Add to collection button not visible").atMost(20, TimeUnit.SECONDS).until(driver.findElement(By.xpath(addToCollectionBtnXPath))::isDisplayed);
        driver.findElement(By.xpath(addToCollectionBtnXPath)).click();
        wait.until(ExpectedConditions.alertIsPresent());
        Alert alert = driver.switchTo().alert();
        String alertMessage= driver.switchTo().alert().getText();
        alert.accept();
        System.out.println(alertMessage);
    }

}
