package com.udacity.jwdnd.course1.cloudstorage;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import java.io.File;
import java.time.Duration;
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CloudStorageApplicationTests {

	@LocalServerPort
	private int port;

	private WebDriver driver;

	@BeforeAll
	static void beforeAll() {
		WebDriverManager.chromedriver().setup();
	}

	@BeforeEach
	public void beforeEach() {
		this.driver = new ChromeDriver();
	}

	@AfterEach
	public void afterEach() {
		if (this.driver != null) {
			driver.quit();
		}
	}

	@Test
	public void getLoginPage() {
		driver.get("http://localhost:" + this.port + "/login");
		Assertions.assertEquals("Login", driver.getTitle());
	}

	/**
	 * PLEASE DO NOT DELETE THIS method.
	 * Helper method for Udacity-supplied sanity checks.
	 **/
	private void doMockSignUp(String firstName, String lastName, String userName, String password){
		// Create a dummy account for logging in later.

		// Visit the sign-up page.
		WebDriverWait webDriverWait = new WebDriverWait(driver, Duration.ofSeconds(2));
		driver.get("http://localhost:" + this.port + "/signup");
		webDriverWait.until(ExpectedConditions.titleContains("Sign Up"));
		
		// Fill out credentials
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputFirstName")));
		WebElement inputFirstName = driver.findElement(By.id("inputFirstName"));
		inputFirstName.click();
		inputFirstName.sendKeys(firstName);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputLastName")));
		WebElement inputLastName = driver.findElement(By.id("inputLastName"));
		inputLastName.click();
		inputLastName.sendKeys(lastName);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputUsername")));
		WebElement inputUsername = driver.findElement(By.id("inputUsername"));
		inputUsername.click();
		inputUsername.sendKeys(userName);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputPassword")));
		WebElement inputPassword = driver.findElement(By.id("inputPassword"));
		inputPassword.click();
		inputPassword.sendKeys(password);

		// Attempt to sign up.
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("buttonSignUp")));
		WebElement buttonSignUp = driver.findElement(By.id("buttonSignUp"));
		buttonSignUp.click();

		/* Check that the sign up was successful. 
		// You may have to modify the element "success-msg" and the sign-up 
		// success message below depening on the rest of your code.
		*/
//		Assertions.assertTrue(driver.findElement(By.id("success-msg")).getText().contains("You successfully signed up!"));
	}

	
	
	/**
	 * PLEASE DO NOT DELETE THIS method.
	 * Helper method for Udacity-supplied sanity checks.
	 **/
	private void doLogIn(String userName, String password)
	{
		// Log in to our dummy account.
		driver.get("http://localhost:" + this.port + "/login");
		WebDriverWait webDriverWait = new WebDriverWait(driver, Duration.ofSeconds(2));

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputUsername")));
		WebElement loginUserName = driver.findElement(By.id("inputUsername"));
		loginUserName.click();
		loginUserName.sendKeys(userName);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputPassword")));
		WebElement loginPassword = driver.findElement(By.id("inputPassword"));
		loginPassword.click();
		loginPassword.sendKeys(password);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("login-button")));
		WebElement loginButton = driver.findElement(By.id("login-button"));
		loginButton.click();

		webDriverWait.until(ExpectedConditions.titleContains("Home"));

	}

	/**
	 * PLEASE DO NOT DELETE THIS TEST. You may modify this test to work with the 
	 * rest of your code. 
	 * This test is provided by Udacity to perform some basic sanity testing of 
	 * your code to ensure that it meets certain rubric criteria. 
	 * 
	 * If this test is failing, please ensure that you are handling redirecting users 
	 * back to the login page after a succesful sign up.
	 * Read more about the requirement in the rubric: 
	 * https://review.udacity.com/#!/rubrics/2724/view 
	 */
	private void openTab(WebDriverWait wait, String tabId, String paneId) {
		WebElement tab = driver.findElement(By.id(tabId));
		((JavascriptExecutor) driver).executeScript("arguments[0].click();", tab);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(paneId)));
	}

	/**
	 * After a note/credential save, the app redirects to /result?success.
	 * This helper waits for the Result page and clicks the "here" link to go back to /home.
	 */
	private void returnHomeFromResult(WebDriverWait wait) {
		wait.until(ExpectedConditions.titleContains("Result"));
		// Verify it was a success (not an error)
		Assertions.assertTrue(driver.getCurrentUrl().contains("success"),
				"Expected success result page but got: " + driver.getCurrentUrl());
		// Click the "here" link — Thymeleaf renders th:href="@{/home}" as href="/home" at runtime
		driver.findElement(By.cssSelector("a[href='/home']")).click();
		wait.until(ExpectedConditions.titleContains("Home"));
	}

	/** PLEASE DO NOT DELETE THIS TEST. **/
	@Test
	public void testRedirection() {
		// Create a test account
		doMockSignUp("Redirection","Test","RT","123");
		
		// Check if we have been redirected to the log in page.
		Assertions.assertEquals("http://localhost:" + this.port + "/login", driver.getCurrentUrl());
	}

	/**
	 * PLEASE DO NOT DELETE THIS TEST. You may modify this test to work with the 
	 * rest of your code. 
	 * This test is provided by Udacity to perform some basic sanity testing of 
	 * your code to ensure that it meets certain rubric criteria. 
	 * 
	 * If this test is failing, please ensure that you are handling bad URLs 
	 * gracefully, for example with a custom error page.
	 * 
	 * Read more about custom error pages at: 
	 * https://attacomsian.com/blog/spring-boot-custom-error-page#displaying-custom-error-page
	 */
	@Test
	public void testBadUrl() {
		// Create a test account
		doMockSignUp("URL","Test","UT","123");
		doLogIn("UT", "123");
		
		// Try to access a random made-up URL.
		driver.get("http://localhost:" + this.port + "/some-random-page");
		Assertions.assertFalse(driver.getPageSource().contains("Whitelabel Error Page"));
	}


	/**
	 * PLEASE DO NOT DELETE THIS TEST. You may modify this test to work with the 
	 * rest of your code. 
	 * This test is provided by Udacity to perform some basic sanity testing of 
	 * your code to ensure that it meets certain rubric criteria. 
	 * 
	 * If this test is failing, please ensure that you are handling uploading large files (>1MB),
	 * gracefully in your code. 
	 * 
	 * Read more about file size limits here: 
	 * https://spring.io/guides/gs/uploading-files/ under the "Tuning File Upload Limits" section.
	 */
	@Test
	public void testLargeUpload() {
		// Create a test account
		doMockSignUp("Large File","Test","LFT","123");
		doLogIn("LFT", "123");

		// Try to upload an arbitrary large file
		WebDriverWait webDriverWait = new WebDriverWait(driver, Duration.ofSeconds(2));
		String fileName = "upload5m.zip";

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("fileUpload")));
		WebElement fileSelectButton = driver.findElement(By.id("fileUpload"));
		fileSelectButton.sendKeys(new File(fileName).getAbsolutePath());

		WebElement uploadButton = driver.findElement(By.id("uploadButton"));
		uploadButton.click();
		try {
			webDriverWait.until(ExpectedConditions.presenceOfElementLocated(By.id("success")));
		} catch (org.openqa.selenium.TimeoutException e) {
			System.out.println("Large File upload failed");
		}
		Assertions.assertFalse(driver.getPageSource().contains("HTTP Status 403 – Forbidden"));
	}

	// -------------------------------------------------------------------------
	// AUTH TESTS
	// -------------------------------------------------------------------------

	@Test
	public void testHomeNotAccessibleWithoutLogin() {
		driver.get("http://localhost:" + this.port + "/home");
		Assertions.assertTrue(driver.getCurrentUrl().contains("/login"));
	}

	@Test
	public void testSignUpLoginLogoutFlow() {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(3));

		doMockSignUp("Auth", "Flow", "authflow", "pass123");
		Assertions.assertTrue(driver.getCurrentUrl().contains("/login"));

		doLogIn("authflow", "pass123");
		Assertions.assertTrue(driver.getCurrentUrl().contains("/home"));

		driver.findElement(By.cssSelector("#logoutDiv button[type='submit']")).click();
		wait.until(ExpectedConditions.urlContains("/login"));

		driver.get("http://localhost:" + this.port + "/home");
		Assertions.assertTrue(driver.getCurrentUrl().contains("/login"));
	}

	// -------------------------------------------------------------------------
	// NOTE TESTS
	// -------------------------------------------------------------------------

	@Test
	public void testCreateNoteAndVerify() {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(3));

		doMockSignUp("Note", "Create", "notecreate", "pass123");
		doLogIn("notecreate", "pass123");

		openTab(wait, "nav-notes-tab", "nav-notes");

		wait.until(ExpectedConditions.elementToBeClickable(By.id("add-note-btn")));
		driver.findElement(By.id("add-note-btn")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("noteModal")));

		driver.findElement(By.id("note-title")).sendKeys("Test Note Title");
		driver.findElement(By.id("note-description")).sendKeys("Test Note Description");
		driver.findElement(By.id("noteSubmitButton")).click();

		// Handle result page -> click "here" to go back to home
		returnHomeFromResult(wait);

		// Open Notes tab and verify the note is in the list
		openTab(wait, "nav-notes-tab", "nav-notes");
		wait.until(ExpectedConditions.visibilityOfElementLocated(
				By.xpath("//div[@id='nav-notes']//th[contains(text(),'Test Note Title')]")));

		String pageSource = driver.getPageSource();
		Assertions.assertTrue(pageSource.contains("Test Note Title"));
		Assertions.assertTrue(pageSource.contains("Test Note Description"));
	}

	@Test
	public void testEditNoteAndVerify() {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(3));

		doMockSignUp("Note", "Edit", "noteedit", "pass123");
		doLogIn("noteedit", "pass123");

		// Create a note first
		openTab(wait, "nav-notes-tab", "nav-notes");
		driver.findElement(By.id("add-note-btn")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("noteModal")));
		driver.findElement(By.id("note-title")).sendKeys("Original Title");
		driver.findElement(By.id("note-description")).sendKeys("Original Description");
		driver.findElement(By.id("noteSubmitButton")).click();

		// Handle result page -> back to home
		returnHomeFromResult(wait);

		// Open Notes tab and click Edit — button has class "edit-note-btn" (add this to home.html)
		openTab(wait, "nav-notes-tab", "nav-notes");
		wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("#nav-notes .edit-note-btn")));
		driver.findElement(By.cssSelector("#nav-notes .edit-note-btn")).click();

		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("noteModal")));

		WebElement titleField = driver.findElement(By.id("note-title"));
		titleField.clear();
		titleField.sendKeys("Updated Title");

		WebElement descField = driver.findElement(By.id("note-description"));
		descField.clear();
		descField.sendKeys("Updated Description");

		driver.findElement(By.id("noteSubmitButton")).click();

		// Handle result page -> back to home
		returnHomeFromResult(wait);

		// Verify updated values in the list
		openTab(wait, "nav-notes-tab", "nav-notes");
		wait.until(ExpectedConditions.visibilityOfElementLocated(
				By.xpath("//div[@id='nav-notes']//th[contains(text(),'Updated Title')]")));

		String pageSource = driver.getPageSource();
		Assertions.assertTrue(pageSource.contains("Updated Title"));
		Assertions.assertTrue(pageSource.contains("Updated Description"));
		Assertions.assertFalse(pageSource.contains("Original Title"));
	}

	@Test
	public void testDeleteNoteAndVerify() {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(3));

		doMockSignUp("Note", "Delete", "notedelete", "pass123");
		doLogIn("notedelete", "pass123");

		// Create a note first
		openTab(wait, "nav-notes-tab", "nav-notes");
		driver.findElement(By.id("add-note-btn")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("noteModal")));
		driver.findElement(By.id("note-title")).sendKeys("Note To Delete");
		driver.findElement(By.id("note-description")).sendKeys("This will be deleted");
		driver.findElement(By.id("noteSubmitButton")).click();

		// Handle result page -> back to home
		returnHomeFromResult(wait);

		// Open Notes tab and click Delete — <a class="btn btn-danger delete-note-btn"> (add class to home.html)
		openTab(wait, "nav-notes-tab", "nav-notes");
		wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("#nav-notes .delete-note-btn")));
		driver.findElement(By.cssSelector("#nav-notes .delete-note-btn")).click();

		// Delete also redirects to result page -> back to home
		returnHomeFromResult(wait);

		// Verify note is gone
		openTab(wait, "nav-notes-tab", "nav-notes");
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("nav-notes")));
		Assertions.assertFalse(driver.getPageSource().contains("Note To Delete"));
	}

	// -------------------------------------------------------------------------
	// CREDENTIAL TESTS
	// -------------------------------------------------------------------------

	@Test
	public void testCreateCredentialAndVerify() {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(3));

		doMockSignUp("Cred", "Create", "credcreate", "pass123");
		doLogIn("credcreate", "pass123");

		openTab(wait, "nav-credentials-tab", "nav-credentials");

		// "Add a New Credential" button — add id="add-credential-btn" to home.html
		wait.until(ExpectedConditions.elementToBeClickable(By.id("add-credential-btn")));
		driver.findElement(By.id("add-credential-btn")).click();

		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("credentialModal")));

		driver.findElement(By.id("credential-url")).sendKeys("https://testsite.com");
		driver.findElement(By.id("credential-username")).sendKeys("testuser");
		driver.findElement(By.id("credential-password")).sendKeys("testpassword");

		// "Save changes" triggers $('#credentialSubmit').click() — add id="credential-save-btn" to home.html
		driver.findElement(By.id("credential-save-btn")).click();

		// Handle result page -> back to home
		returnHomeFromResult(wait);

		openTab(wait, "nav-credentials-tab", "nav-credentials");
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("credentialTable")));

		String pageSource = driver.getPageSource();
		Assertions.assertTrue(pageSource.contains("https://testsite.com"));
		Assertions.assertTrue(pageSource.contains("testuser"));
	}

	@Test
	public void testEditCredentialAndVerify() {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(3));

		doMockSignUp("Cred", "Edit", "crededit", "pass123");
		doLogIn("crededit", "pass123");

		// Create a credential first
		openTab(wait, "nav-credentials-tab", "nav-credentials");
		driver.findElement(By.id("add-credential-btn")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("credentialModal")));
		driver.findElement(By.id("credential-url")).sendKeys("https://original.com");
		driver.findElement(By.id("credential-username")).sendKeys("originaluser");
		driver.findElement(By.id("credential-password")).sendKeys("originalpass");
		driver.findElement(By.id("credential-save-btn")).click();

		// Handle result page -> back to home
		returnHomeFromResult(wait);

		// Open Credentials tab and click Edit — add class "edit-credential-btn" to home.html
		openTab(wait, "nav-credentials-tab", "nav-credentials");
		wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("#nav-credentials .edit-credential-btn")));
		driver.findElement(By.cssSelector("#nav-credentials .edit-credential-btn")).click();

		// Modal opens with decrypted password pre-filled
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("credentialModal")));

		WebElement urlField = driver.findElement(By.id("credential-url"));
		urlField.clear();
		urlField.sendKeys("https://updated.com");

		WebElement usernameField = driver.findElement(By.id("credential-username"));
		usernameField.clear();
		usernameField.sendKeys("updateduser");

		WebElement passwordField = driver.findElement(By.id("credential-password"));
		passwordField.clear();
		passwordField.sendKeys("updatedpass");

		driver.findElement(By.id("credential-save-btn")).click();

		// Handle result page -> back to home
		returnHomeFromResult(wait);

		// Verify updated values in the list
		openTab(wait, "nav-credentials-tab", "nav-credentials");
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("credentialTable")));

		String pageSource = driver.getPageSource();
		Assertions.assertTrue(pageSource.contains("https://updated.com"));
		Assertions.assertTrue(pageSource.contains("updateduser"));
		Assertions.assertFalse(pageSource.contains("https://original.com"));
	}

	@Test
	public void testDeleteCredentialAndVerify() {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(3));

		doMockSignUp("Cred", "Delete", "creddelete", "pass123");
		doLogIn("creddelete", "pass123");

		// Create a credential first
		openTab(wait, "nav-credentials-tab", "nav-credentials");
		driver.findElement(By.id("add-credential-btn")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("credentialModal")));
		driver.findElement(By.id("credential-url")).sendKeys("https://todelete.com");
		driver.findElement(By.id("credential-username")).sendKeys("deleteuser");
		driver.findElement(By.id("credential-password")).sendKeys("deletepass");
		driver.findElement(By.id("credential-save-btn")).click();

		// Handle result page -> back to home
		returnHomeFromResult(wait);

		// Open Credentials tab and click Delete — add class "delete-credential-btn" to home.html
		openTab(wait, "nav-credentials-tab", "nav-credentials");
		wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("#nav-credentials .delete-credential-btn")));
		driver.findElement(By.cssSelector("#nav-credentials .delete-credential-btn")).click();

		// Delete also goes through result page -> back to home
		returnHomeFromResult(wait);

		// Verify credential is gone
		openTab(wait, "nav-credentials-tab", "nav-credentials");
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("nav-credentials")));
		Assertions.assertFalse(driver.getPageSource().contains("https://todelete.com"));
	}
}