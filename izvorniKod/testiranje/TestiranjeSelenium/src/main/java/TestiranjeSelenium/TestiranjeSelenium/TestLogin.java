package TestiranjeSelenium.TestiranjeSelenium;

import org.openqa.selenium.chrome.ChromeDriver;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.*;

public class TestLogin {

	public static void main(String[] args) {
		
		WebDriver driver = new ChromeDriver(); 
		System.setProperty("webdriver.chrome.driver", "C:\\Program Files (x86)\\Chrome Driver\\chromedriver.exe");
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		
		loginTest(driver, "klijent", "bb");
	        driver.quit();
	}
	
	public static void loginTest(WebDriver driver, String username, String pass) {
		driver.get("http://104.45.11.92/");
		
		WebElement element = driver.findElement(By.name("username")); 
		element.sendKeys(username);
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		element = driver.findElement(By.name("password"));
	    element.sendKeys(pass);
	    try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    driver.findElement(By.cssSelector("input[type='submit']")).click();
	    try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    
		
		
	}
}
