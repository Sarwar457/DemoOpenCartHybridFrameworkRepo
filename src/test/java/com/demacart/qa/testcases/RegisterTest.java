package com.demacart.qa.testcases;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.DemoCart.qa.pages.AccountSuccessPage;
import com.DemoCart.qa.pages.HomePage;
import com.DemoCart.qa.pages.RegisterPage;
import com.DemoOpenCart.qa.base.Base;
import com.DemoOpenCart.qa.utils.Utilities;

public class RegisterTest extends Base {
	
	public RegisterTest() {
		super();
	}
	
	public WebDriver driver;
	RegisterPage registerPage;
	AccountSuccessPage accountSuccessPage;
	
	@BeforeMethod
	public void setup() {
		
		driver = initializeBrowserAndOpenApplicationUrl(prop.getProperty("browserName"));
		HomePage homePage = new HomePage(driver);
		homePage.clickOnMyAccount();
		registerPage = homePage.selectRegisterOption();
		
	}
	
	@AfterMethod
	public void tearDown() {
		driver.quit();
	}
	
	@Test(priority=1)
	public void verifyRegisterAnAccountWithMandatoryFields() {
		
		registerPage.enterFirstName(dataProp.getProperty("firstName"));
		registerPage.enterLastName(dataProp.getProperty("lastName"));
		registerPage.enterEmailAddress(Utilities.generateEmailWithTimeStamp());
		registerPage.enterTelephoneNumber(dataProp.getProperty("telephoneNumber"));
		registerPage.enterPassword(prop.getProperty("validPassword"));
		registerPage.enterConfirmPassword(prop.getProperty("validPassword"));
		registerPage.selectPrivacyPolicy();
		accountSuccessPage = registerPage.clickOnContine();
		String actualSuccessHeading = accountSuccessPage.retrieveAccountSuccessPageHeading();
		Assert.assertEquals(actualSuccessHeading,dataProp.getProperty("accountSuccessfullyCreatedHeading"),"Account Success page is not displayed");
		
		
		
	}
	
	@Test(priority=2)
	public void verifyRegisterAnAccountWithAllFields() {
		
		registerPage.enterFirstName(dataProp.getProperty("firstName"));
		registerPage.enterLastName(dataProp.getProperty("lastName"));
		registerPage.enterEmailAddress(Utilities.generateEmailWithTimeStamp());
		registerPage.enterTelephoneNumber(dataProp.getProperty("telephoneNumber"));
		registerPage.enterPassword(prop.getProperty("validPassword"));
		registerPage.enterConfirmPassword(prop.getProperty("validPassword"));
		registerPage.selectNewsLetter();
		registerPage.selectPrivacyPolicy();
		accountSuccessPage = registerPage.clickOnContine();
		String actualSuccessHeading = accountSuccessPage.retrieveAccountSuccessPageHeading();
		Assert.assertEquals(actualSuccessHeading,dataProp.getProperty("accountSuccessfullyCreatedHeading"),"Account Success page is not displayed");
		
		
		
	}
	
	@Test(priority=3)
	public void verifyRegisterAnAccountWithExistingEmailAddress() {
		
		registerPage.enterFirstName(dataProp.getProperty("firstName"));
		registerPage.enterLastName(dataProp.getProperty("lastName"));
		registerPage.enterEmailAddress(prop.getProperty("validEmail"));
		registerPage.enterTelephoneNumber(dataProp.getProperty("telephoneNumber"));
		registerPage.enterPassword(prop.getProperty("validPassword"));
		registerPage.enterConfirmPassword(prop.getProperty("validPassword"));
		registerPage.selectNewsLetter();
		registerPage.selectPrivacyPolicy();
		registerPage.clickOnContine();
		
		Assert.assertTrue(registerPage.retrieveDuplicateEmailAddressWarning().contains(dataProp.getProperty("duplicateEmailWarning")),"Warning message is not displayed");
		
		
		
	}
	
	@Test(priority=4)
	public void verifyRegisterAnAccountWithoutFillingAnyDetails() {
		
		registerPage.clickOnContine();
		
		Assert.assertTrue(registerPage.retreivePrivacyPolicyWarning().contains(dataProp.getProperty("privacyPolicyWarning")),"Privacy policy warning is not dsiplayed");
		Assert.assertTrue(registerPage.retrieveFirstNameWarning().contains(dataProp.getProperty("firstNameWarning")),"FirstName Warning msg is not displayed");
		Assert.assertTrue(registerPage.retrieveLastNameWarning().contains(dataProp.getProperty("lastNameWarning")),"LastName Warning msg is not displayed");
		Assert.assertTrue(registerPage.retrieveEmailWarning().contains(dataProp.getProperty("emailWarning")),"Email Warning msg is not displayed");
		Assert.assertTrue(registerPage.retrieveTelephoneNumberWarning().contains(dataProp.getProperty("telephoneWarning")),"Telephone Warning msg is not displayed");
		Assert.assertTrue(registerPage.retrievePasswordWarning().contains(dataProp.getProperty("passwordWarning")),"Password Warning msg is not displayed");
		
		
	}
	

}
