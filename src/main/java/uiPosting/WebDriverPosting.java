package uiPosting;

import com.github.javafaker.Faker;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class WebDriverPosting {
    private static final String URL = "https://www.facebook.com";
    private  String email = "automation_xgfnylx_user@tfbnw.net";
    private  String password  = "qwerty123!";
    private WebDriver driver;

    @FindBy(id = "email")
    private WebElement emailInput;

    @FindBy(id = "pass")
    private WebElement passInput;

    @FindBy(className = "uiButtonConfirm")
    private WebElement loginButton;

    @FindBy(css = "div[aria-label=\"What's on your mind?\"]")
    private WebElement postArea;

    @FindBy(css = "div[data-click] a")
    private WebElement user;

    @FindBy(css = " button[data-testid=\"react-composer-post-button\"]")
    private WebElement postButton;



    public WebDriverPosting(){
        System.setProperty("webdriver.chrome.driver", "./src/main/resources/chromedriver.exe");
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.addArguments(("--disable-notifications"));
        driver = new ChromeDriver(chromeOptions);
        PageFactory.initElements(driver, this);
        driver.manage().window().maximize();
    }

    public void createPost(){
        String message = new Faker().chuckNorris().fact() + new Faker().hobbit().quote();
        driver.get(URL);
        emailInput.sendKeys(email);
        passInput.sendKeys(password);
        loginButton.click();
        user.click();
        WebDriverWait webDriverWait = new WebDriverWait(driver, 10);
        webDriverWait.until(ExpectedConditions.visibilityOf(postArea));
        postArea.click();
        postArea.sendKeys(message);
        webDriverWait.until(ExpectedConditions.visibilityOf(postButton));
        postButton.click();
        try {
           Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        driver.quit();


    }
}
