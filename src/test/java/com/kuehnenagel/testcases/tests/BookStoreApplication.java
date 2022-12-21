package com.kuehnenagel.testcases.tests;

import com.kuehnenagel.testcases.pages.bookStorePage;
import com.kuehnenagel.testcases.pages.loginPage;
import com.kuehnenagel.testcases.pages.profilePage;
import com.kuehnenagel.testcases.utils.TestBase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class BookStoreApplication extends TestBase {

    loginPage logPage = new loginPage();
    profilePage profPage = new profilePage();
    bookStorePage bookstorepage = new bookStorePage();

    @BeforeEach
    public void deleteAllBooksInCollection() throws IOException {
        logPage.login();
        profPage.deleteAllBooksInProfile();
    }

    @Test
    public void verifyBookAddedToCollection() throws IOException {
        profPage.clickGoToBookStoreBtn();
        bookstorepage.verifyBookStorePageTitle();
        Properties propFile = new Properties();
        FileInputStream readPropFile = new FileInputStream(System.getProperty("user.dir")+"/src/test/resources/configs/TestData.properties");
        propFile.load(readPropFile);
        bookstorepage.searchBookTitle(propFile.getProperty("booktitle"));
        bookstorepage.clickBookTitle(propFile.getProperty("booktitle"));
        bookstorepage.addBookToCollection();
        profPage.verifyBookNameInCollection(propFile.getProperty("booktitle"));
    }

    @Test
    public void verifyBookRemovedFromCollection() throws IOException {
        profPage.clickGoToBookStoreBtn();
        bookstorepage.verifyBookStorePageTitle();
        Properties propFile = new Properties();
        FileInputStream readPropFile = new FileInputStream(System.getProperty("user.dir")+"/src/test/resources/configs/TestData.properties");
        propFile.load(readPropFile);
        bookstorepage.searchBookTitle(propFile.getProperty("booktitle"));
        bookstorepage.clickBookTitle(propFile.getProperty("booktitle"));
        bookstorepage.addBookToCollection();
        profPage.deleteABookInProfile(propFile.getProperty("booktitle"));
        profPage.verifyBookIsNotAvailableInCollection(propFile.getProperty("booktitle"));
    }

}
