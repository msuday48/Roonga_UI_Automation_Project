package testBase;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.Properties;

import org.apache.commons.lang3.RandomStringUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Platform;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

import org.apache.logging.log4j.LogManager;  // Log4j
import org.apache.logging.log4j.Logger;      // Log4j

public class BaseClass {

	public WebDriver driver;
	public Logger logger;  // Log4j
	public Properties p;

	@BeforeClass(groups = {"Sanity", "Regression", "Master"})
	@Parameters({"os", "browser"})
	public void setup(@Optional("windows") String os, @Optional("chrome") String br) throws IOException {
		// Loading config.properties file
		FileReader file = new FileReader("./src//test//resources//config.properties");
		p = new Properties();
		p.load(file);

		logger = LogManager.getLogger(this.getClass());  // Log4j2

		if (p.getProperty("execution_env").equalsIgnoreCase("remote")) {
			DesiredCapabilities capabilities = new DesiredCapabilities();

			// OS
			switch (os.toLowerCase()) {
				case "windows":
					capabilities.setPlatform(Platform.WINDOWS);
					break;
				case "linux":
					capabilities.setPlatform(Platform.LINUX);
					break;
				case "mac":
					capabilities.setPlatform(Platform.MAC);
					break;
				default:
					System.out.println("No matching OS");
					return;
			}

			// Browser
			switch (br.toLowerCase()) {
				case "chrome":
					capabilities.setBrowserName("chrome");
					break;
				case "edge":
					capabilities.setBrowserName("MicrosoftEdge");
					break;
				case "firefox":
					capabilities.setBrowserName("firefox");
					break;
				default:
					System.out.println("No matching browser");
					return;
			}

			driver = new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"), capabilities);
		}

		if (p.getProperty("execution_env").equalsIgnoreCase("local")) {
			switch (br.toLowerCase()) {
				case "chrome":
					driver = new ChromeDriver();
					break;
				case "edge":
					driver = new EdgeDriver();
					break;
				case "firefox":
					driver = new FirefoxDriver();
					break;
				default:
					System.out.println("Invalid browser name..");
					return;
			}
		}

		driver.manage().deleteAllCookies();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		driver.get(p.getProperty("appURL")); // Reading URL from properties file
		driver.manage().window().maximize();
	}

	@AfterClass(groups = {"Sanity", "Regression", "Master"})
	public void tearDown() {
		driver.quit();
	}

	public String randomeString() {
		return RandomStringUtils.randomAlphabetic(5);
	}

	public String randomeNumber() {
		return RandomStringUtils.randomNumeric(10);
	}

	public String randomeAlphaNumberic() {
		String generatedString = RandomStringUtils.randomAlphabetic(3);
		String generatedNumber = RandomStringUtils.randomNumeric(3);
		return (generatedString + "@" + generatedNumber);
	}

	public String captureScreen(String tname) throws IOException {
		String timeStamp = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());
		TakesScreenshot takesScreenshot = (TakesScreenshot) driver;
		File sourceFile = takesScreenshot.getScreenshotAs(OutputType.FILE);
		String targetFilePath = System.getProperty("user.dir") + "\\screenshots\\" + tname + "_" + timeStamp + ".png";
		File targetFile = new File(targetFilePath);
		sourceFile.renameTo(targetFile);
		return targetFilePath;
	}
}
