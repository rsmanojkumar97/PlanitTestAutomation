package commonFunctions;

import static org.testng.Assert.assertTrue;

import java.util.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

//import org.apache.xmlbeans.impl.common.XmlErrorWatcher;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
//import org.sikuli.guide.ClickableWindow;
//import org.sikuli.script.FindFailed;
//import org.sikuli.script.Screen;
//import org.testng.Assert;
import org.testng.Assert;

//import io.netty.util.internal.SystemPropertyUtil;
import locators.HomePageLoc;
import locators.MenuLinksLoc;
import locators.ContactPageLoc;
import utils.DriverBase;
import utils.Listener;
import utils.DriverBase.ElementType;

public class ContactPage extends DriverBase {
 	public DriverBase DB = new DriverBase();
	private ContactPage ContactPage;
	public static String propertyFilePath = System.getProperty("user.dir") + File.separator + "config.properties";
	public static Properties prop = new Properties();
	public Listener ls = new Listener();
	public boolean flag;
	public static CommonFunctions cf = new CommonFunctions();
	public static ContactPage cp = new ContactPage();
	public static HomePage hp = new HomePage();
	public static CartPage cp1 = new CartPage();
	public static ShopPage sp = new ShopPage();

		
		public boolean ForenameMandotryFields() throws FileNotFoundException, IOException, InterruptedException  {
//		    String ForenameerrorText = driver.findElement(By.id(ContactPageLoc.ForenamefieldErr1)) .getText() .trim();
//		    return !ForenameerrorText.isEmpty();
			//System.out.println(driver.findElements(By.xpath(ContactPageLoc.ForenamefieldErr)).size() > 0);
			return driver.findElements(By.xpath(ContactPageLoc.ForenamefieldErr)).size() > 0;
		}
		
		public boolean EmailMandotryFields() throws FileNotFoundException, IOException, InterruptedException {
			return driver.findElements(By.xpath(ContactPageLoc.EmailfieldeErr)).size() > 0;
//			String EmailfielderrorText = driver.findElement(By.id(ContactPageLoc.EmailfieldeErr1)).getText().trim();
//			return !EmailfielderrorText.isEmpty();
		}
		
		public boolean MessageMandotryFields() throws FileNotFoundException, IOException, InterruptedException {
			return driver.findElements(By.xpath(ContactPageLoc.MessagefieldErr)).size() > 0;
//			String MessagefielderrorText = driver.findElement(By.id(ContactPageLoc.MessagefieldErr1)).getText().trim();
//			return !MessagefielderrorText.isEmpty();
		}
		
		public boolean PopulateMandotryFields()  throws FileNotFoundException, IOException, InterruptedException {
			//Loading files from Config.properties
			prop.load(new FileInputStream(propertyFilePath));
			flag = DB.waitForElement(ContactPageLoc.Emailfield, ElementType.Xpath);
			assertTrue(flag, "Email Field Appeared");
			
			String Forename = prop.getProperty("Forename");
			DB.sendTextToAnElement(ContactPageLoc.Forename, Forename, ElementType.Xpath);
			
			String Username = prop.getProperty("RandomEmail");
			DB.sendTextToAnElement(ContactPageLoc.Emailfield, Username, ElementType.Xpath);
			
			String Message = prop.getProperty("Message");
			DB.sendTextToAnElement(ContactPageLoc.Message, Message, ElementType.Xpath);
			
			return flag;
		}
		
		public boolean Submit() throws FileNotFoundException, IOException, InterruptedException {			
			flag = DB.waitForElement(ContactPageLoc.Submit, ElementType.Xpath);
			if(flag) {
			DB.clickAnElement(ContactPageLoc.Submit, ElementType.Xpath, "Submit button clicked");
			Thread.sleep(5000);
			//System.out.println("Submit button is clicked" + flag );
			}
			return flag;
	}
		
		public boolean Successmessage() throws FileNotFoundException, IOException, InterruptedException {
			return driver.findElements(By.xpath(ContactPageLoc.Thanksmsg)).size() > 0;
		}
		
		public boolean Backbutton() throws FileNotFoundException, IOException, InterruptedException{
			flag = DB.waitForElement(ContactPageLoc.Backbutton, ElementType.Xpath);
			if(flag) {
				DB.clickAnElement(ContactPageLoc.Backbutton, ElementType.Xpath, "back button clicked");
				Thread.sleep(5000);
			}
			return flag;
		}
		
		public boolean ThanksPage() throws FileNotFoundException, IOException, InterruptedException {
			flag = Successmessage();
			if(flag) {
				System.out.println("Success Message appeared successfully");
				flag = Backbutton();
				if(flag) {
					System.out.println("Back button appeared and clicked");
				}else {
					System.out.println("Back button didnt appear");
				}
			}else {
				System.out.println("Success Message didnt appear");
			}
			return flag;
		}
}