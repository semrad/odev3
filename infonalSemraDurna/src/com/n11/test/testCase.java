package com.n11.test;


import java.io.IOException;
import java.util.concurrent.TimeUnit;
import org.junit.After;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;


@SuppressWarnings("unused")
public class testCase {
	private WebDriver driver;
	private String baseUrl;
	private boolean acceptNextAlert = true;
	private StringBuffer verificationErrors = new StringBuffer();

	@Before
	public void setUp() throws Exception {
		driver = new FirefoxDriver();
		baseUrl = "http://www.n11.com/";
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		 
	}

	@Test
	public void test() throws Exception {
		Actions action = new Actions(driver);
		
		driver.get(baseUrl); //www.n11.com  
		assertEquals("n11.com - Al��veri�in U�urlu Adresi",driver.getTitle());//<title> kontrolu yaparak ana sayfa kontrolu		
		assertEquals("http://www.n11.com/",driver.getCurrentUrl());//URL kontrol
		System.out.println("Ana Sayfa: " + driver.getCurrentUrl());
		 
		driver.findElement(By.xpath("//a[contains(@title,'Giri� Yap')]")).click();
		assertEquals("Giri� Yap - n11.com" ,driver.getTitle()); //<title> kontrolu yaparak giris sayfasi kontrolu
		assertEquals("https://www.n11.com/giris-yap", driver.getCurrentUrl());//URL kontrol 
		System.out.println("Giri� Sayfas�: " + driver.getCurrentUrl());
		
		driver.findElement(By.id("email")).clear();
		driver.findElement(By.id("email")).sendKeys("durna_semra@hotmail.com");//kullanici e-mail adresi girisi		
		driver.findElement(By.id("password")).clear();
		driver.findElement(By.id("password")).sendKeys("08260536s");//kullanici sifre girisi		
		driver.findElement(By.id("loginButton")).click();
		
		assertEquals("semra durna", driver.findElement(By.cssSelector(".username")).getText());//ana sayfadan kullanici adi kontrolu
		System.out.println("Kullan�c� Ad�: " + driver.findElement(By.cssSelector(".username")).getText());
		
		driver.findElement(By.id("searchData")).clear();
		driver.findElement(By.id("searchData")).sendKeys("samsung");//samsung arama kelimesi girilir 
		
		
		driver.findElement(By.cssSelector(".searchBtn")).click();//arama dugmesine basilir
		
		assertEquals("Samsung", driver.findElement(By.xpath("//div[contains(@class,'resultText')]/h1")).getText());//Samsung sonuc onayi "Samsung i�in xxx.xxx sonu� bulundu."
		assertEquals("Samsung - n11.com" , driver.getTitle()); //<title> icinde Samsung kelimesini eslestirme
		System.out.println("Aranan �r�n: "+driver.findElement(By.xpath("//div[contains(@class,'resultText')]/h1")).getText() );
		driver.findElement(By.xpath("//div[@id='contentListing']/div/div/div[2]/div[4]/a[2]")).click(); // 2.sayfa gecis icin linke click yapilmasi
		assertEquals("2", driver.findElement(By.cssSelector(".currentPage")).getAttribute("value"));//2.sayfanin gosterildigin onaylanmasi
		 
		Thread.sleep(3000);
		action.click(driver.findElement(By.xpath("//div[contains(@id,'contentListing')]/div/div/div[2]/section/div[2]/ul/li[3]/div/div[2]/span[2]"))).perform(); //3.urunu favorilere ekleme
		String urunAdi = driver.findElement(By.xpath("//div[contains(@id,'contentListing')]/div/div/div[2]/section/div[2]/ul/li[3]/div/div/a/h3")).getText(); //Urun ismini urunAdi string degerine atama 
		 
		Thread.sleep(3000); 
		
		driver.findElement(By.xpath("//a[contains(.,'Hesab�m')]")).click(); // Hesab�m sayfasi linki
		
		//Hesabim sayfasi kontrolu
		assertEquals("Hesab�m - n11.com", driver.getTitle()); //<title>
		assertEquals("https://www.n11.com/hesabim", driver.getCurrentUrl());//URL
		System.out.println("Hesab�m sayfas�: " + driver.getCurrentUrl());
		driver.findElement(By.xpath("//a[contains(@href, 'https://www.n11.com/hesabim/favorilerim')]")).click(); //Favorilerim link
		
		Thread.sleep(5000);
		//Favorilerim sayfasi kontrol
		assertEquals("Favorilerim - n11.com",driver.getTitle());//<title>
		assertEquals("https://www.n11.com/hesabim/favorilerim",driver.getCurrentUrl());//URL
		String  urunFav = driver.findElement(By.xpath("//td[contains(@class,'productTitle')]/p/a")).getText(); 
		assertEquals(urunAdi,urunFav);System.out.println(urunAdi + " / " + urunFav); //Kars�last�rma
		driver.findElement(By.xpath("//table[@id='watchList']/tbody/tr/td[1]/input")).click();
		driver.findElement(By.xpath("//table[@id='watchList']/tbody/tr/td[7]/a")).click();
		assertEquals("�zledi�iniz bir �r�n bulunmamaktad�r.",driver.findElement(By.xpath("//table[@id='watchList']/tbody/tr/td/div")).getText());//Takip edilen urun listesinin bos oldugu kontrolu
		System.out.println("�zlenen �r�nler: " +driver.findElement(By.xpath("//table[@id='watchList']/tbody/tr/td/div")).getText() );
		Thread.sleep(3000);
	}

	@After
	public void tearDown() throws Exception {
		driver.quit();
		String verificationErrorString = verificationErrors.toString();
		if (!"".equals(verificationErrorString)) {
			fail(verificationErrorString);
		}
	}

	private boolean isElementPresent(By by) {
		try {
			driver.findElement(by);
			return true;
		} catch (NoSuchElementException e) {
			return false;
		}
	}

	private boolean isAlertPresent() {
		try {
			driver.switchTo().alert();
			return true;
		} catch (NoAlertPresentException e) {
			return false;
		}
	}

	private String closeAlertAndGetItsText() {
		try {
			Alert alert = driver.switchTo().alert();
			String alertText = alert.getText();
			if (acceptNextAlert) {
				alert.accept();
			} else {
				alert.dismiss();
			}
			return alertText;
		} finally {
			acceptNextAlert = true;
		}
	}
        
        public static void main(String[] args) {
        Result result=JUnitCore.runClasses(testCase.class);
    }
}
