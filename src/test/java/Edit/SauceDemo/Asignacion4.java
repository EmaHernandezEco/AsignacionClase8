package Edit.SauceDemo;

import org.testng.annotations.Test;
import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.github.javafaker.Faker;

public class Asignacion4 {
	
	private String url ="http://automationpractice.pl/index.php";
	
	WebDriver driver = new ChromeDriver();
	WebDriverWait espera = new WebDriverWait(driver, Duration.ofSeconds(10));
	
	
	@Test
	public void registroUsuario () {
		
		//Accede a http://automationpractice.pl/index.php
		driver.get(url);
		driver.manage().window().maximize();
		
		//Presiona Sign In
		WebElement signIn = driver.findElement(By.xpath("//a[contains(text(),'Sign in')]"));
		signIn.click();
		
		//Elementos Web  Sign In
		WebElement emailImput = driver.findElement(By.xpath("//input[@id='email_create']"));
		WebElement createAccountBtn = driver.findElement(By.xpath("//button[@id='SubmitCreate']"));

		//Ingresa email y presiona Create an Account
		Faker faker = new Faker ();
		String email = faker.internet().emailAddress();
		
		emailImput.sendKeys(email);
		createAccountBtn.click();
		
		
		//Elementos Web Formulario
		
		espera.until(ExpectedConditions.elementToBeClickable(By.cssSelector("#id_gender1")));		
		WebElement titleMrRadioBtn = driver.findElement(By.cssSelector("#id_gender1"));
		WebElement firstNameImput = driver.findElement(By.xpath("//input[@id='customer_firstname']"));
		WebElement lastNameImput = driver.findElement(By.xpath("//input[@id='customer_lastname']"));
		WebElement passwordImput = driver.findElement(By.xpath("//input[@id='passwd']"));
		Select dayOfBirth 	 = new Select (driver.findElement(By.xpath("//select[@id='days']")));
		Select monthOfBirth  = new Select (driver.findElement(By.xpath("//select[@id='months']")));
		Select yearOfBirth 	 = new Select (driver.findElement(By.xpath("//select[@id='years']")));
		WebElement registerBtn = driver.findElement(By.xpath("//button[@id='submitAccount']"));
		
		
		//Completa formulario
		titleMrRadioBtn.click();
		firstNameImput.sendKeys("Emanuel");
		lastNameImput.sendKeys("Hernandez");
		passwordImput.sendKeys("12345");
		dayOfBirth.selectByValue("11");
		monthOfBirth.selectByValue("9");
		yearOfBirth.selectByValue("1993");
		
		//Presiona boton Register
		registerBtn.click();
		
		
			
		
	}
	

}
