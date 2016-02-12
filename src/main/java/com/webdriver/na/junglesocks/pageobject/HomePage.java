/**
 *   File Name: WelcomePage.java<br>
 *
 *   Adams, Nik<br>
 *   Created: Feb 8, 2016
 *
 */

package com.webdriver.na.junglesocks.pageobject;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

/**
 * WelcomePage //ADDD (description of class)
 * <p>
 * //ADDD (description of core fields)
 * <p>
 * Methods for servicing the Catalog page of Jungle Socks
 *
 * @author Adams, Nik
 * @version 1.0.0
 * @since 1.0
 *
 */
public class HomePage {

	final static String HOMEPAGE_URL = "https://jungle-socks.herokuapp.com/";

	@CacheLookup
	@FindBy(name = "commit")
	private WebElement checkoutBtn;

	private WebDriver driver;

	// FindBY Annotations to create webElements for Items on Welcome page

	@FindBy(id = "line_item_quantity_elephant")
	private WebElement elephantQtyTxtField;

	@FindBy(id = "line_item_quantity_giraffe")
	private WebElement giraffeQtyTxtField;

	@FindBy(id = "line_item_quantity_lion")
	private WebElement lionQtyTxtField;

	@FindBy(name = "state")
	private WebElement stateSelectDropDown;

	@FindBy(id = "line_item_quantity_zebra")
	private WebElement zebraQtyTxtField;

	public HomePage(WebDriver driver) {
		this.driver = driver;
	}

	public CheckOutPage clickCheckoutBtn() {
		this.checkoutBtn.click();
		return PageFactory.initElements(this.driver, CheckOutPage.class);
	}

	/*
	 * Return multiple elements (price,qty) from the catalog based on the item
	 * name
	 */
	public List<WebElement> getitemCatalog(String name) {

		By itemLocator = By.cssSelector("tr.line_item." + name.toLowerCase() + " >td");
		List<WebElement> elements = this.driver.findElements(itemLocator);
		return elements;

	}

	/*
	 * Gets the item price displayed on the catalog page. Search is executed by
	 * item name
	 */
	public int getItemPrice(String name) {
		By itemLocator = By.cssSelector("tr.line_item." + name.toLowerCase() + " >td");
		List<WebElement> elements = this.driver.findElements(itemLocator);
		return Integer.parseInt(elements.get(1).getText());
	}

	/*
	 * Gets the item available quantity displayed on the catalog page. Search is
	 * executed by item name
	 */
	public int getItemQty(String name) {
		By itemLocator = By.cssSelector("tr.line_item." + name.toLowerCase() + " >td");
		List<WebElement> elements = this.driver.findElements(itemLocator);
		return Integer.parseInt(elements.get(2).getText());
	}

	/* Returns the selected option displayed on the dropdown for state taxes */
	public String getStateDropDown() {
		Select select = new Select(this.stateSelectDropDown);
		return select.getFirstSelectedOption().getText();
	}

	public HomePage navigateToHomePage() {
		this.driver.get(HOMEPAGE_URL);
		return this;
	}

	/* Selects the state option from the taxes drop down */
	public HomePage selectStateDropDown(String state) {
		Select select = new Select(this.stateSelectDropDown);
		select.selectByVisibleText(state);
		return this;
	}

	public HomePage typeQtyForElephant(String qty) {
		this.elephantQtyTxtField.sendKeys(qty);
		return this;
	}

	public HomePage typeQtyForGiraffe(String qty) {
		this.giraffeQtyTxtField.sendKeys(qty);
		return this;
	}

	public HomePage typeQtyForLion(String qty) {
		this.lionQtyTxtField.sendKeys(qty);
		return this;
	}

	public HomePage typeQtyForZebra(String qty) {
		this.zebraQtyTxtField.sendKeys(qty);
		return this;
	}

}
