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

public class HomePage extends DriverBase {
 	public DriverBase DB = new DriverBase();
	private HomePage HomePage;
	public static String propertyFilePath = System.getProperty("user.dir") + File.separator + "config.properties";
	public static Properties prop = new Properties();
	public Listener ls = new Listener();
	public boolean flag;
	public static CommonFunctions cf = new CommonFunctions();
	public static ContactPage cp = new ContactPage();
	public static HomePage hp = new HomePage();
	public static CartPage cp1 = new CartPage();
	public static ShopPage sp = new ShopPage();

		
		public boolean HomePageStartShop() throws FileNotFoundException, IOException, InterruptedException {
			flag = DB.waitForElement(HomePageLoc.HomePageStartShoppingButton, ElementType.Xpath);
			if(flag) {
				System.out.println("Shopping Link button appeared");
			}else {
				System.out.println("Shopping Link button didnt appeared");
			}
			return flag;
		}		
		
		public boolean ClickStartShopping() {
		    try {
		        WebElement startShopping = driver.findElement(By.xpath(HomePageLoc.HomePageStartShoppingButton));
		        //System.out.println("Button found");
		        startShopping.click();
		        //System.out.println("Button clicked");
		        return true;
		    } catch (Exception e) {
		        e.printStackTrace();
		        return false;
		    }
		}
		
}