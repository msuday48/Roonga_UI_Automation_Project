
package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;
import pageObjects.AccountRegistrationPage;
import pageObjects.HomePage;
import testBase.BaseClass;

public class TC_001_AccountRegistrationTest extends BaseClass{
	
@Test(groups={"Regression","Master"})
	public void verify_account_registration()
	{
		logger.info("starting TC_001_AccountRegistrationTest ");
		try {
			HomePage hp = new HomePage(driver);
			hp.clickMyAccount();
			logger.info("Clicked on nmy account link");
			hp.clickRegister();
			logger.info("Clicked on nmy Register link");
			AccountRegistrationPage regpage = new AccountRegistrationPage(driver);

			logger.info("Providing customer details");
			regpage.setFirstName(randomeString().toUpperCase());
			regpage.setLastName(randomeString().toUpperCase());
			regpage.setEmail(randomeString() + "@gmail.com");// randomly generated the email
			regpage.setTelephone(randomeNumber());

			String password = randomeAlphaNumberic();

			regpage.setPassword(password);
			regpage.setConfirmPassword(password);

			regpage.setPrivacyPolicy();
			regpage.clickContinue();

			logger.info("Validating Expected meassage");
			String confmsg = regpage.getConfirmationMsg();
			Assert.assertEquals(confmsg, "Your Account Has Been Created!");
		}
		catch(Exception e)
		{
			logger.error("test failed");
			logger.debug("Debug logs");
			Assert.fail();
		}
	}
}