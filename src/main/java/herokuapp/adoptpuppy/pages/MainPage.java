package herokuapp.adoptpuppy.pages;

import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class MainPage extends BasePageObject {

    private String pageUrl = "https://spartantest-puppies.herokuapp.com/";
    private final By allPuppiesLabelsLocator = By.xpath("//div[@class='name']/h3");
    private final By viewDetailsLocator = By.xpath("./../../div[@class='view']/form");
    private final By nextPageLocator = By.xpath("//a[@class='next_page']");
    private By adoptionConfirmationMessage = By.id("notice");
    public String expectedAdoptionConfirmationMessage = "Thank you for adopting a puppy!";


    public MainPage(WebDriver driver, Logger log) {
        super(driver, log);
    }

    /** Open WelcomePage with it's url*/
    public void openPage(){
        log.info("Opening page: " + pageUrl);
        openUrl(pageUrl);
        log.info("Page opened");
    }

    /** Find Puppy to adoption based on Name and return locator to correct ViewDetails Button */
    private WebElement findPuppyLocator(String puppyName){
        while (true){
            List<WebElement> allPuppiesInPage = driver.findElements(allPuppiesLabelsLocator);
            for (WebElement puppyNameLabel: allPuppiesInPage) {
                String puppyLabel = puppyNameLabel.getText();
                if (puppyLabel.equals(puppyName)){
                    return puppyNameLabel.findElement(viewDetailsLocator);
                }
            }
            try {
                WebElement nextPage = driver.findElement(nextPageLocator);
                nextPage.click();
            } catch (NoSuchElementException e){
                break;
            }
        }
        return null;
    }

    /** Adding Puppy to adoption using findPuppyLocator method */
    public PuppiesPage clickViewDetails(String puppyName) {
        log.info("Clicking View Details on " + puppyName);
        WebElement viewDetails = findPuppyLocator(puppyName);
        if (viewDetails != null){
            viewDetails.click();
        } else throw new IllegalArgumentException(puppyName + " not found!");
        return new PuppiesPage(driver, log);
    }

    /** Returning adoption confirmation after placing an order */
    public String adoptionConfirmation() {
       return find(adoptionConfirmationMessage).getText();
    }

}
