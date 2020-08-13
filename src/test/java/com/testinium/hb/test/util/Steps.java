package com.testinium.hb.test.util;

import com.thoughtworks.gauge.Step;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Steps extends Methods{

    public static String productName = "";
    public static String detailProductName = "";
    public static String productQuantity = "";
    public static String basketProductQuantity ="";
    public static String totalPrice = "";

    @Step("<key> li elemente tıkla")
    public void clickElement(String key) {
        WebElement element = findElement(key);
        clictToElement(element);
    }

    @Step("<key> li elementlerden birine rastgele tıkla")
    public void randomClick(String key){
        List<WebElement> elements = findElements(key);
        clictToElement(elements.get(randomInt(key)));
    }

    @Step("<key> li elemente <text> değerini yaz")
    public void sendKeysToElementTest(String key, String text) {
        WebElement element = findElement(key);
        element.clear();
        sendKeysToElement(element, text);
    }

    @Step("<second> saniye bekle")
    public void waitSecond(int second) {
        waitByMilliSeconds(second * 1000);
    }

    @Step("<key> li elementin değeri <text> değeri ile uyuşuyor mu kontrol et")
    public void getTextControlTest(String key, String text) {
        WebElement element = findElement(key);
        getTextControl(element, text);
    }

    @Step("<key> li elementin text değerini kaydet")
    public void detailNameSaveText(String key){
        WebElement element= findElement(key);
        detailProductName = element.getText();
        System.out.println(detailProductName);
    }

    @Step("<key> li elementlerden rastgele birinin text değerini kaydet ve elemente tıkla")
    public void SaveTextRandomClick(String key){
        List<WebElement> elements = findElements(key);
        WebElement element = elements.get(randomInt(key)).findElement(By.cssSelector("div[class='product-detail']"));
        productName = element.findElement(By.cssSelector("h3 span")).getText();
        clictToElement(element);
        System.out.println(productName);
    }

    @Step("Sayfadaki div değişikliği kontrol et sonuca göre element seç")
    public void changeControl(){
        if (driver.findElements(By.cssSelector("ul[class='ChildMenuItems-3m2LI']")).size()>0){
            randomClick("ul[class='ChildMenuItems-3m2LI']");
            randomClick("a[class*='ChildMenuItems-1Mjr0 item-']");
        }else {
            randomClick("a[class*='ChildMenuItems-1Mjr0 item-']");
        }
    }

    @Step("Sepete eklerkenki div değişikliği kontrol et sonuca göre işlem yap")
    public void basketChangeControl(){
        if (driver.findElements(By.xpath("//a[text()='Sepete git']")).size()>0){
            clickByXpath("//a[text()='Sepete git']");
        }else {
            clickElement("span[id='shoppingCart']");
        }
    }

    @Step("<key> li elementlerin rastgele birinin üstüne gel")
    public void randomMouseHover(String key){
        Actions action=new Actions(driver);
        List<WebElement> element =driver.findElements(By.cssSelector(key));
        action.moveToElement(element.get(randomInt(key))).build().perform();
    }

    @Step("<key> li elementin üzerine gel")
    public void mouseHover(String key){
        Actions action = new Actions(driver);
        WebElement element = driver.findElement(By.cssSelector(key));
        action.moveToElement(element).build().perform();
    }

    @Step("<key> li elementlerden <number>. olanına tıkla")
    public void optionClick(String key,Integer number){
        List<WebElement> elements = findElements(key);
        clictToElement(elements.get(number));
    }

    @Step("Ürünün açıklama metnini csv dosyasına yaz")
    public void productNameWriteCsv() throws IOException {
        FileWriter csvWriter = new FileWriter("ProductName.csv");
        csvWriter.append(productName);
        csvWriter.flush();
        csvWriter.close();
    }

    @Step("<key> li elementin toplam tutarını csv dosyasına yaz")
    public void totalPriceWriteCsv(String key) throws IOException {
        WebElement element= findElement(key);
        totalPrice=element.getText();
        FileWriter csvWriter = new FileWriter("totalPrice.csv");
        csvWriter.append(totalPrice);
        csvWriter.flush();
        csvWriter.close();
    }

    @Step("Ürünün texti ile detay sayfasındaki ürünün textini karşılaştır")
    public void compareName(){
        Assert.assertEquals("Textler uyuşmuyor", productName,detailProductName);
    }

    @Step("<key> li elementin attribute'ünü kaydet")
    public void saveAttribute(String key){
        WebElement element=findElement(key);
        productQuantity=element.getAttribute("value");
    }

    @Step("Detay sayfasındaki <key> li elementin attribute'ünü kaydet")
    public void cartQuantitySaveAttribute(String key){
        WebElement element=findElement(key);
        basketProductQuantity = element.getAttribute("value");
        Assert.assertEquals("Ürün miktarları uyuşmuyor",productQuantity,basketProductQuantity);
    }

    @Step("<xpath> li elemente xpath ile tıkla")
    public void clickByXpath(String xpath){
        WebElement element = new WebDriverWait(driver, 30).until(ExpectedConditions.elementToBeClickable(By.xpath(xpath)));
        scrollToElement(element);
        element.click();
    }

}
