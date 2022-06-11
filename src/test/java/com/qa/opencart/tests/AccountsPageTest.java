package com.qa.opencart.tests;

import java.util.List;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.qa.opencart.base.BaseTest;
import com.qa.opencart.constants.Constants;
import com.qa.opencart.util.ExcelUtil;

import io.qameta.allure.Epic;
import io.qameta.allure.Story;


@Epic("Epic - 200: this epic is for Accounts page of open cart application")
@Story("ACCPAGE - 201: design Acccount page with various features")

public class AccountsPageTest extends BaseTest{
	
	@BeforeClass
	public void accSetup() {
		accPage = loginPage.doLogin(prop.getProperty("username").trim(), prop.getProperty("password").trim());
		//accPage = new AccountsPage(driver);
	}
	
	@Test
	public void accPageTitleTest() {
		String actTitle = accPage.getAccountsPageTitle();
		System.out.println("Acc page title is:" +actTitle);
		Assert.assertEquals(actTitle, Constants.ACCOUNT_PAGE_TITLE);
	}
	

	@Test
	public void accPageURLTest() {
		String actURL = accPage.getAccountsPageURL();
		System.out.println("Acc page url is:" +actURL);
		Assert.assertTrue(actURL.contains(Constants.ACCOUNT_PAGE_URL_FRACTION));

	}
	
	@Test
	public void accPageSectionsTest() {
		List<String> actAccSecList=accPage.getAccountsPageSectionList();
		System.out.println("Act Accounts section List:" +actAccSecList);
		Assert.assertEquals(actAccSecList, Constants.EXPECTED_ACCOUNTS_SECTION_LIST );
	}
	
	@Test
	public void logoutLinkTest() {
		Assert.assertTrue(accPage.isLogoutLinkExist());
	}
	
	@Test
	public void searchExistTest() {
		Assert.assertTrue(accPage.isSearchExist());
	}
	
	@Test
	public void logoutTest() {
		Assert.assertEquals(accPage.clickOnLogOut().getLogoutSuccessMessg(), Constants.LOGOUT_SUCCESS_MSG);
	}
	
	@DataProvider
	public Object[][] getSearchKey() {
		return new Object[][] {
			{"Macbook"},
			{"iMac"},
			{"Apple"},
			{"Samsung"}
		};
	}
	
	@Test(dataProvider = "getSearchKey")
	public void searchTest(String searchKey) {
		searchResPage = accPage.doSearch(searchKey);
		Assert.assertTrue(searchResPage.getSearchResultsCount()>0);
	}
	
//	@DataProvider
//	public Object[][] getProductName() {
//		return new Object[][] {
//			{"Macbook", "MacBook Pro"},
//			{"iMac", "iMac"},
//			{"Apple", "Apple Cinema 30\""},
//			{"Samsung", "Samsung SyncMaster 941BW"}
//		};
//	}
	@DataProvider
	public Object[][] getProductName() {
		return ExcelUtil.getTestData(Constants.PRODUCT_SHEET_NAME);
		
	}
	
	@Test(dataProvider = "getProductName", enabled=false)
	public void selectProductTest(String searchKey, String productName) {
		searchResPage = accPage.doSearch(searchKey);
		proInfoPage = searchResPage.selectProduct(productName);
		String productHeader = proInfoPage.getProductHeaderName();
		System.out.println("Product header: " + productHeader);
		Assert.assertEquals(productHeader, productName);
	}
	
	@DataProvider
	public Object[][] getProductData() {
		return new Object[][] {
			{"Macbook", "MacBook Pro", 4},
			{"Samsung", "Samsung SyncMaster 941BW", 1}
		};
	}
	@Test(dataProvider = "getProductData", enabled=false)
	public void productImageTest(String searchKey, String productName, int productImageCount) {
		searchResPage = accPage.doSearch(searchKey);
		proInfoPage = searchResPage.selectProduct(productName);
		Assert.assertEquals(proInfoPage.getImageCount(), productImageCount);
		
	}
	
	@Test
	public void productInfoTest() {
		searchResPage = accPage.doSearch("Macbook");
		proInfoPage = searchResPage.selectProduct("MacBook Pro");
		Map <String, String> actProductInfoMap = proInfoPage.getProductInformation();
		softAssert.assertEquals(actProductInfoMap.get("name"),"MacBook Pro");
		softAssert.assertEquals(actProductInfoMap.get("Brand"), "Apple");
		softAssert.assertEquals(actProductInfoMap.get("Availability"), "In Stock");
		softAssert.assertEquals(actProductInfoMap.get("price"), "$2,000.00");
		softAssert.assertAll();
		
	}
	
	@Test
	public void productInfoDescriptionTest() {
		searchResPage = accPage.doSearch("Macbook");
		proInfoPage = searchResPage.selectProduct("MacBook Pro");
		softAssert.assertTrue(proInfoPage.getProductInfoPageInnerText().contains("Latest Intel mobile architecture"));
		softAssert.assertTrue(proInfoPage.getProductInfoPageInnerText().contains("Connect. Create. Communicate."));
		softAssert.assertTrue(proInfoPage.getProductInfoPageInnerText().contains("Control presentations and media"));
		softAssert.assertAll();
		
	}
	
	@Test(enabled=false)
	public void addToCartTest() {
		searchResPage = accPage.doSearch("Macbook");
		proInfoPage = searchResPage.selectProduct("MacBook Pro");
		String actSuccessMsg = proInfoPage.enterQuantity("1").clickOnAddToCart().getSuccessMsg();
		System.out.println("Success message is: " +actSuccessMsg);
		Assert.assertTrue(actSuccessMsg.contains("MacBook Pro"));
		String addToCartItemText = proInfoPage.getCartItemText();
		softAssert.assertTrue(addToCartItemText.contains("1" + " item(s)"));
		
	}
	
	
	
}
