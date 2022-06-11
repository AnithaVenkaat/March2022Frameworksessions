package com.qa.opencart.pages;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.qa.opencart.constants.Constants;
import com.qa.opencart.util.ElementUtil;

public class AccountsPage {

	private WebDriver driver;
	private ElementUtil eleUtil;
	
	private By header = By.xpath("//*[@id=\"logo\"]/a");
	private By logoutLink = By.linkText("Logout");
	private By sectionHeaders = By.cssSelector("div#content h2");
	private By search = By.name("search");
	private By searchIcon = By.cssSelector("div#search button");
	
	public AccountsPage(WebDriver driver) {
		this.driver = driver;
		eleUtil = new ElementUtil(this.driver);
	} 
	
	public String getAccountsPageTitle() {
		return eleUtil.waitForTitleIs(Constants.ACCOUNT_PAGE_TITLE, Constants.DEFAULT_TIME_OUT);
	}
	
	public String getAccountsPageURL() {
		return eleUtil.waitForUrlContains(Constants.ACCOUNT_PAGE_URL_FRACTION, Constants.DEFAULT_TIME_OUT);
	}
	
	public List<String> getAccountsPageSectionList() {
		List<WebElement> secList = eleUtil.getElements(sectionHeaders);
		List<String> secVal = new ArrayList<String>();
		for(WebElement e: secList) {
			String text = e.getText();
			secVal.add(text);
		}
		return secVal;
	}
	
	public boolean isLogoutLinkExist() {
		return eleUtil.waitForElementVisible(header, Constants.DEFAULT_ELEMENT_TIME_OUT).isDisplayed();
	}
	
	public LoginPage clickOnLogOut() {
		if(isLogoutLinkExist()) {
			eleUtil.doClick(logoutLink);
		}
		return new LoginPage(driver);
	}
	
	public boolean isSearchExist() {
		return eleUtil.waitForElementVisible(search, Constants.DEFAULT_ELEMENT_TIME_OUT).isDisplayed();
				}
	
	public SearchResultsPage doSearch(String searchKey) {
		if(isSearchExist()) {
		eleUtil.doSendKeys(search, searchKey);
		eleUtil.doClick(searchIcon);
		return new SearchResultsPage(driver);
		}
		return null;
	}
	

	
}
