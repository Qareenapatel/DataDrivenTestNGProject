package pavandatadriven;

import java.io.IOException;
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

public class DataDrivenTestExcel {

	WebDriver driver;

	@BeforeClass
	public void setup() {

		System.setProperty("webdriver.chrome.driver", "C:\\Users\\patel\\Webdriver\\chromedriver.exe");
		driver = new ChromeDriver();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.manage().window().maximize();

	}

	@Test(dataProvider="LoginData")
	public void loginTest(String user, String pwd, String exp) {
		
		driver.get("http://admin-demo.nopcommerce.com/login");
		
		WebElement txtEmail = driver.findElement(By.xpath("//input[@id='Email']"));
		txtEmail.clear();
		txtEmail.sendKeys(user);
		
		WebElement txtpassword = driver.findElement(By.xpath("//input[@id='Password']"));
		txtpassword.clear();
		txtpassword.sendKeys(pwd);
		
		driver.findElement(By.xpath("//button[@type='submit']")).click();
		
		String exp_title = "Dashboard / nopCommerce administration";
		String act_title = driver.getTitle();
		
		if(exp.equals("Valid"))
		{
			if(exp_title.equals(act_title))
			{
				driver.findElement(By.linkText("Logout")).click();
				Assert.assertTrue(true);
			}
			else {
				Assert.assertTrue(false);
			}
		}
			else if(exp.equals("Invalid"))
			{
				if(exp_title.equals(act_title))
				{
					driver.findElement(By.linkText("Logout")).click();
					Assert.assertTrue(false);
				}
				else {
					Assert.assertTrue(true);
			}
		}
		
			
	}

	/*@DataProvider(name = "LoginData")
	public String[][] getData() {
		String loginData[][] = { { "Admin@yourstore.com", "admin", "Valid" },
				{ "Admin@yourstore.com", "adm", "Invalid" },
				{ "Adm@yourstore.com", "admin", "Invalid" },
				{ "Adm@yourstore.com", "adm", "Invalid" }, };
				
		return loginData;

	}*/
	//or
	
	@DataProvider(name="LoginData")
	public String[][] getData() throws IOException{
		
		String path = "C:\\Users\\patel\\OneDrive\\Desktop\\Logindata.xlsx";
		
		ExcelUtils xlsutil = new ExcelUtils(path);
		
		int totalrows = xlsutil.getRowCount("Sheet1");
		int totalcols = xlsutil.getCellCount("Sheet1",1);
		
		String loginData[][] = new String[totalrows][totalcols];

		for(int i=1;i<=totalrows;i++) 
		{
			for(int j=0;j<totalcols;j++) 
			{
				loginData[i-1][j]= ExcelUtils.getCellData("Sheet1", i, j);
			}
		}
				
		return loginData;
	}
	

	@AfterClass
	void tearDown() {
		driver.close();

	}

}
