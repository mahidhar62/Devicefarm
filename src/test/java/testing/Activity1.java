package testing;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class Activity1 {

	public static void main(String[] args) {
	
		//System.setProperty("webdriver.chrome.driver", "/ChromeDriver/New folder/chromedriver.exe");
		System.setProperty("webdriver.chrome.driver", "/Users/saifa/ChromeDriver/chromedriver.exe");
		WebDriver 	driver = new ChromeDriver();
		driver.get("http://www.google.com");
		System.out.println(driver.getTitle());
		//driver.quit();
	}

}
