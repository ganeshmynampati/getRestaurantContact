package test.getcontact;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;

public class FetchContact

{
	private static WebDriver driver;

	@BeforeTest
	public void setUp() {
		WebDriverManager.chromedriver().setup();
		driver = new ChromeDriver();
		driver.get("https://www.google.com/");
		driver.manage().window().maximize();
		driver.manage().deleteAllCookies();
		Assert.assertEquals("Google", driver.getTitle());
	}

	@Test
	public void getContactDetails() throws InterruptedException {

		driver.findElement(By.name("q")).sendKeys("site:foodbooking.com");
		driver.findElement(By.xpath("//*[contains(@class,'FPdoLc')]/descendant::input[@value='Google Search']"))
				.click();
		WebElement searchResults = driver.findElement(By.xpath("//*[@class='med' and @id='res']"));
		WebDriverWait wait = new WebDriverWait(driver, 60);
		wait.until(ExpectedConditions.visibilityOf(searchResults));
		List<WebElement> restList = driver.findElements(By.xpath("//*[@class='rc']/descendant::h3"));
		for (int i = 0; i < restList.size(); i++) {
			if (restList.get(i).getText().equalsIgnoreCase("Dragon Inn Crickhowell | Facebook")) {
				restList.get(i).click();
				break;
			}
		}
		WebElement faceBookTitle = driver.findElement(By.xpath("//*[contains(@class,'fb_logo')]"));
		wait.until(ExpectedConditions.visibilityOf(faceBookTitle));
		Assert.assertEquals("Dragon Inn Crickhowell | Facebook", driver.getTitle());
		WebElement home = driver.findElement(By.xpath("//span[text()='Home']/parent::a"));
		wait.until(ExpectedConditions.visibilityOf(home));
		home.click();
		Thread.sleep(5000);
		String restaurantName = driver.findElement(By.xpath("//h1[@id='seo_h1_tag']/span[@class='_50f7']")).getText();
		System.out.println("The restaurant name is" + " " + restaurantName);
		String restAddress = driver.findElement(By.xpath("//div[@class='_2wzd']")).getText();
		System.out.println("The restaurant address is" + " " + restAddress);
		String restContactNum = driver
				.findElement(By.xpath("//div[@class='_4-u2 _u9q _3xaf _4-u8']/descendant::div[@class='_4bl9'][2]/div"))
				.getText();
		System.out.println("The restaurant contact number is" + " " + restContactNum);

	}

	@AfterTest
	public void tearDown() throws IOException {
		screenShotUtil();
		driver.close();
		driver.quit();

	}

	public static void screenShotUtil() throws IOException {
		File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		String currentDir = System.getProperty("user.dir");
		FileUtils.copyFile(scrFile, new File(currentDir + "/screenshots/" + System.currentTimeMillis() + ".png"));
	}

}
