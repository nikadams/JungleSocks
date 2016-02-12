/**
 *   File Name: Driver.java<br>
 *
 *   Adams, Nik<br>
 *   Created: Jan 21, 2016
 *
 */

package com.webdriver.na.junglesocks.util;

import java.io.File;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * Driver //ADDD (description of class)
 * <p>
 * //ADDD (description of core fields)
 * <p>
 * //ADDD (description of core methods)
 *
 * @author Adams, Nik
 * @version 1.0.0
 * @since 1.0
 *
 */
public class Driver {

	private static WebDriver driver;
	private static String MAC_DRIVER = "/webdrivers/chromedriver";
	private static String WINDOWS_DRIVER = "/webdrivers/chromedriver.exe";

	public static WebDriver driverInit(String browser) {

		switch (browser.toLowerCase()) {
		case "firefox":
			driver = new FirefoxDriver();
			break;
		case "chrome":
			// OS type
			if (System.getProperty("os.name").contains("Mac")) {
				File cDriver = new File(Driver.class.getResource(MAC_DRIVER).getFile());

				// Is it executable?
				if (!cDriver.canExecute()) {
					cDriver.setExecutable(true);
				}
				System.setProperty("webdriver.chrome.driver", Driver.class.getResource(MAC_DRIVER).getFile());

			} else {
				System.setProperty("webdriver.chrome.driver", Driver.class.getResource(WINDOWS_DRIVER).getFile());
			}
			driver = new ChromeDriver();
			break;
		case "edge":
			String edgeDriverPath = System.getProperty("user.dir")
					+ "\\src\\test\\resources\\webdrivers\\MicrosoftWebDriver.exe";
			System.setProperty("webdriver.edge.driver", edgeDriverPath);
			EdgeOptions options = new EdgeOptions();
			options.setPageLoadStrategy("eager");
			driver = new EdgeDriver();
			break;
		default:
			driver = new FirefoxDriver();
			break;
		}
		// driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

		return driver;

	}

	public static void waitUntil(long time, By locator) {
		WebDriverWait wait = new WebDriverWait(driver, time);
		wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
	}
}
