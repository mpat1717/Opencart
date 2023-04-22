package testBase;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.ResourceBundle;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;

import io.github.bonigarcia.wdm.WebDriverManager;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;  // logging

public class BaseClass {
//C:\Users\mpat1\eclipse-workspace\Opencart\testData	
	public Logger logger;// for Logging
	public ResourceBundle rb;// to read config.properties
	
	public static WebDriver driver;
	
	
	@BeforeClass
	@Parameters("browser")   // getting browser parameter from testng.xml
	public void setup(String br)
	{
		logger = LogManager.getLogger(this.getClass());// for Logger  
		rb = ResourceBundle.getBundle("config");// Load config.properties
		
		//launch right browser based on parameter
		if (br.equals("chrome")) {
			ChromeOptions option = new ChromeOptions();
	        option.addArguments("--remote-allow-origins=*");
			
			WebDriverManager.chromedriver().setup();
		    driver=new ChromeDriver(option);
			
		} else if (br.equals("edge")) {
			EdgeOptions optionke=new EdgeOptions();
			optionke.addArguments("--remote-allow-origins=*");
			WebDriverManager.edgedriver().setup();
			driver = new EdgeDriver(optionke);
			
		} 
	
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

		driver.get(rb.getString("appURL"));///reading data from config file
		//driver.get("http://opencart.com/");
		driver.manage().window().maximize();
	}

	@AfterClass
	public void teadDown() {
		driver.quit();
	}

	public String randomeString() {
		String generatedString = RandomStringUtils.randomAlphabetic(5);
		return (generatedString);
	}

	public String randomeNumber() {
		String generatedString2 = RandomStringUtils.randomNumeric(10);
		return (generatedString2);
	}
	
	public String randomAlphaNumeric() {
		String st = RandomStringUtils.randomAlphabetic(4);
		String num = RandomStringUtils.randomNumeric(3);
		
		return (st+"@"+num);
	}
	public String captureScreen(String tname) throws IOException {
		/*SimpleDateFormat sf=new SimpleDateFormat();
		Date dt=new Date();
		sf.format(dt);
*/
		String timeStamp = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());
				
		TakesScreenshot takesScreenshot = (TakesScreenshot) driver;
		File source = takesScreenshot.getScreenshotAs(OutputType.FILE);
		String destination = System.getProperty("user.dir") + "\\screenshots\\" + tname + "_" + timeStamp + ".png";

		try {
			FileUtils.copyFile(source, new File(destination));
		} catch (Exception e) {
			e.getMessage();
		}
		return destination;

	}

}