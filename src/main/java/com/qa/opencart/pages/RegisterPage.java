package com.qa.opencart.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.qa.opencart.constants.Constants;
import com.qa.opencart.util.ElementUtil;

public class RegisterPage {
	
	private WebDriver driver;
	private ElementUtil eleUtil;
	
	
	private By firstname = By.id("input-firstname");
	private By lastname = By.id("input-lastname");
	private By email = By.id("input-email");
	private By telephone = By.id("input-telephone");
//	private By address1 = By.id("input-address-1");
//	private By city = By.id("input-city");
//	private By postcode = By.id("input-postcode");
//	private By country = By.id("input-country");
//	private By region = By.id("input-zone");
	private By password = By.id("input-password");
	private By confirmpassword = By.id("input-confirm");
	private By subscribeYes = By.xpath("//label[@class='radio-inline']/input[@type ='radio' and @value = '1']");
	private By subscribeNo = By.xpath("//label[@class='radio-inline']/input[@type ='radio' and @value = '0']");
	private By agreeCheckBox = By.name("agree");
	private By continueBtn = By.xpath("//input[@type='submit' and @value='Continue']");
	
	private By registerSuccessMsg = By.cssSelector("div#content h1");
	
	private By logoutLink = By.linkText("Logout");
	private By registerLink = By.linkText("Register");
	
	public RegisterPage(WebDriver driver) {
		this.driver = driver;
		eleUtil = new ElementUtil(this.driver);
	}
	
	public boolean registerUser(String firstname, String lastname, String email, String telephone, String password, String subscribe) {
		
		WebElement firstName_ele = eleUtil.waitForElementVisible(this.firstname, Constants.DEFAULT_ELEMENT_TIME_OUT);
		firstName_ele.clear();
		firstName_ele.sendKeys(firstname);
		eleUtil.doSendKeys(this.lastname, lastname);
		eleUtil.doSendKeys(this.email, email);
		eleUtil.doSendKeys(this.telephone, telephone);
		eleUtil.doSendKeys(this.password, password);
		eleUtil.doSendKeys(confirmpassword, password);
		if(subscribe.equalsIgnoreCase("Yes")) {
			eleUtil.doClick(subscribeYes);
		}
		else {
			eleUtil.doClick(subscribeNo);
		}
		eleUtil.doClick(agreeCheckBox);
		eleUtil.doClick(	continueBtn);
		String successMsg = eleUtil.waitForElementVisible(registerSuccessMsg, Constants.DEFAULT_TIME_OUT).getText();
		
		if(successMsg.contains(Constants.ACCOUNT_REGISTER_SUCCESS_MSG)) {
			eleUtil.doClick(logoutLink);
			eleUtil.doClick(registerLink);
			return true;
		}
		else {
			eleUtil.doClick(logoutLink);
			eleUtil.doClick(registerLink);
			
		}
		return false;
	}
	

}
