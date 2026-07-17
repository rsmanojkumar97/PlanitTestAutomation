package com.testing.qa.PlanitTestAutomation;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern; 

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.sikuli.guide.ClickableWindow;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import commonFunctions.CartPage;
import commonFunctions.CommonFunctions;
import commonFunctions.ContactPage;
import commonFunctions.HomePage;
import commonFunctions.ShopPage;
import utils.DriverBase;
import utils.DriverBase.ElementType;
import utils.Listener;
import utils.Mailer;
import locators.HomePageLoc;
import locators.MenuLinksLoc;

public class RegressionTest extends Listener {
	public static CommonFunctions cf = new CommonFunctions();
	public static ContactPage cp = new ContactPage();
	public static HomePage hp = new HomePage();
	public static CartPage cp1 = new CartPage();
	public static ShopPage sp = new ShopPage();
	public static boolean flag;
	public Listener ls = new Listener();
	public DriverBase DB = new DriverBase();
	public Mailer Mail = new Mailer();

	@BeforeClass
	public void BrowserOpen() throws FileNotFoundException, IOException, Exception {
		System.out.println("Browser Open");
		DB.executionTimeStart();
		cf.VerifyBrowserOpen();
		System.out.println("Planit application launched Successfully");
	}
	
	
	@Test (description = "Test case 1:\r\n"
			+ "From the home page go to contact page\r\n"
			+ "Click submit button\r\n"
			+ "Verify error messages\r\n"
			+ "Populate mandatory fields\r\n"
			+ "Validate errors are gone",priority =1)
	public void TestCase1() throws FileNotFoundException, IOException, Exception {
		System.out.println("Finding contact page link");
//From the home page go to contact page
		flag = cf.ContactPage();
		if (flag) {
			System.out.println("Found the contact page link");
			ls.reportLogPass("Found the contact page link");
//Click submit button
			flag = cp.Submit();
			if(flag) {
				ls.reportLogPass("Successfully submited");
				System.out.println("Successfully submited");
//Verify error messages
				flag = cp.ForenameMandotryFields();
				if(flag) {
				    System.out.println("Forename error message is displayed");
					ls.reportLogPass("Forename error message is displayed");
					}
					assertEquals(flag, true);

					flag = cp.EmailMandotryFields();
					if(flag) {
					    System.out.println("Email error message is displayed");
						ls.reportLogPass("Email error message is displayed");
					}
					assertEquals(flag, true);
	
					flag = cp.MessageMandotryFields();
					if(flag) {
					    System.out.println("Message error message is displayed");
						ls.reportLogPass("Message error message is displayed");
					}
					assertEquals(flag, true);
//Populate mandatory fields
					flag = cp.PopulateMandotryFields();
					assertEquals(flag,true);
						System.out.println("Values Popluated");
						ls.reportLogPass("Values Popluated");
//Validate errors are gone
							flag = cp.ForenameMandotryFields();
							assertEquals(flag, false);
						    System.out.println("Forename error message is not displayed");
							ls.reportLogPass("Forename error message is not displayed");
							
							flag = cp.EmailMandotryFields();
							assertEquals(flag, false);
							System.out.println("Email error message is not displayed");
							ls.reportLogPass("Email error message is not displayed");
							
							flag = cp.MessageMandotryFields();
							assertEquals(flag, false);	
							System.out.println("Message error message is not displayed");
							ls.reportLogPass("Message error message is not displayed");
//Navigate to Home page
						flag = cf.NavigateHomePage();	
			}else {
				System.out.println("Failed to click the submit button");
				ls.reportLogFail("Failed to click the submit button");
			}
		}else {
			System.out.println("Failed to find the contact page link");
			ls.reportLogFail("Failed to find the contact page link");
		}
	}
	
	@Test (priority = 2) //invocationCount = 5
	public void TestCase2()  throws FileNotFoundException, IOException, Exception {
		for (int i = 1; i <= 5; i++) {
	   System.out.println("******** Execution " + i + " ********");
	   ls.reportLog("******** Execution " + i + " ********");
		System.out.println("Finding contact page link");
		//From the home page go to contact page
				flag = cf.ContactPage();
				if(flag) {
					System.out.println("Finding contact page link");
					ls.reportLogPass("Finding contact page link");
		//Populate mandatory fields
					flag = cp.PopulateMandotryFields();
					if(flag) {
						System.out.println("Values Popluated");
						ls.reportLogPass("Values Popluated");
		//Click submit button
						flag = cp.Submit();
						if(flag) {
							System.out.println("Submit button clicked");
							ls.reportLogPass("Submit button clicked");
		//Validate successful submission message
							flag =cp.ThanksPage();
							if(flag) {
								flag = cf.NavigateHomePage();	
								System.out.println("Thanks Page appeared");
								ls.reportLogPass("Thanks Page appeared");
							}else {
								System.out.println("Thanks didnt appear");
								ls.reportLogFail("Thanks didnt appear");
							}							
						}else {
							System.out.println("Submit button didnt clicked");
							ls.reportLogFail("Submit button didnt clicked");
						}
					}else {
						System.out.println("Values didnt Popluated");
						ls.reportLogFail("Values didnt Popluated");
					}
				}else {
					System.out.println("Failed to click the submit button");
					ls.reportLogFail("Failed to click the submit button");
				}
				System.out.println("Execution " + i + " Passed");
				ls.reportLog("Execution " + i + " Passed");
			}
	}
	
	@Test (priority = 3)
	public void TestCase3()  throws FileNotFoundException, IOException, Exception {
		System.out.println("Finding shop button from home page");
		//Go to the cart page
				flag = hp.ClickStartShopping();
				Assert.assertTrue(flag, "Unable to click Start Shopping button");
				ls.reportLogPass("Start shop button clicked");
				if(flag) {
					System.out.println("Finding buy button");
					ls.reportLogPass("Finding buy button");
					flag = cp1.buy();
					if(flag) {
						System.out.println("Purchased the products");
						ls.reportLogPass("Purchased the products");
		//Verify the subtotal for each product is correct				
						flag = cf.goToCart();
						if(flag) {
							System.out.println("Navigate to Cart page");
							ls.reportLogPass("Navigate to Cart page");
		//Verify the price for each product
		//Verify that total = sum(sub totals)
							flag =cp1.ExpectedTotal();
							if(flag) {
								System.out.println("Successfully verified the total products");
								ls.reportLogPass("Successfully verified the total products");
							}else {
								System.out.println("Thanks didnt appear");
								ls.reportLogFail("Thanks didnt appear");
							}							
						}else {
							System.out.println("Submit button didnt clicked");
							ls.reportLogFail("Submit button didnt clicked");
						}
					}else {
						System.out.println("Values didnt Popluated");
						ls.reportLogFail("Values didnt Popluated");
					}
				}else {
					System.out.println("Failed to click the shopping button");
					ls.reportLogFail("Failed to click the shopping button");

			}
	}
	
	@AfterClass
	 public void tearDown() {
        DB.quitBrowser();
    }
	
	@AfterSuite
	public void MailSendReport() throws Exception {
		DB.executionTimeEnd();
		Mail.execute("Regression Test");
	}
	
}

//Commands
//Kill chrome driver for windows
//Browser Kill & Driver Kill	taskkill /F /IM chromedriver.exe /T

//Kill Firefox driver for windows
//Browser Kill 		taskkill /F /IM firefox.exe
//Driver Kill		taskkill /F /IM geckodriver.exe

//Kill Edge Driver for windows
//Browser Kill		taskkill /F /IM msedge.exe
//Driver Kill		taskkill /F /IM msedgedriver.exe

//Kill Safari Driver for Mac
//pkill -f safaridriver

//Kill Mozilla Driver for Mac
//pkill -f firefox
//pkill -f geckodriver

