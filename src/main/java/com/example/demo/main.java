package com.example.demo;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.io.FileHandler;

import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;

public class main {
	private WebDriver driver;
	FileWriter fichero;
	String msj;

	@Before
	public void setUp() {
		try {
			fichero = new FileWriter("F:/Visual Studio Code proyects/demo2/demo/fichero.txt");
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.setProperty("webdriver.chrome.driver", "./src/main/resources/chromedriver/chromedriver.exe");
		driver = new ChromeDriver();
		msj = "Abriendo una ventana de Chrome";
		mostrarMensaje();
		driver.get("https://www.consultoriaglobal.com.ar/cgweb/");
		msj = "Establece y se dirige a esa url";
		mostrarMensaje();
	}

	private void mostrarMensaje() {
		System.out.println(msj);
		try {
			fichero.write(msj + "\n");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testGoogle() {
		WebElement search = null;
		driver.findElement(By.id("menu-item-1364")).click();
		msj = "Busca el item de Contactenos y hace click";
		mostrarMensaje();

		search = driver.findElement(By.name("your-name"));
		msj = "Busca el item de Nombre";
		mostrarMensaje();
		search.clear();
		search.sendKeys("Alan Vicente");
		msj = "Completa el campo con 'Alan Vicente'";
		mostrarMensaje();

		search = driver.findElement(By.name("your-email"));
		msj = "Busca el item de Email";
		mostrarMensaje();
		search.clear();
		search.sendKeys("alanvicente");
		msj = "Completa el campo con 'alanvicente'";
		mostrarMensaje();

		search = driver.findElement(By.name("your-subject"));
		msj = "Busca el item de Asunto";
		mostrarMensaje();
		search.clear();
		search.sendKeys("Ejemplo");
		msj = "Completa el campo con 'Ejemplo'";
		mostrarMensaje();

		search = driver.findElement(By.name("your-message"));
		msj = "Busca el item de Su mensaje";
		mostrarMensaje();
		search.clear();
		search.sendKeys("Mensaje de ejemplo");
		msj = "Completa el campo con 'Ejemplo'";
		mostrarMensaje();

		search = driver.findElement(By.name("captcha-636"));
		msj = "Busca el item de Captcha";
		mostrarMensaje();
		search.clear();
		String captcha = leerCaptcha();
		msj = "El captcha encontrado es: " + captcha;
		mostrarMensaje();

		String finalCaptcha = captcha.replaceAll("[^a-zA-Z0-9]", "");
		msj = "Reemplaza todo lo que no entre en ese bloque por espacios vacios\nEl captcha final es: " + finalCaptcha;
		mostrarMensaje();

		search.sendKeys(finalCaptcha);
		msj = "Completa el campo con el captcha final";
		mostrarMensaje();

		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

		search.submit();
		msj = "Envia el formulario";
		mostrarMensaje();

		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		msj = "Busca si hay elemento erroneo";
		mostrarMensaje();
		if (driver.findElement(By.className("wpcf7-not-valid-tip")).isDisplayed()) {
			msj = "El elemento erroneo es: " + driver.findElement(By.className("wpcf7-not-valid-tip")).getText();
			mostrarMensaje();
		} else {
			msj = "No hay errores.";
			mostrarMensaje();
		}

	}

	private String leerCaptcha() {
		WebElement imagen = driver.findElement(By.xpath("(//*[@id=\"wpcf7-f1297-p370-o1\"]//following::img)[1]"));
		msj = "Busca la imagen captcha por xpath";
		mostrarMensaje();

		File src = imagen.getScreenshotAs(OutputType.FILE);
		msj = "Realiza una screenshot del captcha";
		mostrarMensaje();

		String path = "F:\\Visual Studio Code proyects\\demo2\\demo\\src\\main\\resources\\captchaImagenes\\captcha.png";
		msj = "Asigno a path la ruta de la imagen";
		mostrarMensaje();
		try {
			FileHandler.copy(src, new File(path));
			Thread.sleep(2000);
			ITesseract img = new Tesseract();
			msj = "Crea el objeto Tesseract y luego lo devuelve como String";
			mostrarMensaje();

			img.setDatapath("F:\\Visual Studio Code proyects\\demo2\\demo\\tessdata");
			img.setLanguage("eng");
			return img.doOCR(new File(path));
		} catch (IOException | InterruptedException | TesseractException e) {
			e.printStackTrace();
		}
		return null;
	}

	@After
	public void cerrar() {
		try {
			fichero.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
//		driver.quit();
	}
}
