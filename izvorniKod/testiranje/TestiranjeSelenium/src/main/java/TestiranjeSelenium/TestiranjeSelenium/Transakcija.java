package TestiranjeSelenium.TestiranjeSelenium;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;

public class Transakcija {

	public static void main(String[] args) throws InterruptedException{
		
		WebDriver driver = new ChromeDriver(); 
		System.setProperty("webdriver.chrome.driver", "C:\\Program Files (x86)\\Chrome Driver\\chromedriver.exe");
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.get("http://104.45.11.92/");
		driver.manage().window().maximize();
		Thread.sleep(500);
		
		prebaciNovac(driver, "klijent", "bb", "HR1112223334445556667");
		provjeraTransakcije(driver, "lidi1", "lidiluci1");
		driver.quit();
		
	}

	public static void prebaciNovac(WebDriver driver, String username, String pass, String brRac) throws InterruptedException{
		
		WebElement element = driver.findElement(By.name("username"));
		element.sendKeys(username);
		Thread.sleep(500);
		
		element = driver.findElement(By.name("password"));
		element.sendKeys(pass);
		Thread.sleep(1000);
		
		driver.findElement(By.cssSelector("input[type='submit']")).click();
		Thread.sleep(1000);
		
		driver.findElement(By.id("transakcije")).click();
		Thread.sleep(1000);
		
		driver.findElement(By.id("newTransactionBtn")).click();
		Thread.sleep(1000);
		
		element = driver.findElement(By.cssSelector("#newTransaction > form > select"));
	     
	    Select select = new Select(element);
	    select.selectByVisibleText("HR0201235730 : 730.00HRK");
	    Thread.sleep(1000);
	    
	    element = driver.findElement(By.id("approvalAccount"));
	    element.sendKeys(brRac);
	    Thread.sleep(1000);
	    
	    element = driver.findElement(By.id("amount"));
	    element.sendKeys("500");
	    Thread.sleep(1000);
	    
	    driver.findElement(By.cssSelector("input[type='submit']")).click();
		Thread.sleep(1000);
		
		driver.findElement(By.id("logout")).click();
		Thread.sleep(500);
	}
	
	public static void provjeraTransakcije(WebDriver driver, String username, String pass) throws InterruptedException{
		
		WebElement element = driver.findElement(By.name("username"));
		element.sendKeys(username);
		Thread.sleep(500);
		
		element = driver.findElement(By.name("password"));
		element.sendKeys(pass);
		Thread.sleep(500);
		
		driver.findElement(By.cssSelector("input[type='submit']")).click();
		Thread.sleep(500);
		
		driver.findElement(By.id("racuni")).click();
		Thread.sleep(3000);
		
		driver.findElement(By.id("logout")).click();
	}
}
