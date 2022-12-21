package com.kuehnenagel.testcases.pages;

import com.kuehnenagel.testcases.utils.TestBase;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import static org.awaitility.Awaitility.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class profilePage extends TestBase {

    public profilePage(){
        setWebDriver(webDriver);
    }

    protected static WebDriver driver;

    public void setWebDriver(WebDriver driver) {
        profilePage.driver = driver;
    }

    String profilePageTitleXPath = "//*[@id=\"app\"]/div/div/div[1]/div";
    String goToBookStoreBtnID = "gotoStore";
    String deleteAllBooksBtnXPath = "//button[text()='Delete All Books']";
    String profileLinkXPath = "//span[text()='Profile']";
    String loginLinkXPath = "//span[text()='Login']";
    String viewYourProfileLinkXPath = "//a[text()='profile']";
    String deleteIconID = "delete-record-undefined";
    String deleteSingleBookPopupMsgXPath = "//div[text()='Do you want to delete this book?']";
    String deleteSingleBookPopupOKBtnID = "closeSmallModal-ok";
    String noBooksInCollectionText = "No rows found";

    WebDriverWait wait;

    bookStorePage bookstorepage = new bookStorePage();


    public void verifyProfilePageTitle(){
        if(driver.findElement(By.xpath(profilePageTitleXPath)).getText().equalsIgnoreCase("Profile")){
            System.out.println("User is on Profile page");
        }
    }

    public bookStorePage clickGoToBookStoreBtn() {
        await("Go To Book Store button not visible").atMost(20, TimeUnit.SECONDS).until(driver.findElement(By.id(goToBookStoreBtnID))::isDisplayed);
        driver.findElement(By.id(goToBookStoreBtnID)).click();
        return new bookStorePage();
    }

    public void verifyBookNameInCollection(String bookTitle) {
        await("Login link not visible").atMost(20, TimeUnit.SECONDS).until(driver.findElement(By.xpath(loginLinkXPath))::isDisplayed);
        driver.findElement(By.xpath(loginLinkXPath)).click();
        driver.findElement(By.xpath(viewYourProfileLinkXPath)).click();
        bookstorepage.searchBookTitle(bookTitle);
        String bookName = bookstorepage.verifyBookName(bookTitle);
        System.out.println(bookName);
        Assertions.assertEquals(bookTitle, bookName);
    }

    public void deleteAllBooksInProfile() {
        await("Delete all button not visible").atMost(20, TimeUnit.SECONDS).until(driver.findElement(By.xpath(deleteAllBooksBtnXPath))::isDisplayed);
        driver.findElement(By.xpath(deleteAllBooksBtnXPath)).click();
        driver.findElement(By.id("closeSmallModal-ok")).click();
        wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.alertIsPresent());
        Alert alert = driver.switchTo().alert();
        String alertMessage= driver.switchTo().alert().getText();
        alert.accept();
        System.out.println(alertMessage);
    }

    public void deleteABookInProfile(String bookTitle) {
        wait = new WebDriverWait(driver, 10);
        driver.findElement(By.xpath(loginLinkXPath)).click();
        driver.findElement(By.xpath(viewYourProfileLinkXPath)).click();
        await("Search bar not visible").atMost(20, TimeUnit.SECONDS).until(driver.findElement(By.id(bookstorepage.searchBarID))::isDisplayed);
        bookstorepage.searchBookTitle(bookTitle);
        driver.findElement(By.id(deleteIconID)).click();
        driver.findElement(By.id(deleteSingleBookPopupOKBtnID)).click();
        wait.until(ExpectedConditions.alertIsPresent());
        String alertMessage = driver.switchTo().alert().getText();
        driver.switchTo().alert().accept();
        Assertions.assertEquals("Book deleted.", alertMessage);

    }

    public void verifyBookIsNotAvailableInCollection(String bookTitle) {
        boolean isBookRemoved = false;
        await("Search bar not visible").atMost(20, TimeUnit.SECONDS).until(driver.findElement(By.id(bookstorepage.searchBarID))::isDisplayed);
        bookstorepage.searchBookTitle(bookTitle);
        List<WebElement> list1= driver.findElements(By.xpath("//*[contains(text(),'No rows')]"));
        if ( list1.size() > 0){
            System.out.println("Text: " + noBooksInCollectionText + ". " +bookTitle+ " is not present. ");
            isBookRemoved = true;
        } else {
            System.out.println("Text: " + noBooksInCollectionText + ". " +bookTitle+ " is present. ");
        }
        Assertions.assertTrue(isBookRemoved, "" +bookTitle+ " has not been removed from collection");
    }

}
