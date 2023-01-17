package Edit.SauceDemo;

import org.testng.annotations.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class Asignacion2 {
	
	private String url ="https://www.saucedemo.com/";
	

	@Test
	public void loginChrome() throws InterruptedException {
		
		WebDriver navegador = new ChromeDriver();
		//Abre el navegador Chrome y Accede a la página https://www.saucedemo.com/
		navegador.get(url);
		navegador.manage().window().maximize();
		
		//Elementos web pagina
		WebElement inputUsername = navegador.findElement(By.xpath("//input[@id='user-name']"));
		WebElement inputPassword = navegador.findElement(By.xpath("//input[@id='password']"));
		WebElement loginButton = navegador.findElement(By.xpath("//input[@id='login-button']"));
		
		
		
		//Escribe el usuario (standard_user)  y la contraseña (secret_sauce)
		inputUsername.sendKeys("standard_user");
		inputPassword.sendKeys("secret_sauce");
		
		//Hace clic en el botón LOGIN
		loginButton.click();
		
		
		//Cierra el navegador
		navegador.quit();

		
		
		
	}
	
	
	
	
	
	
	
	
			
			

}
