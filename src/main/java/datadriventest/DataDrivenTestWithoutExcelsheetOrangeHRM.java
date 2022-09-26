package datadriventest;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class DataDrivenTestWithoutExcelsheetOrangeHRM {

	WebDriver driver;

	@BeforeClass
	public void setup() {

		System.setProperty("webdriver.chrome.driver", "C:\\Users\\patel\\Webdriver\\chromedriver.exe");
		driver = new ChromeDriver();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.manage().window().maximize();

	}

	@Test(dataProvider = "LoginData")
	public void loginTest(String user, String pwd, String exp) {

		driver.get("https://opensource-demo.orangehrmlive.com/index.php/auth/login");

		WebElement txtusername = driver.findElement(By.xpath("//input[@id='txtUsername']"));
		txtusername.sendKeys(user);

		WebElement txtpassword = driver.findElement(By.xpath("//input[@id='txtPassword']"));
		txtpassword.sendKeys(pwd);

		driver.findElement(By.xpath("//input[@type='submit']")).click();

		String exp_title = "OrangeHRM";
		String act_title = driver.getTitle();

		String exp_title1 = "OrangeHRM1";
		String act_title1 = driver.getTitle();

		if (exp.equals("Valid")) {
			if (exp_title.equals(act_title)) {
				driver.findElement(By.xpath("//a[@id='welcome']")).click();
				driver.findElement(By.xpath("//a[contains(text(),'Logout')]")).click();
				Assert.assertTrue(true);
			} else {
				Assert.assertTrue(false);
			}
		} else if (exp.equals("Invalid")) {
			if (exp_title1.equals(act_title1)) {
				driver.findElement(By.xpath("//a[@id='welcome']")).click();
				driver.findElement(By.xpath("//a[contains(text(),'Logout')]")).click();
				Assert.assertTrue(false);
			} else {
				Assert.assertTrue(true);
			}
		}

	}

	@DataProvider(name = "LoginData")
	public String[][] getData() {
		String loginData[][] = { { "Admin", "admin123", "Valid" }, { "Admin", "admi", "Invalid" },
				{ "Adm", "admin123", "Invalid" }, { "Adm", "admi", "Invalid" }, };
		return loginData;

	}

	@AfterClass
	void tearDown() {
		driver.close();

	}

}
