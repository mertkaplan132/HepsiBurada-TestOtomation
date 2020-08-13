package com.testinium.hb.test.base;

import com.thoughtworks.gauge.AfterScenario;
import com.thoughtworks.gauge.BeforeScenario;
import org.junit.After;
import org.junit.Before;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.concurrent.TimeUnit;

public class BaseTest {

    protected static WebDriver driver;
    protected static WebDriverWait webDriverWait;
    protected String baseUrl = "https://www.hepsiburada.com/";
    DesiredCapabilities capabilities = new DesiredCapabilities();
    ChromeOptions options = new ChromeOptions();
    public static String Path = "drivers/chromedriver";



    @BeforeScenario
    public void setUp() {
        if (Platform.getCurrent().toString().contains("WIN")) {
            Path = "drivers/chromedriver.exe";
        }
        System.setProperty("webdriver.chrome.driver", Path);
        options.setExperimentalOption("w3c", false);
        options.addArguments("--disable-notifications");
        options.addArguments("disable-popup-blocking");
        capabilities.setCapability(ChromeOptions.CAPABILITY, options);
        driver = new ChromeDriver(capabilities);
        driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
        webDriverWait = new WebDriverWait(driver, 45, 150);
        driver.manage().window().maximize();
        driver.get(baseUrl);

    }

    @AfterScenario
    public void close() {
        driver.quit();
    }
}
