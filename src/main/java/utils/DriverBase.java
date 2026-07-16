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

//import io.restassured.path.json.JsonPath;
//import io.restassured.specification.RequestSpecification;

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
	public static String access_Token;
	public static final String USERNAME = "praveenkumar_gCN3XW";
	public static final String AUTOMATE_KEY = "qavoSkiusLaX4KksFjpK";
	public static final String URL = "https://" + USERNAME + ":" + AUTOMATE_KEY + "@hub-cloud.browserstack.com/wd/hub";
	public static String starttime;
	public static String executiontime;
	public static String endtime;
	public static long executionstartdatetimestamp;
	public static String startdatetimestamp;
	public static String enddatetimestamp;
	public static Map<String, String> commonCapabilities1;
    private static int counter = 1;

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
	
	public Boolean scrollToAnElement1(String element, ElementType elementType) {
		try {
			switch (elementType) {
			case Xpath: {
				WebElement scrollElement = driver.findElement(By.xpath(element));
				((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(false)", scrollElement);
				break;
			}
			case Id: {
				WebElement scrollElement = driver.findElement(By.id(element));
				((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(false)", scrollElement);
				break;
			}
			case Name: {
				WebElement scrollElement = driver.findElement(By.name(element));
				((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(false)", scrollElement);
				break;
			}
			case CssSelector: {
				WebElement scrollElement = driver.findElement(By.cssSelector(element));
				((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(false)", scrollElement);
				break;
			}
			case ClassName: {
				WebElement scrollElement = driver.findElement(By.className(element));
				((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(false)", scrollElement);
				break;
			}
			case LinkText: {
				WebElement scrollElement = driver.findElement(By.linkText(element));
				((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(false)", scrollElement);
				break;
			}	
			case Text: {
				WebElement scrollElement = driver.findElement(By.xpath("//*[text()='" + element + "']"));
				((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(false)", scrollElement);
				break;
			}	
		default:
			break;
			}
		} 
		catch (Exception e) {
			System.out.println(element);
//			reportLog("Unable to scroll to an element: " + element + " in the page, with the Exception: " + e);
//			Reporter.log("Unable to scroll to an element: " + element + " in the page, with the Exception: " + e, true);
			return false;
		}
		return true;
	}

	/**
	 * Scroll Down
	 *
	
	 * @param value
	 */
	public void scrollDown(int value) {
		((JavascriptExecutor) driver).executeScript("window.scrollBy(0," + value + ")");
	}

	/**
	 * Scroll Up
	 *
	
	 * @param value
	 */
	public void scrollUp(int value) {
		((JavascriptExecutor) driver).executeScript("window.scrollBy(0,-" + value + ")");
	}

	/**
	 * Click Element with Text Using CSS Selector
	 *
	
	 * @param element
	 * @param text
	 * @return
	 * @throws InterruptedException
	 */
	public Boolean clickElementWithText(String element, String text) throws InterruptedException {
		List<WebElement> Elements = driver.findElements(By.cssSelector(element));
		for (WebElement sample : Elements) {
			if (sample.getText().equals(text)) {
				sample.click();
				flag = true;
				break;
			} else
				flag = false;
		}
		return flag;
	}

	/**
	 * Double click on the Element
	 *
	
	 * @param element
	 * @param elementtype
	 */
	public void doubleClick(String element, ElementType elementtype) {
		Actions action = new Actions(driver); 
		try {
			if (elementtype == ElementType.Id) {
				WebElement webElement = driver.findElement(By.id(element));
				action.doubleClick(webElement).perform();
			} else if (elementtype == ElementType.Name) {
				WebElement webElement = driver.findElement(By.name(element));
				action.doubleClick(webElement).perform();
			} else if (elementtype == ElementType.Xpath) {
				WebElement webElement = driver.findElement(By.xpath(element));
				action.doubleClick(webElement).perform();
			} else if (elementtype == ElementType.CssSelector) {
				WebElement webElement = driver.findElement(By.cssSelector(element));
				action.doubleClick(webElement).perform();
			} else if (elementtype == ElementType.ClassName) {
				WebElement webElement = driver.findElement(By.className(element));
				action.doubleClick(webElement).perform();
			} else if (elementtype == ElementType.LinkText) {
				WebElement webElement = driver.findElement(By.linkText(element));
				action.doubleClick(webElement).perform();
			} else if (elementtype == ElementType.Text) {
				WebElement element1 = driver.findElement(By.xpath("//*[text()='" + element + "']"));
				((JavascriptExecutor) driver).executeScript("arguments[0].doubleclick();", element1);
			}
		} catch (Exception e) {
			System.out.println(element);
//			reportLog("Unable to Double click the Element: " + element + " Exception: " + e);
//			Reporter.log("Unable to Double click the Element: " + element + " Exception: " + e);
		}
	}

	/**
	 * Press Tab Key
	 *
	
	 */
	public void pressTabKey() {
		Actions builder = new Actions(driver);
		builder.sendKeys(Keys.TAB).build().perform();
	}

	/**
	 * Clear, Enter the Text and click tab key
	 *
	
	 * @param locator
	 * @param text
	 * @param element
	 */
	public void enterTextAndClickTabKey(String locator, String text, ElementType element) {
		clickAnElement(locator, element, "ClickElement");
		sendTextToAnElement(locator, text, element);
		pressEndKey();
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

//	/**
//	 * Clear text area
//	 *
//	
//	 */
//	public void cleartextarea() {
//		driver.findElement(By.cssSelector(DineInPageLocators.ADDINSTRUCTION_TEXTAREA)).clear();
//	}

	/**
	 * Scroll bottom at end
	 *
	
	 */
	public void scrollbottom() {
		driver.findElement(By.cssSelector("body")).sendKeys(Keys.CONTROL, Keys.END);
	}

	/**
	 * Click Back Button
	 *
	
	 */
	public void clickBackButton() {
		try {
			driver.navigate().back();
		} catch (Exception e) {
//			reportLog("Unable to go back from the Page, Exception: " + e);
//			Reporter.log("Unable to go back from the Page, Exception: " + e);
		}
	}

	/**
	 * Press Escape Key
	 *
	
	 */
	public void pressEscapeKey() {
		Actions builder = new Actions(driver);
		builder.sendKeys(Keys.ESCAPE).build().perform();
	}

	/**
	 * Press Enter Key
	 *
	
	 */
	public void pressEnterKey() {
		Actions builder = new Actions(driver);
		builder.sendKeys(Keys.ENTER).build().perform();
	}

	/**
	 * Press downarrow Key
	 *
	
	 */
	public void pressDownArrowKey() {
		Actions builder = new Actions(driver);
		builder.sendKeys(Keys.ARROW_DOWN).build().perform();
	}

	/**
	 * Press Refresh Key
	 *
	 */
	public void pressRefreshKey() {
		Actions builder = new Actions(driver);
		builder.keyDown(Keys.CONTROL).sendKeys(Keys.F5).keyUp(Keys.CONTROL).perform();
	}

	/**
	 * Press uparrow Key
	 *
	
	 */
	public void pressUpArrowKey() {
		Actions builder = new Actions(driver);
		builder.sendKeys(Keys.ARROW_UP).build().perform();
	}

	/**
	 * Press downarrow Key
	 *
	
	 */
	public void pressRightArrowKey() {
		Actions builder = new Actions(driver);
		builder.sendKeys(Keys.ARROW_RIGHT).build().perform();
	}

	/**
	 * Press Back Space
	 *
	
	 */
	public void pressBackSpace() {
		Actions builder = new Actions(driver);
		builder.sendKeys(Keys.BACK_SPACE).build().perform();
	}

	/**
	 * Press End Key
	 *
	
	 */
	public void pressEndKey() {
		Actions builder = new Actions(driver);
		builder.sendKeys(Keys.END).build().perform();
	}

	/**
	 * Scroll To Element
	 *
	
	 * @param element
	 * @param index
	 */
	public void scrollToAnElementInListOfElements(String element, int index) {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		List<WebElement> elements = driver.findElements(By.cssSelector(element));
		WebElement scrollElement = elements.get(index);
		js.executeScript("arguments[0].scrollIntoView(false)", scrollElement);
		scrollUp(75);
	}

	/**
	 * Move to Element
	 *
	
	 * @throws InterruptedException
	 */
	public void moveToElement(String element, ElementType elementType) throws InterruptedException {
		switch (elementType) {
		case Id: {
			Actions action = new Actions(driver);
			WebElement Element = driver.findElement(By.id(element));
			action.moveToElement(Element).perform();
			Thread.sleep(2500);
			break;
		}

		case Name: {
			Actions action = new Actions(driver);
			WebElement Element = driver.findElement(By.name(element));
			action.moveToElement(Element).perform();
			Thread.sleep(2500);
			break;
		}

		case Xpath: {
			Actions action = new Actions(driver);
			WebElement Element = driver.findElement(By.xpath(element));
			action.moveToElement(Element).perform();
			Thread.sleep(2500);
			break;
		}

		case CssSelector: {
			Actions action = new Actions(driver);
			WebElement Element = driver.findElement(By.cssSelector(element));
			action.moveToElement(Element).perform();
			Thread.sleep(2500);
			break;
		}

		case ClassName: {
			Actions action = new Actions(driver);
			WebElement Element = driver.findElement(By.className(element));
			action.moveToElement(Element).perform();
			Thread.sleep(2500);
			break;
		}

		case LinkText: {
			Actions action = new Actions(driver);
			WebElement Element = driver.findElement(By.linkText(element));
			action.moveToElement(Element).perform();
			Thread.sleep(2500);
			break;
		}
		default:
			break;
		}
	}

	/**
	 * Capture screenshot
	 *
	
	 * @param screenShotName
	 * @return
	 * @throws IOException
	 */
	public String capture(String screenShotName) throws IOException {
		TakesScreenshot ts = (TakesScreenshot) driver;
		File source = ts.getScreenshotAs(OutputType.FILE);
		String dest = System.getProperty("user.dir") + screenShotName + ".png";
		File destination = new File(dest);
		FileUtils.copyFile(source, destination);
		return dest;
	}

	/**
	 * Switch Frame by Index
	 *
	
	 * @throws InterruptedException
	 */
	public void switchFrame(int i) throws InterruptedException {
		driver.switchTo().frame(i);
		Thread.sleep(3000);
	}

	/**
	 * Switch Frame by Element
	 *
	
	 * @param element
	 */
//	public void switchFrameElement(String element) {
//		WebElement elementframe = driver.findElement(By.cssSelector(element));
//		driver.switchTo().frame(elementframe);
//	}
	
	public void switchFrameElementall(String element, ElementType elementType, String elementname) {
		try {
			switch (elementType) {
			case Id: {
				WebElement elementframe = driver.findElement(By.id(element));
				driver.switchTo().frame(elementframe);			
				break;
				}

			case Name: {
				WebElement elementframe = driver.findElement(By.name(element));
				driver.switchTo().frame(elementframe);				
				break;
			}

			case Xpath: {
				WebElement elementframe = driver.findElement(By.xpath(element));
				driver.switchTo().frame(elementframe);				
				break;
			}

			case CssSelector: {
				WebElement elementframe = driver.findElement(By.cssSelector(element));
				driver.switchTo().frame(elementframe);					
				break;
			}

			case ClassName: {
				WebElement elementframe = driver.findElement(By.className(element));
				driver.switchTo().frame(elementframe);					
				break;
			}

			case LinkText: {
				WebElement elementframe = driver.findElement(By.linkText(element));
				driver.switchTo().frame(elementframe);
				break;
			}

			case Text: {
				WebElement elementframe = driver.findElement(By.xpath("//*[text()='" + element + "']"));
				driver.switchTo().frame(elementframe);
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
	}
	
	/**
	 * Switch to Main Window And Click an Element
	 *
	
	 * @param element
	 * @param elementType
	 * @throws InterruptedException
	 */
	public void switchToMainAndClickElement(String element, ElementType elementType) throws InterruptedException {
		driver.switchTo().defaultContent();
		Thread.sleep(3000);
		String MainWindow = driver.getWindowHandle();
		Set<String> s1 = driver.getWindowHandles();
		Iterator<String> i1 = s1.iterator();
		while (i1.hasNext()) {
			String ChildWindow = i1.next();
			if (!MainWindow.equalsIgnoreCase(ChildWindow)) {
				driver.switchTo().window(ChildWindow);
				Thread.sleep(1500);
				switch (elementType) {
				case Id: {
					driver.findElement(By.id(element)).click();
					Thread.sleep(1000);
					break;
				}

				case Name: {
					driver.findElement(By.name(element)).click();
					Thread.sleep(1000);
					break;
				}

				case Xpath: {
					driver.findElement(By.xpath(element)).click();
					Thread.sleep(1000);
					break;
				}

				case CssSelector: {
					driver.findElement(By.cssSelector(element)).click();
					Thread.sleep(1000);
					break;
				}

				case ClassName: {
					driver.findElement(By.className(element)).click();
					Thread.sleep(1000);
					break;
				}

				case LinkText: {
					driver.findElement(By.linkText(element)).click();
					Thread.sleep(1000);
					break;
				}

				case Text: {
					WebElement element1 = driver.findElement(By.xpath("//*[text()='" + element + "']"));
					((JavascriptExecutor) driver).executeScript("arguments[0].click();", element1);
					Thread.sleep(1000);
					break;
				}
				default:
					break;
				}
			}
		}
		driver.switchTo().window(MainWindow);
		Thread.sleep(3000);
		driver.switchTo().frame(0);
	}

	/**
	 * Switch to Child Window
	 *
	
	 * @param element
	 * @throws InterruptedException
	 */
	public void switchToChildWindow() throws InterruptedException {
		driver.switchTo().defaultContent();
		Thread.sleep(5000);
		String MainWindow = driver.getWindowHandle();
		Set<String> s1 = driver.getWindowHandles();
		Iterator<String> i1 = s1.iterator();
		while (i1.hasNext()) {
			String ChildWindow = i1.next();
			if (!MainWindow.equalsIgnoreCase(ChildWindow)) {
				driver.switchTo().window(ChildWindow);
				Thread.sleep(5000);
				// driver.findElement(By.linkText(element)).click();
				break;
			}
		}
	}
	public void switchTodefaultcontent() throws InterruptedException {
		// TODO Auto-generated method stub
		driver.switchTo().defaultContent();
	}
	

	/**
	 * Switch to Child Window
	 *
	
	 * @param element
	 * @throws InterruptedException
	 */
	public void switchToChildWindow(String element) throws InterruptedException {
		driver.switchTo().defaultContent();
		Thread.sleep(5000);
		String MainWindow = driver.getWindowHandle();
		Set<String> s1 = driver.getWindowHandles();
		Iterator<String> i1 = s1.iterator();
		while (i1.hasNext()) {
			String ChildWindow = i1.next();
			if (!MainWindow.equalsIgnoreCase(ChildWindow)) {
				driver.switchTo().window(ChildWindow);
				Thread.sleep(5000);
				// driver.findElement(By.linkText(element)).click();
				break;
			}
		}
	}

	/**
	 * Switch to Child Window and Close Current window
	 *
	
	 * @param element
	 * @return
	 * @throws InterruptedException
	 */
	public Boolean switchToChildWindowClose(String element) throws InterruptedException {
		driver.switchTo().defaultContent();
		Thread.sleep(5000);
		String MainWindow = driver.getWindowHandle();
		Set<String> s1 = driver.getWindowHandles();
		Iterator<String> i1 = s1.iterator();
		while (i1.hasNext()) {
			String ChildWindow = i1.next();
			if (!MainWindow.equalsIgnoreCase(ChildWindow)) {
				driver.switchTo().window(ChildWindow);
				Thread.sleep(3000);
				flag = driver.getCurrentUrl().contains(element);
				Thread.sleep(3000);
				driver.close();
			}
		}
		driver.switchTo().window(MainWindow);
		Thread.sleep(3000);
		return flag;
	}

	/**
	 * Get list of elements text
	 *
	
	 * @param element
	 * @return
	 */
	public String[] getListOfElementsText(String element) {
		int i = 0;
		List<WebElement> Elements = driver.findElements(By.xpath(element));
		String[] allText = new String[Elements.size()];
		Iterator<WebElement> itr = Elements.iterator();
		while (itr.hasNext()) {
			allText[i++] = itr.next().getText();
		}
		return allText;
	}

	/**
	 * Select the value from dropdown by using index value
	 *
	
	 * @param element
	 * @param index
	 */
	public void selectDropDownValueByIndex(String element, int index) {
		Select dropdown = new Select(driver.findElement(By.cssSelector(element)));
		dropdown.getOptions().get(index).isSelected();
	}

	/**
	 * Verify the Check box Selected or not
	 *
	
	 * @param element
	 * @return
	 */
	@SuppressWarnings("unused")
	public boolean checkboxSelected(String element) {
		WebElement checked = driver.findElement(By.cssSelector(element));
		boolean isSelected = checked.isSelected();
		return flag;
	}

	/**
	 * Clear the text fieldsS
	 *
	
	 * @param element
	 * @param elementtype
	 */
	public void clearTextFields(String element, ElementType elementtype) {
		try {
			if (elementtype == ElementType.Id)
				driver.findElement(By.id(element)).clear();
			else if (elementtype == ElementType.Name)
				driver.findElement(By.name(element)).clear();
			else if (elementtype == ElementType.Xpath)
				driver.findElement(By.xpath(element)).clear();
			else if (elementtype == ElementType.CssSelector) {
				driver.findElement(By.cssSelector(element)).click();
				driver.findElement(By.cssSelector(element)).clear();
			} else if (elementtype == ElementType.ClassName)
				driver.findElement(By.className(element)).clear();
		} catch (Exception e) {
//			reportLog("Unable to Clear the Text field to the Element: " + element + " Exception: " + e);
//			Reporter.log("Unable to Clear the Text field to the Element: " + element + " Exception: " + e);
			System.out.println(element);
		}
	}

	/**
	 * Select the value from dropdown by using option value
	 *
	
	 * @param element
	 * @param value
	 * @return
	 */
	public boolean selectDropDownOptionsByCssSelector(String element, String value) {
		try {
			Select dropdown = new Select(driver.findElement(By.cssSelector(element)));
			dropdown.selectByValue(value);
		} catch (Exception e) {
			Reporter.log("Element is not found in the page " + element + " Exception " + e, true);
			return false;
		}
		return true;
	}

	/**
	 * Clear text
	 *
	
	 * @param element
	 */
	public void clearText(String element) {
		WebElement toClear = driver.findElement(By.xpath(element));
		toClear.sendKeys(Keys.CONTROL + "a");
		toClear.sendKeys(Keys.DELETE);
	}

	/**
	 * Verify Element is not displayed
	 *
	
	 * @param element
	 * @param elementType
	 * @return
	 */
	public Boolean verifyElementIsNotDisplayed(String element, ElementType elementType) {
		int loop = 0;
		switch (elementType) {
		case CssSelector: {
			for (int count = 0; count < 5 && loop == 0; count++) {
				// Thread.sleep(2000);
				loop = driver.findElements(By.cssSelector(element)).size() > 0
						&& driver.findElement(By.cssSelector(element)).isDisplayed() ? 0 : 1;
			}
			if (loop == 1)
				flag = true;
			else {
				flag = false;
				Reporter.log("Element is displayed in this page." + element, true);
			}
			break;
		}
		case Xpath: {
			for (int count = 0; count < 5 && loop == 0; count++) {
				// Thread.sleep(2000);
				loop = driver.findElements(By.xpath(element)).size() > 0
						&& driver.findElement(By.xpath(element)).isDisplayed() ? 0 : 1;
			}
			if (loop == 1)
				flag = true;
			else {
				flag = false;
				Reporter.log("Element is displayed in this page." + element, true);
			}
			break;
		}
		default:
			break;
		}
		return flag;
	}

	/**
	 * Compare two text to check equal or not
	 *
	
	 * @param actualText
	 * @param compareText
	 * @return
	 */
	public boolean compareTwoText(String[] actualText, String[] compareText) {
		int index = 0;
		flag = true;
		for (String e : actualText) {
			if (flag && index < 12) {
				flag = (e.equals(compareText[index]));
				index++;
			}
		}
		return flag;
	}

	/**
	 * To Check value is Present or not
	 *
	
	 * @return
	 */
	public boolean toCheckVauleIsPresent(String[] arr, String toCheckValue) {
		flag = false;
		for (String element : arr) {
			if (element.equals(toCheckValue)) {
				flag = true;
				break;
			}
		}
		return flag;
	}

	/**
	 * Move to Element
	 *
	
	 */
	public void moveToXpathElement(String element) {
		Actions action = new Actions(driver);
		WebElement Element = driver.findElement(By.xpath(element));
		action.moveToElement(Element).perform();
	}

	/**
	 * To close tab
	 *
	
	 */
	public boolean pressCloseTab() {
		Actions builder = new Actions(driver);
		builder.sendKeys(Keys.CONTROL + "w").build().perform();
		return flag;

	}

	/**
	 * To restore close tab
	 *
	
	 */
	@SuppressWarnings("static-access")
	public boolean pressRestoreTab(String url) {
		Actions builder = new Actions(driver);
		builder.sendKeys(Keys.CONTROL.SHIFT + "t").build().perform();
		driver.get(url);
		return flag;
	}

	/**
	 * verify open new tab
	 *
	 * @param url
	 * @return
	 * @throws InterruptedException
	 */
	public static boolean openNewTab(String url) throws InterruptedException {
		((JavascriptExecutor) driver).executeScript("window.open()");
		ArrayList<String> tabs = new ArrayList<String>(driver.getWindowHandles());
		driver.switchTo().window(tabs.get(1));
		driver.get(url);
		wait = new WebDriverWait(driver, Duration.ofSeconds(20));
		Thread.sleep(2000);
		return flag;
	}

	/**
	 * verify switch tab
	 *
	 * @param tab
	 */
	public static void switchToTab(String tab) {
		ArrayList<String> tabs = new ArrayList<String>(driver.getWindowHandles());
		if (tab.equals("New"))
			driver.switchTo().window(tabs.get(0));
		else
			driver.switchTo().window(tabs.get(1));
	}

	/**
	 * verify switch tab
	 *
	 * @param tab
	 */
	public static void switchToTabandCloseTab(String tab) {
		ArrayList<String> tabs = new ArrayList<String>(driver.getWindowHandles());
		if (tab.equals("New")) {
			driver.switchTo().window(tabs.get(0));
			driver.close();
		} else {
			driver.switchTo().window(tabs.get(1));
			driver.close();
		}
	}

	/**
	 * verify close tabs
	 *
	 * @param tab
	 */
	@SuppressWarnings("unlikely-arg-type")
	public void CloseTab(String tab) {
		ArrayList<String> tabs = new ArrayList<String>(driver.getWindowHandles());
		if (tab.equals("New") || (tabs.equals("Old")))
			driver.switchTo().window(tabs.get(0));
	}

	/**
	 * verify another new tabs
	 *
	 * @param newurl
	 * @return
	 * @throws InterruptedException
	 */
	public boolean anotherNewTab(String newurl) throws InterruptedException {
		((JavascriptExecutor) driver).executeScript("window.open()");
		ArrayList<String> tabs = new ArrayList<String>(driver.getWindowHandles());
		driver.switchTo().window(tabs.get(1));
		Thread.sleep(2000);
		driver.get(newurl);
		wait = new WebDriverWait(driver, Duration.ofSeconds(20));
		Thread.sleep(2000);
		return flag;

	}

	/**
	 * Verify Element is Displayed
	 *
	 * @return
	 *
	
	 */
	public boolean verifyElementIsDisplayed(String element, ElementType elementType) {
		try {
			switch (elementType) {
			case Id: {
				flag = driver.findElement(By.id(element)).isDisplayed();
				break;
			}

			case Name: {
				flag = driver.findElement(By.name(element)).isDisplayed();
				break;
			}

			case Xpath: {
				flag = driver.findElement(By.xpath(element)).isDisplayed();
				break;
			}

			case CssSelector: {
				flag = driver.findElement(By.cssSelector(element)).isDisplayed();
				break;
			}

			case ClassName: {
				flag = driver.findElement(By.className(element)).isDisplayed();
				break;
			}

			case LinkText: {
				flag = driver.findElement(By.linkText(element)).isDisplayed();
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
	 * Verify Drop down values
	 *
	
	 * @param element
	 * @return
	 */
	public boolean verifyDropdownValues(String element, String[] expected) {
		String[] exp = expected;
		Select select = new Select(driver.findElement(By.xpath(element)));
		List<WebElement> options = select.getOptions();
		flag = options.size() == exp.length;
		if (flag) {
			for (int i = 0; i < options.size(); i++) {
				Assert.assertEquals(exp[i], options.get(i).getText());
			}
		} else {
			System.out.println("Dropdown Values are Mismatched");
			flag = false;
		}
		return flag;
	}

	/**
	 * Get list of elements text
	 *
	
	 * @param element
	 * @return
	 * @return
	 */
	public String getElementsText(String element) {
		WebElement t = driver.findElement(By.xpath(element));
		Select select = new Select(t);
		WebElement o = select.getFirstSelectedOption();
		String selectedoption = o.getText();
		return selectedoption;
	}

	/**
	 * Verify the Check box Selected or not
	 *
	PraveenKumar R
	 * @param element
	 * @return
	 */
	public boolean swapCheckboxSelected(String element) {
		WebElement checked = driver.findElement(By.cssSelector(element));
		boolean isSelected = checked.isSelected();
		if (isSelected == false) {
			checked.click();
		} else if (isSelected == true) {
			System.out.println("Already clicked");
		}
		return flag;
	}

	/**
	 * Verify the Check box Selected or not
	 *
	
	 * @param element
	 * @return
	 */
	public boolean checkboxIsNotSelected(String element, ElementType elementtype) {
		flag = driver.findElement(By.cssSelector(element)).isSelected();
		if (flag) {
			flag = false;
		} else {
			flag = true;
		}
		return flag;
	}

	/**
	 * Verify the Check box Selected or not
	 *
	
	 * @param element
	 * @return
	 */
	public boolean checkboxIsSelected(String element, ElementType elementtype) {
		try {
			switch (elementtype) {
			case Id: {
				flag = driver.findElement(By.id(element)).isSelected();
				break;
			}

			case Name: {
				flag = driver.findElement(By.name(element)).isSelected();
				break;
			}

			case Xpath: {
				flag = driver.findElement(By.xpath(element)).isSelected();
				break;
			}

			case CssSelector: {
				flag = driver.findElement(By.cssSelector(element)).isSelected();
				break;
			}

			case ClassName: {
				flag = driver.findElement(By.className(element)).isSelected();
				break;
			}

			case LinkText: {
				flag = driver.findElement(By.linkText(element)).isSelected();
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
	 * Select the text from dropdown by using text
	 *
	
	 * @param element
	 * @param value
	 * @return
	 */
	public boolean selectDropDownOptionsByText(String element, String value, ElementType elementtype) {
		try {
			switch (elementtype) {
			case Id: {
				Select dropdown = new Select(driver.findElement(By.id(element)));
				dropdown.selectByVisibleText(value);
				break;
			}

			case Name: {
				Select dropdown = new Select(driver.findElement(By.name(element)));
				dropdown.selectByVisibleText(value);
				break;
			}

			case Xpath: {
				Select dropdown = new Select(driver.findElement(By.xpath(element)));
				dropdown.selectByVisibleText(value);
				break;
			}

			case CssSelector: {
				Select dropdown = new Select(driver.findElement(By.cssSelector(element)));
				dropdown.selectByVisibleText(value);
				break;
			}

			case ClassName: {
				Select dropdown = new Select(driver.findElement(By.className(element)));
				dropdown.selectByVisibleText(value);
				break;
			}

			case LinkText: {
				Select dropdown = new Select(driver.findElement(By.linkText(element)));
				dropdown.selectByVisibleText(value);
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
		return true;
	}

	/**
	 * JavaScript click an element
	 *
	
	 * @param element
	 * @param elementType
	 * @return boolean - element present/not
	 */
	public Boolean javaScriptClickAnElement(String element, ElementType elementType, String elementname) {
		try {
			switch (elementType) {
			case Id: {
				WebElement element1 = driver.findElement(By.id(element));
				((JavascriptExecutor) driver).executeScript("arguments[0].click();", element1);
			}

			case Name: {
				WebElement element1 = driver.findElement(By.name(element));
				((JavascriptExecutor) driver).executeScript("arguments[0].click();", element1);
				break;
			}

			case Xpath: {
				WebElement element1 = driver.findElement(By.xpath(element));
				((JavascriptExecutor) driver).executeScript("arguments[0].click();", element1);
				break;
			}

			case CssSelector: {
				WebElement element1 = driver.findElement(By.cssSelector(element));
				((JavascriptExecutor) driver).executeScript("arguments[0].click();", element1);
				break;
			}

			case ClassName: {
				WebElement element1 = driver.findElement(By.className(element));
				((JavascriptExecutor) driver).executeScript("arguments[0].click();", element1);
				break;
			}

			case LinkText: {
				WebElement element1 = driver.findElement(By.linkText(element));
				((JavascriptExecutor) driver).executeScript("arguments[0].click();", element1);
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
//			reportLog("Unable to click : " + elementname + element + " in the page, with the Exception: " + e);
//			Reporter.log("Unable to click : " + elementname + element + " in the page, with the Exception: " + e, true);
			return false;
		}
		return true;
	}

	/**
	 * Switch to Child Window and Send text,Click Element
	 *
	
	 * @param element
	 * @return
	 * @throws InterruptedException
	 */
	public void switchToChildWindow(String element, String element1, String Value) throws InterruptedException {
		driver.switchTo().defaultContent();
		Thread.sleep(5000);
		String MainWindow = driver.getWindowHandle();
		Set<String> s1 = driver.getWindowHandles();
		Iterator<String> i1 = s1.iterator();
		while (i1.hasNext()) {
			String ChildWindow = i1.next();
			if (!MainWindow.equalsIgnoreCase(ChildWindow)) {
				driver.switchTo().window(ChildWindow);
				Thread.sleep(3000);
				driver.findElement(By.cssSelector(element1)).sendKeys(Value);
				driver.findElement(By.cssSelector(element)).click();
				Thread.sleep(3000);
			}
		}
		driver.switchTo().window(MainWindow);
		Thread.sleep(3000);
	}

	/**
	 * Switch to Child Window and Send text,Click Element
	 *
	
	 * @param element
	 * @return
	 * @throws InterruptedException
	 */
	public void switchToChildWindowclick(String element, String element1) throws InterruptedException {
		driver.switchTo().defaultContent();
		Thread.sleep(5000);
		String MainWindow = driver.getWindowHandle();
		Set<String> s1 = driver.getWindowHandles();
		Iterator<String> i1 = s1.iterator();
		while (i1.hasNext()) {
			String ChildWindow = i1.next();
			if (!MainWindow.equalsIgnoreCase(ChildWindow)) {
				driver.switchTo().window(ChildWindow);
				Thread.sleep(3000);
				driver.findElement(By.cssSelector(element)).click();
				Thread.sleep(2500);
				driver.findElement(By.xpath(element1)).click();
				Thread.sleep(3000);
			}
		}
		driver.switchTo().window(MainWindow);
		Thread.sleep(3000);
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
	    public static String generateUniqueEmail() 
	    {
	        // Get current timestamp (date and time)
	        LocalDateTime currentDateTime = LocalDateTime.now();

	        // Format the timestamp to be used in the email (example: yyyyMMdd_HHmmss)
	        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss");
	        String formattedTimestamp = currentDateTime.format(formatter);

	        // Create a unique email by appending the formatted timestamp to a base email
	        String uniqueEmail = "Manoj" + formattedTimestamp + "@maildrop.cc";

	        return uniqueEmail;
	    }
	    
	    // Method to generate a unique username with an incremental number
	    public static String generateUniqueUsername() {
	        // Increment the counter each time a username is generated
	        String uniqueUsername = "Kumar" + counter;  // Example: user1, user2, user3, etc.
	        
	        // Increment the counter for the next username
	        counter++;
	        
	        return uniqueUsername;
	    }
	    public void scrollUntilElementVisible(By elementLocator, int maxScrolls) {
	        Actions actions = new Actions(driver);
	        int scrollCount = 0;
	        boolean elementFound = false;
	        
	        while (scrollCount < maxScrolls) {
	            try {
	                WebElement element = driver.findElement(elementLocator);  // Find the element
	                if (element.isDisplayed()) {
	                    System.out.println("Element found!");
	                    elementFound = true;
	                    break; // Exit loop if element is found and displayed
	                } else {
	                    System.out.println("Element not visible, scrolling down...");
	                    actions.sendKeys(Keys.DOWN).perform(); // Scroll down
	                    Thread.sleep(500); // Small delay after each scroll
	                    scrollCount++; // Increment scroll count
	                }
	            } catch (NoSuchElementException e) {
	                // Element not found, scroll and try again
	                System.out.println("Element not found, scrolling down...");
	                actions.sendKeys(Keys.DOWN).perform(); // Scroll down
	                scrollCount++; // Increase scroll count
	            } catch (InterruptedException e) {
	                e.printStackTrace();
	            }
	        }
	        
	        if (!elementFound) {
	            System.out.println("Reached maximum scrolls, element not found.");
	        }
	    }
	    public String[] extractTitleAndParentalGuide(String name) {
	        String title = "";
	        String parentalGuide = "";

	        // Define a regex pattern to capture the program title and the optional parental guide inside parentheses
	        Pattern pattern = Pattern.compile("([^(]+)\\s?\\((.*?)\\)"); 
	        Matcher matcher = pattern.matcher(name);

	        if (matcher.find()) {
	            title = matcher.group(1).trim(); // Program title without parentheses
	            parentalGuide = matcher.group(2).trim(); // Parental guide inside parentheses (if exists)
	        } else {
	            // If no match, just return the full program title without any parental guide
	            title = name.trim();
	            parentalGuide = ""; // No parental guide
	        }

	        return new String[] {title, parentalGuide};
	    }

	    /**
	     * Extracts the box name (e.g., NEWSKYBOX1) from a given text.
	     * 
	     * @param text The input text from which the box name needs to be extracted.
	     * @return The extracted box name or an empty string if not found.
	     */
	    public static String extractBoxName(String text) {
	        // Define the pattern to match the box name (NEWSKYBOX1, etc.)
	        String regex = "(?i)your\\s+([\\w\\d\\.]+)\\s+box";  // Matches the word after "your" and before "box"
	        
	        // Create a Pattern object
	        Pattern pattern = Pattern.compile(regex);  // CASE_INSENSITIVE to match "NEWSKYBOX1" regardless of case
	        Matcher matcher = pattern.matcher(text);
	        
	        // If a match is found, return the box name; otherwise, return an empty string
	        if (matcher.find()) {
	            return matcher.group(1); // Extract and return the box name
	        } else {
	            return ""; // Return empty string if no match is found
	        }
	    }
	    
	 // Function to extract minutes from a time string (including seconds)
	    public static int extractMinutes(String time) {
	        String[] timeParts = time.split(":");

	        if (timeParts.length == 3) {
	            // Time format: HH:mm:ss
	            return Integer.parseInt(timeParts[1]); // Return minutes part (ignores hours and seconds)
	        } else if (timeParts.length == 2) {
	            // Time format: mm:ss
	            return Integer.parseInt(timeParts[0]); // Return minutes directly (ignores seconds)
	        } else if (timeParts.length == 1) {
	            // Time format: mm (only minutes)
	            return Integer.parseInt(timeParts[0]); // Return minutes directly
	        } 
	        // In case of invalid format, return 0 (you can handle it differently if needed)
	        return 0;
	    }
	    
	    public void adjustHandleVertically(WebElement element, int offsetY) {
			Actions action = new Actions(driver);
	    	action.clickAndHold(element)
	               .moveByOffset(0, offsetY) // Positive value moves down, negative moves up
	               .release()
	               .perform();
	    }
	    public void adjustHandleHorizontally(WebElement element, int offsetX) {
			Actions action = new Actions(driver);
	    	action.clickAndHold(element)
	    		.moveByOffset(offsetX, 0) // Positive value moves right, negative moves left
	    		.release()
	    		.perform();
	    
	    }
	    
	    // Helper function to check visibility of an element
	    public boolean isElementVisible(By locator) {
	        try {
	            WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
	            return element.isDisplayed();
	        } catch (Exception e) {
	            return false; // Element is not visible
	        }
	    }
}