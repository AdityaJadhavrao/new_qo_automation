package try_automation;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.BeforeMethod;

public class Build_Page extends BaseClass{

    static int index=0;
	@BeforeMethod
	public void build() throws InterruptedException
	{
		
		// Scrolling page to the builds
		if(driver !=null)
		{
			JavascriptExecutor js = ((JavascriptExecutor)driver);
			js.executeScript("window.scrollBy(0,350)");
			System.out.println("Page scrolled");
			Thread.sleep(6000);
		
			
		//Click on the required build
		
		java.util.List<WebElement> list=driver.findElements(By.xpath("//tbody/tr/td/a"));
		int builds = list.size();
		System.out.println(builds);
		for(int i=0;i<builds;i++)
		{
			WebElement build = list.get(i);
			
			if(i==index)
			{
				build.click();
			}
		}
		}
	}
	@BeforeMethod
	public void downloadBuild() throws InterruptedException
	{
		//Unhide the builds
		
		driver.findElement(By.xpath("//*[@class='MuiFormControlLabel-root MuiFormControlLabel-labelPlacementEnd css-1tle52v']")).click();
		System.out.println("Clicked on Hide succeeded steps");
		Thread.sleep(8000);
		
		//Scrolling the page to debug build
		JavascriptExecutor js1 = (JavascriptExecutor)driver;
		js1.executeScript("window.scrollBy(0,900)");
		System.out.println("Page scrolled to the bottom");
		Thread.sleep(8000);
		Thread.sleep(5000);
		
		System.out.println("Maual intervention is required");
	}
	@BeforeMethod
	public void validateThePage()
	{
		
		String originalTab = driver.getWindowHandle();
		if(driver.getWindowHandle().equals(originalTab))
		{
			
			WebElement logo = driver.findElement(By.xpath("//*[@id='luci-icon']"));
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
			wait.until(ExpectedConditions.visibilityOf(logo));
			System.out.println(logo.isDisplayed());
		}
	}
}


