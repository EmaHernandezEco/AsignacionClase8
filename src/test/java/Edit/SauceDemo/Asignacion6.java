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
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class Asignacion6 {

	String url ="https://www.saucedemo.com/";
	WebDriver driver;
	File pantalla;
	String rutaEvidencia = "..\\SauceDemo\\Evidencias\\";
	String nombreDocumentoEvidencia = "Evidencia Asignacion6 SauceDemo.docx";
	WebDriverWait espera;
	
	

	
	
	@BeforeSuite
	public void irASauceDemo () throws InvalidFormatException, IOException, InterruptedException {
		driver = new ChromeDriver();
		driver.get(url);
		driver.manage().window().maximize();
		espera = new WebDriverWait(driver, Duration.ofSeconds(10));
		CapturaEvidencia.escribirTituloEnDocumento(rutaEvidencia + nombreDocumentoEvidencia, "Asignacion 6",18);

	}
	
	@Test(dataProvider="Datos Login")
	public void pruebaSauceDemo(String username, String password, String producto, String firstName, String lastName, String zipCode) throws Exception {
		
		
		//Elementos web login
		espera.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@id='login-button']")));		
		WebElement inputUsername = driver.findElement(By.xpath("//input[@id='user-name']"));
		WebElement inputPassword = driver.findElement(By.xpath("//input[@id='password']"));
		WebElement loginButton = driver.findElement(By.xpath("//input[@id='login-button']"));
		
		//Escribe el usuario y la contraseña 
		inputUsername.clear();
		inputPassword.clear();
		inputUsername.sendKeys(username);
		inputPassword.sendKeys(password);
		CapturaEvidencia.capturarPantallaEnDocumento(driver, rutaEvidencia + "img.png",rutaEvidencia + nombreDocumentoEvidencia, "Usuario: " + username + " " + "Contraseña: " + password);
				
		//Hace clic en el botón LOGIN
		loginButton.click();
		
		//Cartel Error Login
		if(driver.findElements(By.xpath("//h3[@data-test='error']")).size()!= 0){
			WebElement cartelError = driver.findElement(By.xpath("//h3[@data-test='error']"));
			if(cartelError.getText().contentEquals("Epic sadface: Password is required")) {
				CapturaEvidencia.capturarPantallaEnDocumento(driver, rutaEvidencia + "img.png",rutaEvidencia + nombreDocumentoEvidencia, "Login Denegado Contraseña no ingresada");
				WebElement btnErrorCartel = driver.findElement(By.xpath("//button[@class='error-button']"));
				btnErrorCartel.click();
				Assert.assertEquals(url,"https://www.saucedemo.com/");
				driver.navigate().refresh();
				return;	
			} else if(cartelError.getText().contentEquals("Epic sadface: Username is required")) {
				CapturaEvidencia.capturarPantallaEnDocumento(driver, rutaEvidencia + "img.png",rutaEvidencia + nombreDocumentoEvidencia, "Login Denegado Usuario no ingresado");
				WebElement btnErrorCartel = driver.findElement(By.xpath("//button[@class='error-button']"));
				btnErrorCartel.click();
				Assert.assertEquals(url,"https://www.saucedemo.com/");
				driver.navigate().refresh();
				return;
			} else{
			
			CapturaEvidencia.capturarPantallaEnDocumento(driver, rutaEvidencia + "img.png",rutaEvidencia + nombreDocumentoEvidencia, "Login Denegado Credenciales Invalidas");
			WebElement btnErrorCartel = driver.findElement(By.xpath("//button[@class='error-button']"));
			btnErrorCartel.click();
			Assert.assertEquals(url,"https://www.saucedemo.com/");
			driver.navigate().refresh();
			return;
			
			}
		}

		//Assertion url pagina
		String urlLogin = driver.getCurrentUrl();
		Assert.assertEquals(urlLogin,"https://www.saucedemo.com/inventory.html");
		
		//Captura de pantalla en documento
		CapturaEvidencia.capturarPantallaEnDocumento(driver, rutaEvidencia + "img.png",rutaEvidencia + nombreDocumentoEvidencia, "Login SauceDemo Exitoso");
		
		//Titulo Compra SauceDemo
		CapturaEvidencia.escribirTituloEnDocumento(rutaEvidencia + nombreDocumentoEvidencia, "Compra en SauceDemo",18);
		
		//Elegir Producto
		String xpathElementProducto = this.elegirProducto(producto);
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
		CapturaEvidencia.capturarPantallaEnDocumento(driver, rutaEvidencia + "img.png",rutaEvidencia + nombreDocumentoEvidencia, "Agregado: " + producto +" al carrito");
		chekoutBtn.click();
		
		//Formulario de Informacion
		espera.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@id='continue']")));		
		WebElement inputFirstName = driver.findElement(By.xpath("//input[@id='first-name']"));
		WebElement inputLastName = driver.findElement(By.xpath("//input[@id='last-name']"));
		WebElement postalCode = driver.findElement(By.xpath("//input[@id='postal-code']"));
		WebElement continueBtn = driver.findElement(By.xpath("//input[@id='continue']"));
		String urlCheckoutStepOne = driver.getCurrentUrl();

		
		inputFirstName.sendKeys(firstName);
		inputLastName.sendKeys(lastName);
		postalCode.sendKeys(zipCode);
		
		//Assertion urlStepOne, captura Evidencia y presionar boton continue
		Assert.assertEquals(urlCheckoutStepOne,"https://www.saucedemo.com/checkout-step-one.html");
		CapturaEvidencia.capturarPantallaEnDocumento(driver, rutaEvidencia + "img.png",rutaEvidencia + nombreDocumentoEvidencia, "Completado Formulario Informacion");
				
		continueBtn.click();
				
		//Cartel Error en CheckOut Information
		if(driver.findElements(By.xpath("//h3[@data-test='error']")).size()!= 0){
			WebElement cartelErrorChekout = driver.findElement(By.xpath("//h3[@data-test='error']"));
			
			if(cartelErrorChekout.getText().contentEquals("Error: First Name is required")) {
				CapturaEvidencia.capturarPantallaEnDocumento(driver, rutaEvidencia + "img.png",rutaEvidencia + nombreDocumentoEvidencia, "Checkout Information First Name no ingresado");
				WebElement btnErrorCartel = driver.findElement(By.xpath("//button[@class='error-button']"));
				btnErrorCartel.click();
				Assert.assertEquals(urlCheckoutStepOne,"https://www.saucedemo.com/checkout-step-one.html");
				driver.findElement(By.xpath("//button[@id='react-burger-menu-btn']")).click();
				Thread.sleep(2000);
				driver.findElement(By.xpath("//a[@id='logout_sidebar_link']")).click();
				return;	
			} else if(cartelErrorChekout.getText().contentEquals("Error: Last Name is required")) {
				CapturaEvidencia.capturarPantallaEnDocumento(driver, rutaEvidencia + "img.png",rutaEvidencia + nombreDocumentoEvidencia, "Checkout Information Last Name no ingresado");
				WebElement btnErrorCartel = driver.findElement(By.xpath("//button[@class='error-button']"));
				btnErrorCartel.click();
				Assert.assertEquals(urlCheckoutStepOne,"https://www.saucedemo.com/checkout-step-one.html");
				driver.findElement(By.xpath("//button[@id='react-burger-menu-btn']")).click();
				Thread.sleep(2000);
				driver.findElement(By.xpath("//a[@id='logout_sidebar_link']")).click();
				return;
			} else{
			
			CapturaEvidencia.capturarPantallaEnDocumento(driver, rutaEvidencia + "img.png",rutaEvidencia + nombreDocumentoEvidencia, "Checkout Information Postal Code no ingresado");
			WebElement btnErrorCartel = driver.findElement(By.xpath("//button[@class='error-button']"));
			btnErrorCartel.click();
			Assert.assertEquals(urlCheckoutStepOne,"https://www.saucedemo.com/checkout-step-one.html");
			driver.findElement(By.xpath("//button[@id='react-burger-menu-btn']")).click();
			Thread.sleep(2000);
			driver.findElement(By.xpath("//a[@id='logout_sidebar_link']")).click();
			return;
			
			}
		}
			
		

		
		
		
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
		CapturaEvidencia.capturarPantallaEnDocumento(driver, rutaEvidencia + "img.png",rutaEvidencia + nombreDocumentoEvidencia, "Regreso a la pagina Products ");

		
		//LogOut de SauceDemo
		Assert.assertEquals(urlLogin,"https://www.saucedemo.com/inventory.html");
		espera.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[@id='react-burger-menu-btn']")));	
		
		driver.findElement(By.xpath("//button[@id='react-burger-menu-btn']")).click();
		Thread.sleep(2000);
		driver.findElement(By.xpath("//a[@id='logout_sidebar_link']")).click();
		CapturaEvidencia.capturarPantallaEnDocumento(driver, rutaEvidencia + "img.png",rutaEvidencia + nombreDocumentoEvidencia, "LogOut Exitoso SauceDemo");

		
		
		
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
	
	
	@DataProvider(name="Datos Login")
	public Object[][] leerDatosLogin() throws Exception {
		return DatosExcel.leerExcel(
								"..\\SauceDemo\\Datos\\Datos_Login.xlsx", "Hoja1");
	
	
	
	
	
	}
}
