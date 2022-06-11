package com.qa.opencart.tests;

import java.util.Random;

import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.qa.opencart.base.BaseTest;
import com.qa.opencart.constants.Constants;
import com.qa.opencart.util.ExcelUtil;

public class RegisterPageTest extends BaseTest{
	
	@BeforeTest
	public void registersetup() {
		registerPage = loginPage.goToRegisterPage();
	}
	
	@DataProvider
	public Object[][] getRegisterTestData() {
		Object regData[][] = ExcelUtil.getTestData(Constants.REGISTER_SHEET_NAME);
		return regData;
	}
	
	public String getRandomEamil() {
		Random random =  new Random();
		String email = "marchautomation"+random.nextInt(1000)+"@gmail.com";
		return email;
	}
//	(String firstname, String lastname, String email, String telephone, String address1, String city, String postcode,
//			String country, String region, String password, String subscribe) 

	
	@Test(dataProvider = "getRegisterTestData")
	public void registerUserTest(String firstname, String lastname, String telephone, String password, String subscribe) {
		
		Assert.assertTrue(registerPage.registerUser(firstname, lastname, getRandomEamil(), telephone, password, subscribe));
		
	}



}
