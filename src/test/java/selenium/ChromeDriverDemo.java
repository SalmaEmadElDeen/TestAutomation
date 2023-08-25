package selenium;


import com.google.common.io.Files;
import datahandeler.propertiesConfig;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.*;
import datahandeler.*;

import java.io.File;
import java.io.IOException;
import java.time.Duration;

public class ChromeDriverDemo {



    WebDriver driver;
    @BeforeMethod
    public void chromeDriverSetup()
    {
        System.setProperty("webdriver.chrome.driver", "resources/chromedriver.exe");
        driver= new ChromeDriver();
        driver.get("https://the-internet.herokuapp.com/");
        driver.manage().window().maximize();
    }

//    @DataProvider(name = "testData")
//    public static Object[][] loginUserData()
//    {
//        return new Object[][]
//                {
//                        {"tomsmith","SuperSecretPassword!"},
//                        {"salma","test@123"}
//                };
//    }



    @Test
    public void elementInteraction() throws InterruptedException {

        Thread.sleep(3000);
       WebElement formAuthenticationLink = driver.findElement(By.linkText("Form Authentication"));
       formAuthenticationLink.click();
       WebElement userNameField = driver.findElement(By.id("username"));
       WebElement passwordField= driver.findElement(By.id("password"));
       WebElement loginButton = driver.findElement(By.cssSelector("button.radius"));
       //Enter Value on name and password Field
        /////
        String userName = propertiesConfig.loginUserData.getProperty("username");
        String password = propertiesConfig.loginUserData.getProperty("password");
        /////
//        userNameField.sendKeys("tomsmith");
//        passwordField.sendKeys("SuperSecretPassword!");
//        loginButton.click();

        userNameField.sendKeys(userName);
        passwordField.sendKeys(password);
        loginButton.click();


        //Get Text from Web element
        WebElement successLoginMessage= driver.findElement(By.id("flash"));
        String successMessage= successLoginMessage.getText();
        System.out.println(successMessage);
        //use assertion from testng to verify that test output is correct
        Assert.assertEquals(successMessage,"You logged into a secure area!\n" +
                "×");
    }

   //Before run this test comment the before test method to go google link directly
    @Test
    public void elementInteractionWithSubmitAndClear()
    {
        driver.get("https://www.google.com/");
        WebElement searchField = driver.findElement(By.id("APjFqb"));
        searchField.sendKeys("selenium");
        searchField.clear();
        searchField.sendKeys("selenium");
        searchField.submit();
    }

    @Test
    public void contextClick()
    {
        WebElement contextMenu = driver.findElement(By.linkText("Context Menu"));
        contextMenu.click();
        Actions action= new Actions(driver);
        WebElement contextElement= driver.findElement(By.id("hot-spot"));
        action.contextClick(contextElement);
    }

    @Test
    public void KeyToPress()
    {
        WebElement keyPress= driver.findElement(By.linkText("Key Presses"));
        keyPress.click();
        WebElement keyPressField = driver.findElement(By.id("target"));
        keyPressField.sendKeys(Keys.ENTER);

    }

    @Test
    public void dragAndDrop() throws InterruptedException {
        WebElement dragAndDropLink = driver.findElement(By.linkText("Drag and Drop"));
        dragAndDropLink.click();
        WebElement sourceElement= driver.findElement(By.id("column-a"));
        WebElement targetElement = driver.findElement(By.id("column-b"));
        Actions actions = new Actions(driver);
        //Performing the drag and drop action
        actions.dragAndDrop(sourceElement,targetElement).build().perform();

        //Another solution to drag and drop
//        Action dragAndDrop=actions.clickAndHold(sourceElement)
//                .moveToElement(targetElement)
//                .release(targetElement)
//                .build();
//        dragAndDrop.perform();



    }


    //Before run this test comment the before test method to go google link directly
    @Test
    public void radioButton()
    {
        driver.get("https://formy-project.herokuapp.com/form");
        WebElement highSchoolRadioButton = driver.findElement(By.id("radio-button-1"));
        highSchoolRadioButton.click();
        Assert.assertTrue(highSchoolRadioButton.isSelected());

    }

    @Test
    public void alerts()
    {
        WebElement alertLink = driver.findElement(By.linkText("JavaScript Alerts"));
        alertLink.click();

        /////////////////////////////////
        //Alert pop up
        WebElement alertButton= driver.findElement(By.xpath("//button[text()='Click for JS Alert']"));
        alertButton.click();
        driver.switchTo().alert().accept();
        /////////////////////////////////
        //Confirm pop up
        WebElement confirmButton = driver.findElement(By.xpath("//button[text()='Click for JS Confirm']"));
        confirmButton.click();
        driver.switchTo().alert().accept();
        confirmButton.click();
        driver.switchTo().alert().dismiss();
        ////////////////////////////////////
        //Prompt pop up
        WebElement promptButton = driver.findElement(By.xpath("//button[text()='Click for JS Prompt']"));
        promptButton.click();
        driver.switchTo().alert().sendKeys("TestText");
        driver.switchTo().alert().accept();

    }

    //Must add selenium support dependency on pom file to access Select class
    @Test
    public void dropDown()
    {
        WebElement dropDownLink = driver.findElement(By.linkText("Dropdown"));
        dropDownLink.click();
        WebElement dropDownList= driver.findElement(By.id("dropdown"));
        Select select= new Select(dropDownList);
        //select by index
        select.selectByIndex(1);
        //select by value on html tag
       // select.selectByValue("1");
        //select by visible text
        // select.selectByVisibleText("Option 1");
    }

    @Test
    public void framesInteraction()
    {
        WebElement frameLink = driver.findElement(By.linkText("WYSIWYG Editor"));
        frameLink.click();
        //Get frame by id
        driver.switchTo().frame("mce_0_ifr");

        //get frame by index
        //driver.switchTo().frame(0);

        //get frame by web element
//        WebElement frameElement= driver.findElement(By.id("mce_0_ifr"));
//        driver.switchTo().frame(frameElement);

        WebElement textArea = driver.findElement(By.id("tinymce"));
        textArea.clear();

        //To switch the parent frame (bigger frame)
        driver.switchTo().parentFrame();
    }

    @Test
    public void browserNavigation()
    {
          driver.navigate().to("https://www.google.com/");

          driver.navigate().back();

          driver.navigate().forward();

          driver.navigate().refresh();
    }

    @Test
    public void waitTypes()
    {

        WebElement dynamicLoadingLink = driver.findElement(By.linkText("Dynamic Loading"));
        dynamicLoadingLink.click();

        WebElement elementIsPageHidden = driver.findElement(By.xpath("(//a)[2]"));
        elementIsPageHidden.click();

        WebElement startButton = driver.findElement(By.xpath("//div[@id='start']/button"));
        startButton.click();

        By helloWorldText = By.xpath("//div[@id='finish']/h4");

        //Implicit Wait
//        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
//        Assert.assertTrue(driver.findElement(helloWorldText).isDisplayed());

        //Explicit Wait
//
//        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(60));
//        wait.until(ExpectedConditions.visibilityOf(driver.findElement(helloWorldText)));
//        Assert.assertTrue(driver.findElement(helloWorldText).isDisplayed());

        //Fluent Wait
//        FluentWait wait = new FluentWait(driver)
//                .withTimeout(Duration.ofSeconds(50))
//                .pollingEvery(Duration.ofSeconds(10))
//                .ignoring(NoSuchElementException.class);
//
//        wait.until(ExpectedConditions.visibilityOf(
//                driver.findElement(helloWorldText)));
//
//        Assert.assertTrue(driver.findElement(helloWorldText).isDisplayed());





    }

    @Test
    public void javaScriptExecutor() throws InterruptedException {
        WebElement largeAndDeepDomLink = driver.findElement(By.linkText("Large & Deep DOM"));
        largeAndDeepDomLink.click();

         WebElement tableElement = driver.findElement(By.id("large-table"));
         JavascriptExecutor js = (JavascriptExecutor) driver;
         String script = "arguments[0].scrollIntoView();";
         //Scroll to Specific Element
         js.executeScript(script,tableElement);
        //Scroll down by direction
        // js.executeScript("window.scrollBy(0,600)");
        Thread.sleep(3000);
    }

    @Test
    public void uploadFile()
    {
        WebElement uploadFileLink = driver.findElement(By.linkText("File Upload"));
        uploadFileLink.click();

        WebElement chooseFileButton = driver.findElement(By.id("file-upload"));
        WebElement uploadButton = driver.findElement(By.id("file-submit"));
        String absolutePathOfFile = "D:/ITGate/TestAutomation/TestFile.docx";
        chooseFileButton.sendKeys(absolutePathOfFile);
        uploadButton.click();
    }

    //To get screenshot after every test
    @AfterTest
    public void takeScreenshot() throws IOException {

        var camera = (TakesScreenshot)driver;
        File screenshot = camera.getScreenshotAs(OutputType.FILE);
        Files.move(screenshot,new File("resources/screenshots/test.png"));
        System.out.println("Screenshot taken: " + screenshot.getAbsolutePath());
    }



    @AfterTest
    public void tearDown()
    {
        driver.quit();
    }









}
