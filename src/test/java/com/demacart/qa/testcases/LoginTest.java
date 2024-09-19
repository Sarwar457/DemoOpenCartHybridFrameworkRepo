package com.demacart.qa.testcases;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.DemoCart.qa.pages.AccountPage;
import com.DemoCart.qa.pages.HomePage;
import com.DemoCart.qa.pages.LoginPage;
import com.DemoOpenCart.qa.base.Base;
import com.DemoOpenCart.qa.utils.Utilities;

public class LoginTest extends Base {
	
	LoginPage loginPage;
	
	public LoginTest() {
		super();
	}
	
	public WebDriver driver;
	
	@BeforeMethod
	public void setup() {
			
	driver = initializeBrowserAndOpenApplicationUrl(prop.getProperty("browserName"));
	HomePage homePage = new HomePage(driver);
	homePage.clickOnMyAccount();
	loginPage = homePage.selectLoginOption();
	}
	

	
	@AfterMethod
	public void tearDown() {
		driver.quit();
	}
	
	@Test(priority=1, dataProvider="validCredentialsSupplier")
	public void verifyLoginWithValidCredentials(String email, String password) {
		
		loginPage.enterEmailAddress(email);
		loginPage.enterPassword(password);
		AccountPage accountPage = loginPage.clickOnLoginButton();

		Assert.assertTrue(accountPage.getDisplayStatusAccountInformation());
		
		
	}
	
	@DataProvider(name="validCredentialsSupplier")
	public Object[][] supplyTestData() {
		
		Object[][] data = Utilities.getTestDataFromExcel("Login");
		return data;
		
	}
	
	@Test(priority=2)
	public void verifyLoginWithInvalidCredentials() {
		
		loginPage.enterEmailAddress(Utilities.generateEmailWithTimeStamp());
        loginPage.enterPassword(dataProp.getProperty("invalidPassword"));
		loginPage.clickOnLoginButton();
		
		String actualWarningMessage = loginPage.retrieveEmailPasswordNotMatchingWarningMessageText();
		String expectedWarningMessage = dataProp.getProperty("emailPasswordNoMatchWarning");
		Assert.assertTrue(actualWarningMessage.contains(expectedWarningMessage),"Expected Warning Message is not displayed");
		
	}
	
	
}
