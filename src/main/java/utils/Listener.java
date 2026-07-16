package utils;

import java.util.Objects;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.Reporter;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;


public class Listener implements ITestListener {
	public static int Testcase_passed;
	public static int Testcase_failed;
	public static int Testcase_skipped;
	public static String suiteName;

	ExtentReports extent = ExtentManager.getInstance();
	private static ThreadLocal<ExtentTest> test = new ThreadLocal<>();
	/**
	 * While test is started
	 *
	 
	 */
	@Override
	public synchronized void onStart(ITestContext context) {
		System.out.println("Extent Report Started!");
	}



	/**
	 * While test is finished
	 *
	
	 */
	@Override
	public synchronized void onFinish(ITestContext context) {
		suiteName = context.getCurrentXmlTest().getSuite().getName();
		System.out.println(("Extent Report Ends!"));
		extent.flush();
	}

	/**
	 * When the test begins
	 *
	
	 */
	@Override
	public synchronized void onTestStart(ITestResult result) {
		System.out.println(result.getMethod().getMethodName() + " Started!");
		ExtentTest extentTest = extent.createTest(result.getMethod().getMethodName(),
				result.getMethod().getDescription());
//		ExtentTest extentTest = extent.createTest(result.getMethod().getMethodName());
		test.set(extentTest);
	}

	/**
	 * While test is passed
	 *
	
	 */
	@Override
	public synchronized void onTestSuccess(ITestResult result) {
		Testcase_passed++;
		System.out.println((result.getMethod().getMethodName() + " Passed!"));
		test.get().log(Status.PASS, result.getMethod().getMethodName() + " Passed!");
		// test.get().pass(result.getMethod().getMethodName() + " passed!");
	}

	/**
	 * While test is failure
	 *
	
	 */
	@Override
	public synchronized void onTestFailure(ITestResult result) {
		Testcase_failed++;
		WebDriver driver = DriverBase.driver;
		String base64Screenshot = "data:image/png;base64,"
				+ ((TakesScreenshot) Objects.requireNonNull(driver)).getScreenshotAs(OutputType.BASE64);
	
		System.out.println((result.getMethod().getMethodName() + " Failed!"));
//		test.get().log(Status.FAIL, result.getMethod().getMethodName() + " failed!");
		test.get().fail(result.getThrowable());
		test.get().addScreenCaptureFromBase64String(base64Screenshot).getModel().getMedia().get(0);
		/*
		 * try { String screenShotPath =
		 * ExtentManager.getScreenshot(result.getMethod().getMethodName());
		 * test.get().addScreenCaptureFromPath(screenShotPath); } catch (IOException e)
		 * { e.printStackTrace(); }
		 */
	}

	/**
	 * While test is skipped
	 *
	
	 */
	@Override
	public synchronized void onTestSkipped(ITestResult result) {
		Testcase_skipped++;
		System.out.println((result.getMethod().getMethodName() + " Skipped!"));
	
//		ExtentTest extentTest = extent.createTest(result.getMethod().getMethodName(),
//				result.getMethod().getDescription());
//		test.set(extentTest);
		test.get().skip(result.getThrowable());
		test.get().log(Status.SKIP, result.getMethod().getDescription());
		extent.flush();
	}

	/**
	 * While test on Test Failed But With in Success Percentage
	 *
	
	 */
	@Override
	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
		System.out.println(("onTestFailedButWithinSuccessPercentage for " + result.getMethod().getMethodName()));
	}

	/**
	 * Method for adding logs passed from test cases
	 *
	
	 * @param message
	 */
	public void reportLog(String message) {
		test.get().log(Status.INFO, message);// For extentTest HTML report
		Reporter.log(message);
	}
	public void reportLogPass(String message) {
		test.get().log(Status.PASS, message);// For extentTest HTML report
		Reporter.log(message);
	}
	
	public void reportLogFail(String message) {
		test.get().log(Status.FAIL, message);// For extentTest HTML report
		Reporter.log(message);
	}
}