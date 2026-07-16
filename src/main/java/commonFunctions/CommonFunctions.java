package commonFunctions;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.Duration;
import java.util.List;
import java.util.Properties;
import org.openqa.selenium.By;
import locators.ContactPageLoc;
import locators.MenuLinksLoc;
import utils.DriverBase;
import utils.Listener;

public class CommonFunctions extends DriverBase {
 	public DriverBase DB = new DriverBase();
	public static String propertyFilePath = System.getProperty("user.dir") + File.separator + "config.properties";
	public static Properties prop = new Properties();
	public Listener ls = new Listener();
	public boolean flag;
	public static ContactPage cp = new ContactPage();
	public static HomePage hp = new HomePage();
	public static CartPage cp1 = new CartPage();
	public static ShopPage sp = new ShopPage();

	//Test Env
		@SuppressWarnings("static-access")
		public void ImplicityWait() throws FileNotFoundException, IOException, InterruptedException {
				DB.driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
			}
		
		@SuppressWarnings("static-access")
		public void VerifyBrowserOpen() throws FileNotFoundException, IOException, Exception {
			//Loading files for Config.properties
			prop.load(new FileInputStream(propertyFilePath));	// Calling Property file
			DB.browserOpen();									// Calling Browser open function 
			// Launch URL for Planit
			String PlanItURL = prop.getProperty("PlanItURL");
			DB.goToURL(PlanItURL);
		}

		public boolean ContactPage() throws FileNotFoundException, IOException, InterruptedException {
				List ContactPage = driver.findElements(By.xpath(MenuLinksLoc.ContactLink));
				if(ContactPage.size() == 1) {
				//Clicking the SignIn Link
					System.out.println("ContactLink Appeared");
					DB.clickAnElement(MenuLinksLoc.ContactLink, ElementType.Xpath, "ClickedContactLink");
				//Report Extent Report	
					ls.reportLog("Successfully Navigated to Contact Page");
					System.out.println("Successfully Navigated to Contact Page");
				//Wait Until the Email field appears
					flag = DB.waitForElement(ContactPageLoc.Emailfield, ElementType.Xpath);
					if(flag) {
						System.out.println("Email field appeared");
					}else {
						System.out.println("Email field Not appeared");
					}
				}else {
					System.out.println("Contact link not appeared");
				}
				return flag;
		}
	
		public boolean NavigateHomePage() throws FileNotFoundException, IOException, InterruptedException {
			List HomePage = driver.findElements(By.xpath(MenuLinksLoc.HomePageLink));
			if(HomePage.size() == 1) {
			//Clicking the SignIn Link
				System.out.println("Home Page Link Appeared");
				DB.clickAnElement(MenuLinksLoc.HomePageLink, ElementType.Xpath, "Clicked Home Page Link");
			//Report Extent Report	
				ls.reportLog("Successfully Navigated to Home Page");
				System.out.println("Successfully Navigated to Home Page");
			//Wait Until the Shopping button field appears
				flag = hp.HomePageStartShop();
				if(flag) {
					System.out.println("Shopping Link button appeared");
				}else {
					System.out.println("Shopping Link button didnt appeared");
				}
			}else {
				System.out.println("Home page link not appeared");
			}
			return flag;
		}
		
		public boolean NavigateShopPage() throws FileNotFoundException, IOException, InterruptedException {			
			List HomePage = driver.findElements(By.xpath(MenuLinksLoc.ShopPageLink));
			if(HomePage.size() == 1) {
			//Clicking the SignIn Link
				System.out.println("Shop Page Link Appeared");
				DB.clickAnElement(MenuLinksLoc.ShopPageLink, ElementType.Xpath, "Clicked Home Page Link");
			//Report Extent Report	
				ls.reportLog("Successfully Navigated to Shop Page");
				System.out.println("Successfully Navigated to Shop Page");
			//Wait Until the Shopping button field appears
				flag = hp.HomePageStartShop();
				if(flag) {
					System.out.println("Shopping Link button appeared");
				}else {
					System.out.println("Shopping Link button didnt appeared");
				}
			}else {
				System.out.println("Home page link not appeared");
			}
			return flag;
		}
		
		public boolean goToCart() {
		    try {
		        driver.findElement(By.id("nav-cart")).click();
		        return true;
		    } catch (Exception e) {
		        e.printStackTrace();
		        return false;
		    }
		}
}