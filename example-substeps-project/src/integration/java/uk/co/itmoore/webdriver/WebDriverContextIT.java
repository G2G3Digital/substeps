package uk.co.itmoore.webdriver;

import java.net.URL;

import org.junit.Test;

import uk.co.itmoore.bddrunner.runner.Scope;
import uk.co.itmoore.runner.DriverType;
import uk.co.itmoore.runner.ExecutionContextSupplier;
import uk.co.itmoore.runner.MutableSupplier;

import static org.junit.Assert.assertThat;

import static org.hamcrest.Matchers.endsWith;

public class WebDriverContextIT {

	private final MutableSupplier<WebDriverContext> webDriverContextSupplier = new ExecutionContextSupplier<WebDriverContext>(
			Scope.SCENARIO, WebDriverContext.EXECUTION_CONTEXT_KEY);

	@Test
	public void testWebdriverContextsCreatedInDifferentThreadsAreDifferent() throws Exception {

		final WebContextRunner webContextRunner1 = new WebContextRunner(
				"testWebdriverContextsCreatedInDifferentThreadsAreDifferent-file1.html", webDriverContextSupplier);
		final WebContextRunner webContextRunner2 = new WebContextRunner(
				"testWebdriverContextsCreatedInDifferentThreadsAreDifferent-file2.html", webDriverContextSupplier);
		final WebContextRunner webContextRunner3 = new WebContextRunner(
				"testWebdriverContextsCreatedInDifferentThreadsAreDifferent-file3.html", webDriverContextSupplier);

		new Thread(webContextRunner1).start();
		new Thread(webContextRunner2).start();
		new Thread(webContextRunner3).start();

		Thread.sleep(3000);

		assertThat(webContextRunner1.currentWebDriverPath(),
				endsWith("testWebdriverContextsCreatedInDifferentThreadsAreDifferent-file1.html"));
		assertThat(webContextRunner2.currentWebDriverPath(),
				endsWith("testWebdriverContextsCreatedInDifferentThreadsAreDifferent-file2.html"));
		assertThat(webContextRunner3.currentWebDriverPath(),
				endsWith("testWebdriverContextsCreatedInDifferentThreadsAreDifferent-file3.html"));
	}

	private class WebContextRunner implements Runnable {
		private final String path;
		private final MutableSupplier<WebDriverContext> webDriverContextSupplier;

		private String currentWebDriverUrl;

		public WebContextRunner(final String path, final MutableSupplier<WebDriverContext> webDriverContextSupplier) {
			this.path = path;
			this.webDriverContextSupplier = webDriverContextSupplier;
			this.currentWebDriverUrl = null;
		}

		public void run() {
			final WebDriverContext webDriverContext = new WebDriverContext(DriverType.HTMLUNIT);
			webDriverContext.getWebDriver().navigate().to(pathURL());
			webDriverContextSupplier.set(webDriverContext);

			while (true) {
				currentWebDriverUrl = currentWebDriverUrl();
				try {
					Thread.sleep(100);
				} catch (final InterruptedException e) {
					e.printStackTrace();
				}
			}
		}

		public String currentWebDriverPath() {
			return currentWebDriverUrl;
		}

		private URL pathURL() {
			return getClass().getResource(path);
		}

		private String currentWebDriverUrl() {
			return webDriverContextSupplier.get().getWebDriver().getCurrentUrl();
		}
	}
}
