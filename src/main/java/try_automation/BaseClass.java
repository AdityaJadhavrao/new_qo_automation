package try_automation;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import org.testng.annotations.AfterSuite;

import org.testng.annotations.BeforeSuite;

import io.github.bonigarcia.wdm.WebDriverManager;

public class BaseClass {
	
static protected WebDriver driver;
	
	@BeforeSuite
    public void setUp() throws InterruptedException, IOException 
	{
		Properties prop = new Properties();
		String projtDir = System.getProperty("user.dir");
		String filepath = projtDir+"/src/main/resources/Credentials_folder/User_credntials.txt";
		FileInputStream input = new FileInputStream(filepath);
		prop.load(input);
		
		String username = prop.getProperty("username");
		String password = prop.getProperty("password");

		//Can be deleted if not neccessary

		ChromeOptions options = new ChromeOptions();
		// Omit headless mode
		//options.addArguments("--headless"); // Do not add this line

		options.addArguments("--disable-gpu"); // Optional: Disable GPU hardware acceleration
		options.addArguments("--no-sandbox"); // Optional: Bypass OS security model, but use cautiously
		options.addArguments("--remote-allow-origins=*"); // Optional: Allow access to localhost
		
        // Set up WebDriver using WebDriverManager
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver(options);

        driver.manage().window().maximize();
        
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		
		setUp("ci");
		
		driver.findElement(By.xpath("//a[@class='MuiButtonBase-root MuiButton-root MuiButton-text MuiButton-textPrimary MuiButton-sizeMedium MuiButton-textSizeMedium MuiButton-colorPrimary MuiButton-root MuiButton-text MuiButton-textPrimary MuiButton-sizeMedium MuiButton-textSizeMedium MuiButton-colorPrimary css-13yd90j']")).click();
		System.out.println("Login button clicked");
		Thread.sleep(3000);
		
		driver.findElement(By.xpath("//input[@aria-label='Email or phone']")).sendKeys(username);
		System.out.println("Email ID entered");
		Thread.sleep(3000);
		
		driver.findElement(By.xpath("//span[text()='Next']")).click();
		System.out.println("Next button clicked");
		Thread.sleep(3000);
		
		driver.findElement(By.xpath("//input[@aria-label='Enter your password']")).sendKeys(password);
		System.out.println("Password entered");
		Thread.sleep(3000);
		
		driver.findElement(By.xpath("//span[text()='Next']")).click();
		System.out.println("Next button clicked");
		Thread.sleep(3000);
		
		driver.findElement(By.xpath("//*[text()='Continue']")).click();
		System.out.println("Continue button clicked");
		Thread.sleep(3000);
		
		driver.findElement(By.xpath("//*[text()='Continue']")).click();
		System.out.println("Continue button clicked");
		Thread.sleep(5000);
	}
	public static void setUp(String param) throws InterruptedException
	{
		String baseURL = "https://ci.chromium.org/ui/p/quickoffice/builders/";
		if(param.equalsIgnoreCase("try"))
		{
		//String myURL = baseURL+param+"/bundle_app";
		driver.get(baseURL+param+"/bundle_app");
		System.out.println("Try URL has been accessed");
		Thread.sleep(3000);
		}
		else
		{
			String myURL = baseURL+"ci/bundle_app";
			driver.get(myURL);
			System.out.println("CI URL has been accessed");
			Thread.sleep(3000);
		}
	}
	
	@AfterSuite
	public void tearDown()
	{
		driver.quit();
	}
}

