import org.apache.commons.lang3.RandomStringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.Duration;

public class OrderAsGuestTest {

    private WebDriver driver;
    private WebDriverWait wait;

    @BeforeMethod
    public void setup() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("https://auto.pragmatic.bg/index.php?route=product/product&language=en-gb&product_id=40");
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    @AfterMethod
    public void tearDown() {
        driver.quit();
    }

    @Test
    public void orderAsGuestTest() {
        WebElement addToCartButton = driver.findElement(By.id("button-cart"));
        addToCartButton.click();
        //Wait for the div with the alert message to appear
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("alert-success")));
        //Wait for the div with the alert message to disappear
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.className("alert-success")));
        WebElement checkout = driver.findElement(By.cssSelector("a[title='Checkout']"));
        wait.until(ExpectedConditions.elementToBeClickable(checkout));
        checkout.click();
        wait.until(ExpectedConditions.elementToBeClickable(By.id("input-guest")));
        WebElement guestRadioButton = driver.findElement(By.id("input-guest"));
        guestRadioButton.click();
        WebElement firstMameInputField = driver.findElement(By.id("input-firstname"));
        firstMameInputField.sendKeys("Milen");
        WebElement lastNameInputField = driver.findElement(By.id("input-lastname"));
        lastNameInputField.sendKeys("Bozhinov");
        WebElement addressInputField = driver.findElement(By.id("input-shipping-address-1"));
        addressInputField.sendKeys("Ivan Rilski");
        WebElement cityInputField = driver.findElement(By.id("input-shipping-city"));
        cityInputField.sendKeys("Sofia");
        WebElement postcodeInputField = driver.findElement(By.id("input-shipping-postcode"));
        postcodeInputField.sendKeys("1000");


        //These two lines below ARE VALID and are NOT DEPRECATED
        String randomPrefix = RandomStringUtils.secure().nextAlphabetic(7);
        String randomDomain = RandomStringUtils.secure().nextAlphabetic(5);

        //Concatenate strings to assemble valid email address
        String randomEmail = randomPrefix + "@" + randomDomain + ".com";
        WebElement emailInputField = driver.findElement(By.id("input-email"));
        emailInputField.sendKeys(randomEmail);
        wait.until(ExpectedConditions.elementToBeClickable(By.id("input-shipping-country")));
        WebElement countrySelect = driver.findElement(By.id("input-shipping-country"));
        //Create object of type Select
        Select countryDropdownMenu = new Select(countrySelect);
        //Wait for presence of element
        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("option[value='33']")));
        countryDropdownMenu.selectByValue("33");

        //Scroll element into view
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView();", driver.findElement(By.id("input-shipping-zone")));

        WebElement regionSelect = driver.findElement(By.id("input-shipping-zone"));
        //Create object of type Select
        Select regionDropdownMenu = new Select(regionSelect);
        //Wait for presence of element
        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("option[value='498']")));
        regionDropdownMenu.selectByValue("498");
        //TODO Your homework starts here

        driver.findElement(By.id("button-register")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("alert-success")));
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.className("alert-success")));

        //Selecting a shipping method
        WebElement buttonShippingMethods = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("button-shipping-methods")));

        try {
            buttonShippingMethods.click();
        } catch (org.openqa.selenium.ElementClickInterceptedException e) {

            wait.until(ExpectedConditions.elementToBeClickable(buttonShippingMethods));
            js.executeScript("arguments[0].scrollIntoView(true);", buttonShippingMethods);
            buttonShippingMethods.click();
        }
        System.out.println(js.executeScript(
                "return [arguments[0].getBoundingClientRect(), window.innerHeight, window.scrollY];",
                buttonShippingMethods
        ));

        WebElement radioButtonFlat = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("input-shipping-method-flat-flat")));
        try {
            radioButtonFlat.click();
        } catch (org.openqa.selenium.ElementClickInterceptedException e) {
            js.executeScript("arguments[0].scrollIntoView(true);", radioButtonFlat);
            wait.until(ExpectedConditions.visibilityOf(radioButtonFlat));
            radioButtonFlat.click();
        }

        wait.until(ExpectedConditions.elementToBeSelected(By.id("input-shipping-method-flat-flat")));
        driver.findElement(By.id("button-shipping-method")).click();

        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.className(".modal-content")));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("alert-success")));
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.className("alert-success")));

        //Selecting a payment method
        WebElement buttonPaymentMethods = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("button-payment-methods")));
        try {
            buttonPaymentMethods.click();
        } catch (org.openqa.selenium.ElementClickInterceptedException e) {

            wait.until(ExpectedConditions.visibilityOf(buttonPaymentMethods));
            js.executeScript("arguments[0].scrollIntoView(true);", buttonPaymentMethods);
            buttonPaymentMethods.click();
        }

        WebElement radioButtonCod = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("input-payment-method-cod-cod")));
        try {
            radioButtonCod.click();
        } catch (org.openqa.selenium.ElementClickInterceptedException e) {
            js.executeScript("arguments[0].scrollIntoView(true);", radioButtonCod);
            wait.until(ExpectedConditions.visibilityOf(radioButtonCod));
            radioButtonCod.click();
        }

        wait.until(ExpectedConditions.elementToBeSelected(By.id("input-payment-method-cod-cod")));
        driver.findElement(By.id("button-payment-method")).click();

        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.className(".modal-content")));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("alert-success")));
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.className("alert-success")));


        WebElement confirmationButton = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("button-confirm")));
        js.executeScript("arguments[0].scrollIntoView(true);", confirmationButton);
        wait.until(ExpectedConditions.visibilityOf(confirmationButton));
        confirmationButton.click();

        wait.until(ExpectedConditions.titleIs("Your order has been placed!"));
        WebElement confirmationHeader = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id='content']/h1")));
        String confirmationText = confirmationHeader.getText();
        Assert.assertEquals(confirmationText, "Your order has been placed!");

    }
}
