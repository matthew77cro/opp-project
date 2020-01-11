package TestiranjeSelenium.TestiranjeSelenium;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;

public class DodavanjeNoveKartice{

	public static void main (String[] args)  throws InterruptedException{
		
		WebDriver driver = new ChromeDriver(); 
		System.setProperty("webdriver.chrome.driver", "C:\\Program Files (x86)\\Chrome Driver\\chromedriver.exe");
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.get("http://104.45.11.92/");
		driver.manage().window().maximize();
		Thread.sleep(1000);
		
		loginIZahtjev(driver, "klijent", "bb");
		sluzbenikKartica(driver, "sluzbenik", "bb");
		
		driver.quit();
	}
	

	public static void loginIZahtjev(WebDriver driver, String username, String pass) throws InterruptedException {
		
		WebElement element = driver.findElement(By.name("username")); 
		element.sendKeys(username);
		Thread.sleep(2000);
		
		element = driver.findElement(By.name("password"));
	    element.sendKeys(pass);
		Thread.sleep(2000);
		driver.findElement(By.cssSelector("input[type='submit']")).click();
		Thread.sleep(2000);
		
	     driver.findElement(By.id("kartice")).click();
	     Thread.sleep(2000);
	     
	     driver.findElement(By.id("newCardBtn")).click();
	     Thread.sleep(1000);
	     
	     element = driver.findElement(By.name("cardType"));
	     
	     Select select = new Select(element);
	     select.selectByVisibleText("5 : Visa");
	     Thread.sleep(2000);
	     
	     driver.findElement(By.cssSelector("input[type='submit']")).click();
	     Thread.sleep(1000);
	     
	     driver.findElement(By.id("logout")).click();
	     Thread.sleep(1000);
	     
	}
	
	public static void sluzbenikKartica(WebDriver driver, String username, String pass) throws InterruptedException{
		
		WebElement element = driver.findElement(By.name("username")); 
		element.sendKeys(username);
		Thread.sleep(2000);
		
		element = driver.findElement(By.name("password"));
	    element.sendKeys(pass);
		Thread.sleep(2000);
		driver.findElement(By.cssSelector("input[type='submit']")).click();
		Thread.sleep(2000);
		
		driver.findElement(By.cssSelector("#krediti")).click();
		
		element= driver.findElement(By.name("brojKartice"));
		element.sendKeys("30350540491");
		Thread.sleep(1000);
		
		element = driver.findElement(By.name("datRate"));
		element.sendKeys("15");
		Thread.sleep(1000);
		
		element = driver.findElement(By.name("limit"));
		element.sendKeys("2500,00");
		Thread.sleep(1000);
		
		element = driver.findElement(By.name("kamStopa"));
		element.sendKeys("0");
		Thread.sleep(1000);
		
		element = driver.findElement(By.name("valjanost"));
		element.sendKeys("05022025");
		Thread.sleep(1000);
		
		driver.findElement(By.id("approve")).click();
		Thread.sleep(1000);
		
		driver.findElement(By.id("logout")).click();
		Thread.sleep(1000);
		
	}
	
}
