package Edit.SauceDemo;

import java.io.File;
import java.io.IOException;
import java.time.Duration;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

public class Asignacion5 {
	
	String url ="https://www.saucedemo.com/";
	WebDriver driver;
	File pantalla;
	String rutaEvidencia = "..\\SauceDemo\\Evidencias\\";
	String nombreDocumentoEvidencia = "Evidencia Asignacion5 SauceDemo.docx";
	WebDriverWait espera;
	

	
	
	@BeforeSuite
	public void irASauceDemo () {
		driver = new ChromeDriver();
		driver.get(url);
		driver.manage().window().maximize();
		espera = new WebDriverWait(driver, Duration.ofSeconds(10));
	}
	
	
	@Test(priority=1)
	public void loginSauceDemo() throws InvalidFormatException, IOException, InterruptedException {
		//Elementos web login
		espera.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@id='login-button']")));		
		WebElement inputUsername = driver.findElement(By.xpath("//input[@id='user-name']"));
		WebElement inputPassword = driver.findElement(By.xpath("//input[@id='password']"));
		WebElement loginButton = driver.findElement(By.xpath("//input[@id='login-button']"));

		//Escribe el usuario (standard_user)  y la contraseña (secret_sauce)
		inputUsername.sendKeys("standard_user");
		inputPassword.sendKeys("secret_sauce");
				
		//Hace clic en el botón LOGIN
		loginButton.click();
		
		//Assertion url pagina
		String urlLogin = driver.getCurrentUrl();
		Assert.assertEquals(urlLogin,"https://www.saucedemo.com/inventory.html");
		
		//Captura de pantalla en documento
		CapturaEvidencia.capturarPantallaEnDocumento(driver, rutaEvidencia + "img.png",rutaEvidencia + nombreDocumentoEvidencia, "Login SauceDemo Exitoso");
		
		
		}
	
	@Test(priority=2)
	public void compraSauceDemo() throws InvalidFormatException, IOException, InterruptedException {
		
		//Titulo Compra SauceDemo
		CapturaEvidencia.escribirTituloEnDocumento(rutaEvidencia + nombreDocumentoEvidencia, "Compra en SauceDemo",18);
		
		//Elegir Producto
		String xpathElementProducto = this.elegirProducto("bike light");
		espera.until(ExpectedConditions.elementToBeClickable(By.xpath(xpathElementProducto)));		
		WebElement addCarritoProducto = driver.findElement(By.xpath(xpathElementProducto));
		
		addCarritoProducto.click();
		
		//Presionar sobre el carrito
		WebElement carrito = driver.findElement(By.xpath("//body/div[@id='root']/div[@id='page_wrapper']/div[@id='contents_wrapper']/div[@id='header_container']/div[1]/div[3]/a[1]"));
		carrito.click();
		
		//Assertion sobre url y Presionar Checkout
		espera.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[@id='checkout']")));		
		WebElement chekoutBtn = driver.findElement(By.xpath("//button[@id='checkout']"));
		
		
		String urlLoginCarrito = driver.getCurrentUrl();
		Assert.assertEquals(urlLoginCarrito,"https://www.saucedemo.com/cart.html");
		CapturaEvidencia.capturarPantallaEnDocumento(driver, rutaEvidencia + "img.png",rutaEvidencia + nombreDocumentoEvidencia, "Agregado Producto al carrito");
		
		chekoutBtn.click();
		
		//Formulario de Informacion
		espera.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@id='continue']")));		
		WebElement inputFirstName = driver.findElement(By.xpath("//input[@id='first-name']"));
		WebElement inputLastName = driver.findElement(By.xpath("//input[@id='last-name']"));
		WebElement postalCode = driver.findElement(By.xpath("//input[@id='postal-code']"));
		WebElement continueBtn = driver.findElement(By.xpath("//input[@id='continue']"));

		
		inputFirstName.sendKeys("Emanuel");
		inputLastName.sendKeys("Hernandez");
		postalCode.sendKeys("2000");
		
		//Assertion urlStepOne, captura Evidencia y presionar boton continue
		String urlCheckoutStepOne = driver.getCurrentUrl();
		Assert.assertEquals(urlCheckoutStepOne,"https://www.saucedemo.com/checkout-step-one.html");
		CapturaEvidencia.capturarPantallaEnDocumento(driver, rutaEvidencia + "img.png",rutaEvidencia + nombreDocumentoEvidencia, "Completado Formulario Informacion");
		
		continueBtn.click();
		
		//Assertion urlStepTwo, captura Evidencia y presionar boton finish
		espera.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[@id='finish']")));		
		WebElement finishBtn = driver.findElement(By.xpath("//button[@id='finish']"));
		
		
		String urlCheckoutStepTwo = driver.getCurrentUrl();
		Assert.assertEquals(urlCheckoutStepTwo,"https://www.saucedemo.com/checkout-step-two.html");
		CapturaEvidencia.capturarPantallaEnDocumento(driver, rutaEvidencia + "img.png",rutaEvidencia + nombreDocumentoEvidencia, "Descripcion del producto,envio y precio final");
		
		finishBtn.click();
		
		//Assertion texto de producto ordenado y urlCheckoutComplete, captura Evidencia y presionar boton Back Home
		espera.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[@id='back-to-products']")));	
		WebElement thankOrder = driver.findElement(By.className("complete-header"));
		WebElement orderDescription = driver.findElement(By.className("complete-text"));
		WebElement backHomeBtn = driver.findElement(By.xpath("//button[@id='back-to-products']"));
		
		Assert.assertEquals(thankOrder.getText(),"THANK YOU FOR YOUR ORDER");
		Assert.assertEquals(orderDescription.getText(),"Your order has been dispatched, and will arrive just as fast as the pony can get there!");
		CapturaEvidencia.capturarPantallaEnDocumento(driver, rutaEvidencia + "img.png",rutaEvidencia + nombreDocumentoEvidencia, "Compra Finalizada");
		
		backHomeBtn.click();

		
		

		
	}
	
	
	@AfterSuite
	public void cierraNavegador() {
		driver.quit();
		
	}
	
	private String elegirProducto(String producto) {
		char ch = '-'; 
		String productoConvertido = producto.replace(' ', ch);
		String xpathProducto = "//button[contains(@id,'"+productoConvertido+ "')]";
		return xpathProducto;
		
		
	}
	
	
	
	
	

}
