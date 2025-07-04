package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;
import pageObjects.HomePage;
import pageObjects.LoginPage;
import pageObjects.MyAccountPage;
import testBase.BaseClass;
import utilities.RetryAnalyzer;

public class TC_002_LoginTest extends BaseClass {

	@Test(groups = {"Sanity", "Master"}, retryAnalyzer = RetryAnalyzer.class)
	public void verify_login() {
		logger.info("**** Starting TC_002_LoginTest ****");
		logger.debug("Capturing application debug logs....");

		try {
			// Home Page
			HomePage hp = new HomePage(driver);
			hp.clickMyAccount();
			logger.info("Clicked on My Account link on the Home Page.");

			hp.clickLogin(); // Login link under MyAccount
			logger.info("Clicked on Login link under My Account.");

			// Login Page
			LoginPage lp = new LoginPage(driver);
			logger.info("Entering valid email and password.");
			lp.setEmail(p.getProperty("email"));
			lp.setPassword(p.getProperty("password"));
			lp.clickLogin(); // Login button
			logger.info("Clicked on Login button.");

			// My Account Page
			MyAccountPage macc = new MyAccountPage(driver);

			boolean targetPage = macc.isMyAccountPageExists();
			Assert.assertTrue(targetPage, "Login failed.");
		}
		catch (Exception e) {
			logger.error("Exception occurred during login test: " + e.getMessage());
			Assert.fail("Test failed due to exception: " + e.getMessage());
		}

		logger.info("**** Finished TC_002_LoginTest ****");
	}
}
