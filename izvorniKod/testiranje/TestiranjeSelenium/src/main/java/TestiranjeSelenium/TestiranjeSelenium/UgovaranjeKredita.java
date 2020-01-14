package TestiranjeSelenium.TestiranjeSelenium;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;

public class UgovaranjeKredita {

	public static void main(String[] args) throws InterruptedException {
		
		WebDriver driver = new ChromeDriver(); 
		System.setProperty("webdriver.chrome.driver", "C:\\Program Files (x86)\\Chrome Driver\\chromedriver.exe");
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.get("http://104.45.11.92/");
		driver.manage().window().maximize();
		Thread.sleep(500);
		
		zatraziKredit(driver, "klijent", "bb");
		ugovoriKredit(driver, "sluzbenik", "bb");
		
		driver.quit();
	}

	public static void zatraziKredit(WebDriver driver, String username, String pass) throws InterruptedException {
		
		WebElement element = driver.findElement(By.name("username"));
		element.sendKeys(username);
		Thread.sleep(500);
		
		element = driver.findElement(By.name("password"));
		element.sendKeys(pass);
		Thread.sleep(1000);
		
		driver.findElement(By.cssSelector("input[type='submit']")).click();
		Thread.sleep(1000);
		
		driver.findElement(By.id("krediti")).click();
		Thread.sleep(500);
		
		driver.findElement(By.id("newCreditBtn")).click();
		Thread.sleep(1000);
		
		element = driver.findElement(By.name("creditType"));
	     
	    Select select = new Select(element);
	    select.selectByVisibleText("Nenamjenski kredit");
	    Thread.sleep(1000);
	    
	    element = driver.findElement(By.id("amount"));
	    element.sendKeys("50000");
	    Thread.sleep(500);
	    
	    element = driver.findElement(By.id("time"));
	    element.sendKeys("36");
	    Thread.sleep(1000);
	    
	    driver.findElement(By.id("request")).click();
	    Thread.sleep(1000);
	    
	    driver.findElement(By.id("logout")).click();
	    Thread.sleep(500);
	    
	}
	
	public static void ugovoriKredit(WebDriver driver, String username, String pass) throws InterruptedException{
		
		WebElement element = driver.findElement(By.name("username"));
		element.sendKeys(username);
		Thread.sleep(500);
		
		element = driver.findElement(By.name("password"));
		element.sendKeys(pass);
		Thread.sleep(1000);
		
		driver.findElement(By.cssSelector("input[type='submit']")).click();
		Thread.sleep(1000);
		
		driver.findElement(By.id("krediti")).click();
		Thread.sleep(500);
		
		element = driver.findElement(By.name("datRate"));
		element.sendKeys("20");
		Thread.sleep(1000);
		
		driver.findElement(By.id("approve")).click();
		Thread.sleep(500);
		
		driver.findElement(By.id("logout"));
		Thread.sleep(500);
	}
}
