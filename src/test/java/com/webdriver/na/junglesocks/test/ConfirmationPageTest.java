/**
 *   File Name: ShoppingCartTest.java<br>
 *
 *   Adams, Nik<br>
 *   Created: Feb 8, 2016
 *
 */

package com.webdriver.na.junglesocks.test;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.webdriver.na.junglesocks.pageobject.CheckOutPage;
import com.webdriver.na.junglesocks.pageobject.HomePage;
import com.webdriver.na.junglesocks.util.Driver;
import com.webdriver.na.junglesocks.util.TestData;

/**
 * ShoppingCartTest //ADDD (description of class)
 * <p>
 * Test Scenario for Shopping Cart entry of the Jungle Socks website
 * <p>
 * verifySalesTax(): This is the main test where the sales tax applicable to
 * each state is verified against the server side calculated values. Both the
 * sales tax and total amount are verified in this scenario. Each state which
 * has a unique sales tax is verified (CA,NY,MN) along with 2 other states
 * (equivalence testing practice is applied here)
 *
 * invalidCart(): Specifies an invalid cart entry such as no state selected.
 * This causes an error page to be shown currently.
 *
 * invalidQty(): This test was used to verify that the shopping cart does not
 * allow the user to select more quantity than is available. As the server side
 * quantity values are hard-coded this test does not make much sense in this
 * context however the functionality exists for "future" use. This test is
 * currently not running as part of the suite.
 *
 * verifyCatalogPricing(): Verifies the catalog pricing of each item. The
 * expected values are used as input from the Data Provider while the actual
 * values are retrieved from the website.
 *
 * @author Adams, Nik
 * @version 1.0.0
 * @since 1.0
 *
 */
public class ConfirmationPageTest extends TestData {
	final static double CA_TAX = .08;
	final static String CHECKOUT_ERROR_TXT = "We're sorry, but something went wrong.";
	final static String INVALID_QTY_ERROR = " is an invalid Quantity for available inventory";
	final static String INVALID_TAX_ERROR = "The taxes for the state are not correct";
	final static double MN_TAX = 0;
	final static double NY_TAX = .06;
	final static String TOTAL_AMT_ERROR = "The total amount is not equal";

	private static double calculateSubTotal(CheckOutPage page) {
		return page.getTotalItemPrice("zebra") + page.getTotalItemPrice("lion") + page.getTotalItemPrice("elephant")
				+ page.getTotalItemPrice("giraffe");
	}

	private static double calculateTax(CheckOutPage page, double state) {
		return calculateSubTotal(page) * state;
	}

	private WebDriver driver;

	@Test(dataProvider = "provider")
	public void invalidCart(String zebraQty, String lionQty, String elephantQty, String giraffeQty, String state) {
		HomePage homepage = PageFactory.initElements(this.driver, HomePage.class);
		homepage.navigateToHomePage();
		homepage.typeQtyForZebra(zebraQty);
		homepage.typeQtyForLion(lionQty);
		homepage.typeQtyForElephant(elephantQty);
		homepage.typeQtyForGiraffe(giraffeQty);

		// User does not supply state for sales tax which causes are error
		CheckOutPage checkoutpage = homepage.clickCheckoutBtn();
		Assert.assertEquals(checkoutpage.getCheckoutPageError(), CHECKOUT_ERROR_TXT);
	}

	// @Test(dataProvider = "provider")
	public void invalidQty(String zebraQty, String lionQty, String elephantQty, String giraffeQty, String state) {
		HomePage homepage = PageFactory.initElements(this.driver, HomePage.class);
		homepage.navigateToHomePage();
		homepage.typeQtyForZebra(zebraQty);
		Assert.assertFalse(homepage.getItemQty("zebra") >= Integer.parseInt(zebraQty),
				zebraQty + this.INVALID_QTY_ERROR);
		homepage.typeQtyForLion(lionQty);
		Assert.assertTrue(homepage.getItemQty("lion") >= Integer.parseInt(lionQty), lionQty + this.INVALID_QTY_ERROR);
		homepage.typeQtyForElephant(elephantQty);
		Assert.assertTrue(homepage.getItemQty("elephant") >= Integer.parseInt(elephantQty),
				elephantQty + this.INVALID_QTY_ERROR);
		homepage.typeQtyForGiraffe(giraffeQty);
		Assert.assertTrue(homepage.getItemQty("giraffe") >= Integer.parseInt(giraffeQty),
				giraffeQty + this.INVALID_QTY_ERROR);
		homepage.selectStateDropDown("California");

		CheckOutPage checkoutpage = homepage.clickCheckoutBtn();

	}

	@BeforeMethod
	@Parameters("browser")
	public void setup(String browser) {

		this.driver = Driver.driverInit(browser);

	}

	@AfterMethod
	public void tearDown() {
		this.driver.quit();
	}

	@Test(dataProvider = "provider")
	public void verifyCatalogPricing(final String zebraPrice, final String lionPrice, final String elephantPrice,
			final String giraffePrice) {
		HomePage homepage = PageFactory.initElements(this.driver, HomePage.class);
		homepage.navigateToHomePage();
		Assert.assertEquals(homepage.getItemPrice("zebra"), Integer.parseInt(zebraPrice));
		Assert.assertEquals(homepage.getItemPrice("lion"), Integer.parseInt(lionPrice));
		Assert.assertEquals(homepage.getItemPrice("elephant"), Integer.parseInt(elephantPrice));
		Assert.assertEquals(homepage.getItemPrice("giraffe"), Integer.parseInt(giraffePrice));
	}

	@Test(dataProvider = "provider")
	public void verifySalesTax(String zebraQty, String lionQty, String elephantQty, String giraffeQty, String state) {
		double salesTax = 0;
		HomePage homepage = PageFactory.initElements(this.driver, HomePage.class);
		homepage.navigateToHomePage();
		homepage.typeQtyForZebra(zebraQty);
		homepage.typeQtyForLion(lionQty);
		homepage.typeQtyForElephant(elephantQty);
		homepage.typeQtyForGiraffe(giraffeQty);
		homepage.selectStateDropDown(state);

		CheckOutPage checkoutpage = homepage.clickCheckoutBtn();
		double subtotal = calculateSubTotal(checkoutpage);

		switch (state.toLowerCase()) {
		case "california":
			salesTax = CA_TAX;
			break;
		case "new york":
			salesTax = NY_TAX;
			break;
		case "minnesota":
			salesTax = MN_TAX;
			break;
		default:
			salesTax = 0.05;
		}
		double tax = calculateTax(checkoutpage, salesTax);
		double total = subtotal + tax;
		Assert.assertEquals(tax, checkoutpage.getTaxesTxt(), this.INVALID_TAX_ERROR);
		Assert.assertEquals(total, checkoutpage.getTotalAmountTxt(), this.TOTAL_AMT_ERROR);

	}

}
