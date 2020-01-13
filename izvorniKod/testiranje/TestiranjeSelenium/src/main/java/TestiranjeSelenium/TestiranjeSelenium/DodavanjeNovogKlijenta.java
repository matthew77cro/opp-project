package TestiranjeSelenium.TestiranjeSelenium;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;

public class DodavanjeNovogKlijenta {

	public static void main(String[] args) throws InterruptedException{
		
		WebDriver driver = new ChromeDriver(); 
		System.setProperty("webdriver.chrome.driver", "C:\\Program Files (x86)\\Chrome Driver\\chromedriver.exe");
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.get("http://104.45.11.92/");
		driver.manage().window().maximize();
		Thread.sleep(1000);
		
		stvaranjeNovogKlijentaBankar(driver, "bankar", "bb");
		stvaranjeKorisnickogRacunaAdmin(driver, "admin", "bb", "32165498710");
		prijavaKorisnika(driver, "lidi1", "123456789");
		
		driver.quit();
		
	}

	public static void stvaranjeNovogKlijentaBankar(WebDriver driver, String username, String pass) throws InterruptedException{
		
		WebElement element = driver.findElement(By.name("username"));
		element.sendKeys(username);
		Thread.sleep(1000);
		
		element = driver.findElement(By.name("password"));
		element.sendKeys(pass);
		Thread.sleep(1000);
		
		driver.findElement(By.cssSelector("input[type='submit']")).click();
		Thread.sleep(1000);
		
		driver.findElement(By.id("klijenti")).click();
		Thread.sleep(1000);
		
		driver.findElement(By.id("addProfileBtn")).click();
		Thread.sleep(1000);
		
		element = driver.findElement(By.name("ime"));
		element.sendKeys("Lidija");
		Thread.sleep(1000);
		
		element = driver.findElement(By.name("prezime"));
		element.sendKeys("Lucić");
		Thread.sleep(1000);
		
		element = driver.findElement(By.name("adresa"));
		element.sendKeys("Ilica 45");
		Thread.sleep(1000);
		
		element = driver.findElement(By.name("pbr"));
		element.sendKeys("10000");
		Thread.sleep(1000);
		
		element = driver.findElement(By.cssSelector("#oib"));
		element.sendKeys("32165498710");
		Thread.sleep(1000);
		
		element = driver.findElement(By.name("datRod"));
		element.sendKeys("2111993");
		Thread.sleep(1000);
		
		element = driver.findElement(By.name("email"));
		element.sendKeys("llucic@gmail.com");
		Thread.sleep(1000);
		
		driver.findElement(By.cssSelector("input[type='submit']")).click();
		Thread.sleep(1000);
		
		driver.findElement(By.id("logout")).click();
		Thread.sleep(1000);
		
	}
	
	public static void stvaranjeKorisnickogRacunaAdmin(WebDriver driver, String username, String pass, String oib) throws InterruptedException{
		
		WebElement element = driver.findElement(By.name("username"));
		element.sendKeys(username);
		Thread.sleep(1000);
		
		element = driver.findElement(By.name("password"));
		element.sendKeys(pass);
		Thread.sleep(1000);
		
		driver.findElement(By.cssSelector("input[type='submit']")).click();
		Thread.sleep(1000);
		
		driver.findElement(By.id("racuni")).click();
		Thread.sleep(1000);
		
		element = driver.findElement(By.name("oib"));
		element.sendKeys(oib);
		Thread.sleep(1000);
		
		driver.findElement(By.cssSelector("#searchProfileBtn")).click();
		Thread.sleep(1000);
		
		driver.findElement(By.id("createAccount")).click();
		Thread.sleep(1000);
		
		element = driver.findElement(By.id("username"));
		element.sendKeys("lidi1");
		Thread.sleep(1000);
		
		
		element = driver.findElement(By.name("password"));
		element.sendKeys("123456789");
		Thread.sleep(1000);
		
		element = driver.findElement(By.name("razOvlasti"));
	     
	    Select select = new Select(element);
	    select.selectByVisibleText("Klijent");
	    Thread.sleep(1000);
	    
	    driver.findElement(By.id("addAccount")).click();
	    Thread.sleep(1000);
	    
	    driver.findElement(By.id("logout")).click();
	    Thread.sleep(500);
	    
	}
	
	public static void prijavaKorisnika(WebDriver driver, String username, String pass) throws InterruptedException{
		
		WebElement element = driver.findElement(By.name("username"));
		element.sendKeys(username);
		Thread.sleep(500);
		
		element = driver.findElement(By.name("password"));
		element.sendKeys(pass);
		Thread.sleep(500);
		
		driver.findElement(By.cssSelector("input[type='submit']")).click();
		Thread.sleep(500);
		
		element = driver.findElement(By.id("oldPassword"));
		element.sendKeys(pass);
		Thread.sleep(500);
		
		element = driver.findElement(By.id("newPassword"));
		element.sendKeys("lidiluci1");
		Thread.sleep(500);
		
		element = driver.findElement(By.id("confirmNewPassword"));
		element.sendKeys("lidiluci1");
		Thread.sleep(500);
		
		driver.findElement(By.cssSelector("input[type='submit']")).click();
		Thread.sleep(500);
		
	}
}
