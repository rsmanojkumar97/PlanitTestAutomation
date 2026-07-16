package utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Properties;
import java.util.Set;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.io.FileUtils;
//import org.json.simple.JSONObject;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.safari.SafariOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.Reporter;

import io.github.bonigarcia.wdm.WebDriverManager;

public class DriverBase extends Listener {
	public static WebDriver driver;
	protected static WebDriverWait wait;
	public static JavascriptExecutor js = (JavascriptExecutor) driver;
	String platformName = System.getProperty("platformName");
	static Boolean flag = true;
//	public static JSONObject requestParams;
//	public static RequestSpecification request;
//	public static JsonPath jsonPathEvaluator;
	public static Properties prop = new Properties();
	private static String propertyFilePath;
	public static String starttime;
	public static String executiontime;
	public static String endtime;
	public static long executionstartdatetimestamp;
	public static String startdatetimestamp;
	public static String enddatetimestamp;
	public static Map<String, String> commonCapabilities1;

	public enum ElementType {
		Id, CssSelector, ClassName, Name, LinkText, Xpath, Text, PartialLinkText
	}

	/**
	 * Method will instantiate the Pcloudy execution and start the driver
	 *
	
	 * @throws FileNotFoundException
	 * @throws IOException
	 * @throws Exception
	 */
	public static void browserOpen() throws FileNotFoundException, IOException, Exception {
		if (System.getProperty("platformName").equals("Firefox")
				|| System.getProperty("platformName").equalsIgnoreCase("firefox")) {
			FirefoxProfile profile = new FirefoxProfile();
			System.out.println("Driver initialized: " + driver);
			profile.setPreference("browser.download.manager.closeWhenDone", true);
			profile.setPreference("pdfjs.disabled", true);
			WebDriverManager.firefoxdriver().setup();
			//driver = new FirefoxDriver();
			driver.manage().window().maximize();
			long waitTime = 10;
			driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(waitTime));
			driver.manage().window().maximize();
			driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(waitTime));
		} else if (System.getProperty("platformName").equals("chrome")
				|| System.getProperty("platformName").equalsIgnoreCase("CHROME")) {
	 		WebDriverManager.chromedriver().setup();
	 		//WebDriverManager.chromedriver().clearDriverCache().setup();
			//WebDriverManager.chromedriver().proxy("http://proxy.skytv.co.nz:8082").setup();
			ChromeOptions options = new ChromeOptions();
//			options.addArguments("test-type");
//			options.addArguments("disable-infobars");
//	        options.addArguments("--remote-allow-origins=*");
			options.addArguments("--no-sandbox"); // Bypass OS security model
			options.addArguments("--disable-dev-shm-usage");// overcome limited resource problems
			driver = new ChromeDriver(options);
			System.out.println("Driver initialized: " + driver);
			long waitTime = 20;
			driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(waitTime));
			driver.manage().window().maximize();
			wait = new WebDriverWait(driver, Duration.ofSeconds(waitTime));
		} else if (System.getProperty("platformName").equals("safari")
				|| System.getProperty("platformName").equalsIgnoreCase("SAFARI")){
			//WebDriverManager.safaridriver().setup();
			SafariOptions safariOptions = new SafariOptions();
			System.out.println("Driver initialized: " + driver);
			//safariOptions.Session(true);
			//safariOptions.useCleanSession(true);
			safariOptions.setUseTechnologyPreview(true);
			WebDriver driver = new SafariDriver(safariOptions);
			driver = new SafariDriver();
			long waitTime = 20;
			driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(waitTime));
			driver.manage().window().maximize();
			wait = new WebDriverWait(driver, Duration.ofSeconds(waitTime));
		}else if (System.getProperty("platformName").equals("edge")
				|| System.getProperty("platformName").equalsIgnoreCase("EDGE")){
			EdgeOptions options = new EdgeOptions();
			System.out.println("Driver initialized: " + driver);
			// Add arguments to the options for customization (similar to ChromeOptions)
	        options.addArguments("--no-sandbox"); // Bypass OS security model
	        options.addArguments("--disable-dev-shm-usage"); // Overcome limited resource problems
	        options.addArguments("--disable-infobars"); // Disable infobars (if required)
	        options.addArguments("--remote-allow-origins=*"); // Allow cross-origin requests
	        options.addArguments("test-type"); // Disable test-type (if needed)
	        driver = new EdgeDriver(options);
	        long waitTime = 20;
	        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(waitTime)); // Implicit wait
	        driver.manage().window().maximize(); // Maximize the browser window
	        wait = new WebDriverWait(driver, Duration.ofSeconds(waitTime));
		}
	}

	/**
	 * Quit the browser
	 *
	
	 */
	public void tearDown() {
		driver.quit();

	}

	/**
	 * Wait for an Element to be visible
	 *
	
	 * @param element
	 * @param elementType
	 * @return boolean - element present/not
	 */
	public boolean waitForElement(String element, ElementType elementType) {
		try {
			switch (elementType) {
			case Id: {
				flag = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(element))).isDisplayed();
				break;
			}

			case Name: {
				flag = wait.until(ExpectedConditions.visibilityOfElementLocated(By.name(element))).isDisplayed();
				break;
			}

			case Xpath: {
				flag = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(element))).isDisplayed();
				break;
			}

			case CssSelector: {
				flag = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(element))).isDisplayed();
				break;
			}

			case ClassName: {
				flag = wait.until(ExpectedConditions.visibilityOfElementLocated(By.className(element))).isDisplayed();
				break;
			}

			case LinkText: {
				flag = wait.until(ExpectedConditions.visibilityOfElementLocated(By.linkText(element))).isDisplayed();
				break;
			}
			default:
				break;
			}
		} catch (Exception e) {
//			reportLog("Element is not found in the page " + element + " Exception " + e);
//			Reporter.log("Element is not found in the page " + element + " Exception " + e, true);
			return false;
		}
		return flag;
	}


	
	/**
	 * Wait for element to be clickable
	 *
	 * @param element
	 * @param elementType
	 * @return
	 */
	public Boolean waitForClickElement(String element, ElementType elementType) {
		try {
			switch (elementType) {
			case Id: {
				flag = wait.until(ExpectedConditions.elementToBeClickable(By.id(element))).isDisplayed();
				break;
			}

			case Name: {
				flag = wait.until(ExpectedConditions.elementToBeClickable(By.name(element))).isDisplayed();
				break;
			}

			case Xpath: {
				flag = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(element))).isDisplayed();
				break;
			}

			case CssSelector: {
				flag = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(element))).isDisplayed();
				break;
			}

			case ClassName: {
				flag = wait.until(ExpectedConditions.elementToBeClickable(By.className(element))).isDisplayed();
				break;
			}

			case LinkText: {
				flag = wait.until(ExpectedConditions.elementToBeClickable(By.linkText(element))).isDisplayed();
				break;
			}
			default:
				break;
			}
		} catch (Exception e) {
			System.out.println(element);
//			reportLog("Element is not found in the page " + element + " Exception " + e);
//			Reporter.log("Element is not found in the page " + element + " Exception " + e, true);
			return false;
		}
		return flag;
	}

	/**
	 * click an element
	 *
	
	 * @param element
	 * @param elementType
	 * @return boolean - element present/not
	 */
	public Boolean clickAnElement(String element, ElementType elementType, String elementname) {
		try {
			switch (elementType) {
			case Id: {
				driver.findElement(By.id(element)).click();
			}

			case Name: {
				driver.findElement(By.name(element)).click();
				break;
			}

			case Xpath: {
				driver.findElement(By.xpath(element)).click();
				break;
			}

			case CssSelector: {
				driver.findElement(By.cssSelector(element)).click();
				break;
			}

			case ClassName: {
				driver.findElement(By.className(element)).click();
				break;
			}

			case LinkText: {
				driver.findElement(By.linkText(element)).click();
				break;
			}

			case Text: {
				WebElement element1 = driver.findElement(By.xpath("//*[text()='" + element + "']"));
				((JavascriptExecutor) driver).executeScript("arguments[0].click();", element1);
				break;
			}
			default:
				break;
			}
		} catch (Exception e) {
			System.out.println(element);
			reportLog("Unable to click : " + elementname + element + " in the page, with the Exception: " + e);
			Reporter.log("Unable to click : " + elementname + element + " in the page, with the Exception: " + e, true);
			return false;
		}
		return true;
	}

	/**
	 * Enter Text
	 *
	
	 * @param element
	 * @param value
	 * @param elementType
	 * @return boolean - element present/not
	 */
	public Boolean sendTextToAnElement(String element, String value, ElementType elementType) {
		try {
			switch (elementType) {
			case Id: {
				driver.findElement(By.id(element)).sendKeys(value);
				break;
			}

			case Name: {
				driver.findElement(By.name(element)).sendKeys(value);
				break;
			}

			case Xpath: {
				driver.findElement(By.xpath(element)).sendKeys(value);
				break;
			}

			case CssSelector: {
				driver.findElement(By.cssSelector(element)).sendKeys(value);
				break;
			}

			case ClassName: {
				driver.findElement(By.className(element)).sendKeys(value);
				break;
			}

			case LinkText: {
				driver.findElement(By.linkText(element)).sendKeys(value);
				break;
			}
			default:
				break;
			}
		} catch (Exception e) {
			System.out.println(element);
//			reportLog("Unable to send text to an element: " + element + " in the page, with the Exception: " + e);
//			Reporter.log("Unable to send text to an element: " + element + " in the page, with the Exception: " + e,
//					true);
			return false;
		}
		return true;
	}

	/**
	 * Go to specific URL
	 *
	
	 * @param url
	 * @throws InterruptedException
	 */
	public void goToURL(String url) throws InterruptedException {
		driver.get(url);
		driver.manage().deleteAllCookies();
		driver.navigate().refresh();
		Thread.sleep(5000);

	}

	/**
	 * Scroll To Element
	 *
	
	 * @param element
	 */
	public void scrollToAnElement(String element) {
		try {
			WebElement scrollElement = driver.findElement(By.xpath(element));
			((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(false)", scrollElement);
		} catch (Exception e) {
			System.out.println(element);
//			reportLog("Unable to scroll to an element: " + element + " in the page, with the Exception: " + e);
//			Reporter.log("Unable to scroll to an element: " + element + " in the page, with the Exception: " + e, true);
		}
	}


	/**
	 * Will return the Text
	 *
	
	 * @param element
	 * @param elementtype
	 * @return string
	 */
	public String getText(String element, ElementType elementtype) {
		String temp = null;
		try {
			if (elementtype == ElementType.Id)
				temp = driver.findElement(By.id(element)).getText();
			else if (elementtype == ElementType.Name)
				temp = driver.findElement(By.name(element)).getText();
			else if (elementtype == ElementType.Xpath)
				temp = driver.findElement(By.xpath(element)).getText();
			else if (elementtype == ElementType.CssSelector)
				temp = driver.findElement(By.cssSelector(element)).getText();
			else if (elementtype == ElementType.ClassName)
				temp = driver.findElement(By.className(element)).getText();
		} catch (Exception e) {
			System.out.println(element);
//			reportLog("Unable to get the text from the Element: " + element + " Exception: " + e);
//			Reporter.log("Unable to get the text from the Element: " + element + " Exception: " + e);
		}
		return temp;
	}

	/**
	 * Quit the browser
	 *
	
	 */
	public void quitBrowser() {
		tearDown();
	}

	/**
	 * Execution Time Start
	 *
	 * @throws Exception
	 */
	public void executionTimeStart() throws Exception {
		DateTimeFormatter datetimeformat = DateTimeFormatter.ofPattern("HH:mm:ss");
		LocalDateTime now = LocalDateTime.now();
		starttime = datetimeformat.format(now);
		System.out.println("Start Time: " + starttime);
		DateTimeFormatter datetimestamp = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss");
		LocalDateTime stamp = LocalDateTime.now();
		startdatetimestamp = datetimestamp.format(stamp);
		Date date1 = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse(startdatetimestamp);
		executionstartdatetimestamp = date1.getTime();
	}

	/**
	 * Execution Time End
	 *
	 * @throws Exception
	 */
	public void executionTimeEnd() throws Exception {
		DateTimeFormatter datetimeformat = DateTimeFormatter.ofPattern("HH:mm:ss");
		LocalDateTime localtime = LocalDateTime.now();
		endtime = datetimeformat.format(localtime);
		System.out.println("End Time: " +  endtime);
		SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
		Date startdate = format.parse(starttime);
		Date enddate = format.parse(endtime);
		long difference = enddate.getTime() - startdate.getTime();
		Date timeDifference = new Date(difference);
		SimpleDateFormat simpledateformat = new SimpleDateFormat("HH:mm:ss:sss");
		simpledateformat.setTimeZone(TimeZone.getTimeZone("GMT"));
		executiontime = simpledateformat.format(timeDifference);
		DateTimeFormatter datetimestamp = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss");
		LocalDateTime stamp = LocalDateTime.now();
		enddatetimestamp = datetimestamp.format(stamp);
	}
	public static String extent_captureScreenshot(WebDriver driver) throws IOException {
		 
		File srcFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		File destinationFilepath = new File("src/../Screenshots/" + System.currentTimeMillis() + ".png");
 
		String absolutepathlocation = destinationFilepath.getAbsolutePath();
		FileUtils.copyFile(srcFile, destinationFilepath);
 
		return absolutepathlocation;
	}
	
	public String getAttribute(String element, ElementType elementType, String elementname) {
		String element1 = null;
		try {
			switch (elementType) {
			case Id: {
				 element1 = driver.findElement(By.id(element)).getAttribute(elementname);
			}

			case Name: {
				element1 = driver.findElement(By.name(element)).getAttribute(elementname);
				break;
			}

			case Xpath: {
				element1 = driver.findElement(By.xpath(element)).getAttribute(elementname);
				break;
			}

			case CssSelector: {
				element1 = driver.findElement(By.cssSelector(element)).getAttribute(elementname);
				break;
			}

			case ClassName: {
				element1 = driver.findElement(By.className(element)).getAttribute(elementname);
				break;
			}

			case LinkText: {
				element1 = driver.findElement(By.linkText(element)).getAttribute(elementname);
				break;
			}

			case Text: {
				element1 = driver.findElement(By.xpath("//*[text()='" + element + "']")).getAttribute(elementname);
				break;
			}
			default:
				break;
			}
		} catch (Exception e) {
			System.out.println(element);
//			reportLog("Unable to click : " + elementname + element + " in the page, with the Exception: " + e);
//			Reporter.log("Unable to click : " + elementname + element + " in the page, with the Exception: " + e, true);
		}
		return element1;
		}
}