package com.qa.opencart.factory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.safari.SafariDriver;

import com.qa.opencart.exceptions.FrameworkException;

import io.github.bonigarcia.wdm.WebDriverManager;

public class DriverFactory {
	
	WebDriver driver;
	Properties prop;
	OptionsManager optionsManager;
	
	public static ThreadLocal<WebDriver> tdlDriver = new ThreadLocal<WebDriver>();
	/**
	 * this method is used to initialize the webdriver on the basis of given browser name
	 * @param browserName
	 * @return it returns driver
	 */
	public WebDriver init_driver (Properties prop) {
		
		String browserName = prop.getProperty("browser").trim();
	    optionsManager = new OptionsManager(prop);
		
		System.out.println("Browser name is " + browserName);
		
		if(browserName.equalsIgnoreCase("chrome")) {
			WebDriverManager.chromedriver().setup();
			//driver = new ChromeDriver(optionsManager.getchromeOptions());
			tdlDriver.set(new ChromeDriver(optionsManager.getchromeOptions()));
		}
		else if(browserName.equalsIgnoreCase("firefox")) {
			WebDriverManager.firefoxdriver().setup();
			//driver = new FirefoxDriver(optionsManager.getFirefoxOptions());
			tdlDriver.set(new FirefoxDriver(optionsManager.getFirefoxOptions()));
		}
		else if(browserName.equalsIgnoreCase("Safari")) {
			//driver = new SafariDriver();
			tdlDriver.set(new SafariDriver()); 
		}
		else {
			System.out.println("Please pass the right browser:" +browserName);
			}
		getDriver().manage().deleteAllCookies();
		getDriver().manage().window().maximize();
		getDriver().get(prop.getProperty("url").trim());
//		try {
//			Thread.sleep(2000);
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		driver.findElement(By.cssSelector("div#top-links a[title='My Account']")).click();
//		try {
//			Thread.sleep(2000);
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		driver.findElement(By.linkText("Login")).click();
		return getDriver();
		}
	
	/**
	 * get the thread local copy of the driver
	 * @return
	 */
	
	public static WebDriver getDriver() {
		return tdlDriver.get();
	}
	
	
	/**
	 * This method is used to initialize the properties
	 */
	
	public Properties init_prop() {
		prop = new Properties();
		FileInputStream ip = null;
		
		//mvn  clean install -Denv="qa"
		String envName = System.getProperty("env");
		System.out.println("Running tests on environment:" + envName);
		
		if(envName==null) {
			System.out.println("No env is given.... hence running it on QA environment");
			try {
				ip=new FileInputStream("./src/test/resources/config/qa.config.properties");
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}
		else {
			try {
			switch(envName.toLowerCase()) {
			case "qa":
				System.out.println("Running it on QA env");
				ip=new FileInputStream("./src/test/resources/config/qa.config.properties");
				break;
			case "uat":
				System.out.println("Running it on UAT env");
				ip=new FileInputStream("./src/test/resources/config/uat.config.properties");
				break;
			case "stage":
				System.out.println("Running it on STAGE env");
				ip=new FileInputStream("./src/test/resources/config/stage.config.properties");
				break;
			case "dev":
				System.out.println("Running it on DEV env");
				ip=new FileInputStream("./src/test/resources/config/dev.config.properties");
				break;
			case "prod":
				System.out.println("Running it on PROD env");
				ip=new FileInputStream("./src/test/resources/config/dev.config.properties");
				break;
			default:
				System.out.println("Please pass the right environment: " +envName);
				throw new FrameworkException("No environment is found exception...");
			}
		}catch(Exception e) {
			
			
		}
	}
		
		try {
			prop.load(ip);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return prop;
		
	}
	/*
	 * get the screenshot
	 */
	
	public String getScreenshot() {
		
		File srcFile = ((TakesScreenshot)getDriver()).getScreenshotAs(OutputType.FILE);
		String path = "./"+"screenshot/"+System.currentTimeMillis()+".png";
		File destination = new File(path);
		try {
			FileUtils.copyFile(srcFile, destination);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return path;		
		
	}
}