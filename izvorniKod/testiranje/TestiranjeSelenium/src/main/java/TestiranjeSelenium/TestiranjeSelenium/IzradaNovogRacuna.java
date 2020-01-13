package TestiranjeSelenium.TestiranjeSelenium;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;

public class IzradaNovogRacuna {

	public static void main(String[] args) throws InterruptedException{
		
		WebDriver driver = new ChromeDriver(); 
		System.setProperty("webdriver.chrome.driver", "C:\\Program Files (x86)\\Chrome Driver\\chromedriver.exe");
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.get("http://104.45.11.92/");
		driver.manage().window().maximize();
		Thread.sleep(500);
		
		izradiRacun(driver, "bankar", "bb", "32165498710");
		

		driver.quit();
	}

	public static void izradiRacun(WebDriver driver, String username, String pass, String oib) throws InterruptedException{
		
		WebElement element = driver.findElement(By.name("username"));
		element.sendKeys(username);
		Thread.sleep(500);
		
		element = driver.findElement(By.name("password"));
		element.sendKeys(pass);
		Thread.sleep(500);
		
		driver.findElement(By.cssSelector("input[type='submit']")).click();
		Thread.sleep(500);
		
		driver.findElement(By.cssSelector("#racuni")).click();
		Thread.sleep(500);
		
		driver.findElement(By.id("addAccountBtn")).click();
		Thread.sleep(500);
		
		element = driver.findElement(By.id("client-oib"));
		element.sendKeys(oib);
		Thread.sleep(500);
		
		element = driver.findElement(By.name("vrstaRacuna"));
	     
	    Select select = new Select(element);
	    select.selectByVisibleText("Tekući račun");
	    Thread.sleep(500);
	    
	    element = driver.findElement(By.id("account-number"));
	    element.sendKeys("HR1112223334445556667");
	    Thread.sleep(500);
	    
	    element = driver.findElement(By.id("account-limit"));
	    element.sendKeys("2000");
	    Thread.sleep(500);
	    
	    element = driver.findElement(By.id("account-rate"));
	    element.sendKeys("0");
	    Thread.sleep(500);
	    
	    driver.findElement(By.cssSelector("input[type='submit']")).click();
		Thread.sleep(500);
		
		driver.findElement(By.id("logout")).click();
		Thread.sleep(500);
	}
}
