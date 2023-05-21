package herokuapp.adoptpuppy.pages;

import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class PuppiesPage extends BasePageObject{

    private By adoptButton = By.xpath("//input[@value='Adopt Me!']");
    private By adopAPuppyPage = By.linkText("Adopt a Puppy");

    public PuppiesPage(WebDriver driver, Logger log) {
        super(driver, log);
    }

    /** Click Adopt button */
    public CartPage adoptDog() {
        log.info("Adopting a Puppy!");
        click(adoptButton);
        return new CartPage(driver, log);
    }

    /** Going back to adoption page */
    public MainPage goBackToAddoptionPage() {
        log.info("Going back in order to adopt another Puppy!");
        click(adopAPuppyPage);
        return new MainPage(driver, log);
    }


}
