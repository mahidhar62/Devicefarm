package testing;

import org.openqa.selenium.By;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;

import org.testng.annotations.Test;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.devicefarm.*;
import software.amazon.awssdk.services.devicefarm.model.*;
import software.amazon.awssdk.auth.credentials.EnvironmentVariableCredentialsProvider;
import software.amazon.awssdk.auth.credentials.SystemPropertyCredentialsProvider;
import software.amazon.awssdk.auth.credentials.internal.SystemSettingsCredentialsProvider;

import java.net.MalformedURLException;
import java.net.URL;

public class TestNGBasics {

	private static RemoteWebDriver driver;

	@BeforeTest
	void setUp() throws MalformedURLException {

		
		String myProjectARN = "arn:aws:devicefarm:us-west-2:503085204531:testgrid-project:9e6db8fd-737b-4a63-819b-028e112a0747";
		DeviceFarmClient client = DeviceFarmClient.builder().credentialsProvider(EnvironmentVariableCredentialsProvider.create()).region(Region.US_WEST_2).build();
		CreateTestGridUrlRequest request = CreateTestGridUrlRequest.builder().expiresInSeconds(300)
				.projectArn(myProjectARN).build();
		CreateTestGridUrlResponse response = client.createTestGridUrl(request);

		URL testGridUrl = new URL(response.url());
		DesiredCapabilities desired_capabilities = new DesiredCapabilities();
        desired_capabilities.setCapability("browserName","chrome");
        desired_capabilities.setCapability("browserVersion", "latest");
        desired_capabilities.setCapability("platform", "windows");
        desired_capabilities.setCapability("Build Number ", "1.0");
		
        driver = new RemoteWebDriver(testGridUrl, desired_capabilities);
		
		//driver = new RemoteWebDriver(testGridUrl, DesiredCapabilities.chrome());
		
	}

	@Test
	public void userLogin() {
		driver.manage().window().maximize();
		driver.navigate().to("https://the-internet.herokuapp.com/login");
		driver.findElement(By.id("username")).sendKeys("tomsmith");
		driver.findElement(By.id("password")).sendKeys("SuperSecretPassword!");
		driver.findElement(By.className("radius")).click();
		Assert.assertTrue(driver.getCurrentUrl().contains("secure"));
	}

	@AfterTest
	void tearDown() {
		driver.quit();
		System.out.println("Test Passed");
	}

}
