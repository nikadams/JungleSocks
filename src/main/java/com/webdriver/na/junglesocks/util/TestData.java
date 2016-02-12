/**
 *   File Name: TestData.java<br>
 *
 *   Adams, Nik<br>
 *   Created: Feb 11, 2016
 *
 */

package com.webdriver.na.junglesocks.util;

import java.io.IOException;
import java.lang.reflect.Method;

import org.testng.annotations.DataProvider;

import jxl.read.biff.BiffException;

/**
 * TestData //ADDD (description of class)
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
public class TestData {

	@DataProvider
	public Object[][] provider(Method method) throws BiffException, IOException {
		final String file = System.getProperty("user.dir") + "\\src\\test\\resources\\test-input\\"
				+ this.getClass().getSimpleName() + ".xls";
		return ReadExcel.getDataFromExcel(file, method.getName(), "StartPoint", "EndPoint");

	}

}
