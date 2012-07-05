package uk.co.itmoore.substep.impl;

import static org.junit.Assert.fail;

import java.net.URL;

import org.junit.Before;
import org.junit.Test;

import uk.co.itmoore.runner.DriverType;
import uk.co.itmoore.webdriver.WebDriverContext;

import com.google.common.base.Supplier;

public class BaseWebdriverSubStepImplementationsIT {

    private BaseWebdriverSubStepImplementations substepImplementations;

    private WebDriverContext webDriverContext;


    @Before
    public void initialise() {
        this.webDriverContext = new WebDriverContext(DriverType.HTMLUNIT);
        this.substepImplementations = new BaseWebdriverSubStepImplementations(new Supplier<WebDriverContext>() {
            public WebDriverContext get() {
                return webDriverContext;
            }
        });
    }


    @Test
    public void canAssertTagElementContainsText() {
        navigateToFile("assertTagElementContainsText-test.html");

        substepImplementations.assertTagElementContainsText("div", "some text3");
        try {
            substepImplementations.assertTagElementContainsText("div", "some text4");
            fail("Expected an assertion error");
        } catch (final AssertionError ex) {
            // No-op
        }
    }


    @Test
    public void canAssertTagElementContainsAttribute() {
        navigateToFile("assertTagElementContainsAttribute-test.html");

        substepImplementations.assertTagElementContainsAttribute("div", "class", "attr3");
        try {
            substepImplementations.assertTagElementContainsAttribute("div", "class", "attr4");
            fail("Expected an assertion error");
        } catch (final AssertionError ex) {
            // No-op
        }
    }


    private void navigateToFile(final String filePath) {
        final URL resourceURL = getClass().getResource(filePath);
        if (resourceURL == null) {
            throw new IllegalArgumentException("Could not find file " + filePath);
        }
        browseTo(resourceURL);
    }


    private void browseTo(final URL resourceURL) {
        webDriverContext.getWebDriver().navigate().to(resourceURL);
    }
}
