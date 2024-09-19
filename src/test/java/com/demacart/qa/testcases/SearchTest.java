package com.demacart.qa.testcases;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.DemoCart.qa.pages.HomePage;
import com.DemoCart.qa.pages.SearchPage;
import com.DemoOpenCart.qa.base.Base;

public class SearchTest extends Base {
	
	public SearchTest() {
		super();
	}
	
	public WebDriver driver;
	SearchPage searchPage;
	
	@BeforeMethod
	public void setup() {
			
	driver = initializeBrowserAndOpenApplicationUrl(prop.getProperty("browserName"));
	
	}
	
	@AfterMethod
	public void tearDown() {
		
		driver.quit();
	}
	@Test(priority=1)
	public void verifySearchWithValidProduct() {
		
		HomePage homePage = new HomePage(driver);
		homePage.enterProductIntoSearchBoxField(dataProp.getProperty("validProduct"));
		SearchPage searchPage = homePage.clickOnSearchButton();
		
		Assert.assertTrue(searchPage.displayStatusOfHPValidProduct());
		
	}
	
	@Test(priority=2)
	public void verifySearchWithInValidProduct() {
		
		HomePage homePage = new HomePage(driver);
		homePage.enterProductIntoSearchBoxField(dataProp.getProperty("invalidProduct"));
        searchPage = homePage.clickOnSearchButton();
		
		String actualSearchMessage = searchPage.retrievNoProductMessageText();
		Assert.assertEquals(actualSearchMessage,"abcd", "No product msg in search result is not displayed");
	}
	
	@Test(priority=3, dependsOnMethods= {"verifySearchWithInValidProduct","verifySearchWithValidProduct"})
	public void verifySearchWithoutAnyProduct() {
		
		HomePage homePage = new HomePage(driver);
		searchPage = homePage.clickOnSearchButton();
		
		String actualSearchMessage = searchPage.retrievNoProductMessageText();
		Assert.assertEquals(actualSearchMessage,dataProp.getProperty("NoProductTextInDSearchResults"), "No product msg in search result is not displayed");
	}
	}
		

