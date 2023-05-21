package herokuapp.adoptpuppy.pages;

import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class CartPage extends BasePageObject {

    public By chewToy = By.id("toy");
    public By travelCarrier = By.id("carrier");
    public By collarLeash = By.id("collar");
    public By vetVistit = By.id("vet");
    private By completeAdoptionButton = By.xpath("//input[@value='Complete the Adoption']");

    public CartPage(WebDriver driver, Logger log) {
        super(driver, log);
    }

    /** Adding Additional Products/Services to Cart for one dog */
    public void addAdditions(String addition) {
        log.info("Adding " + addition + " to cart");
        switch(addition) {
            case "toy":
               click(chewToy);
               break;
            case "carrier":
                click(travelCarrier);
                break;
            case "collar&leash":
                click(collarLeash);
                break;
            case "vet":
                click(vetVistit);
                break;
        }
    }

    /** Adding the same Additional Products/Services to Cart for each dog */
    public void addAdditionsToAllDogs(By locator) {
        log.info("Adding " + locator + " to cart");
//        By toyLocator = null;
//        switch(addition) {
//            case "toy":
//                toyLocator = chewToy;
//                break;
//            case "carrier":
//                toyLocator = travelCarrier;
//                break;
//            case "collar&leash":
//                toyLocator = collarLeash;
//                break;
//            case "vet":
//                toyLocator = vetVistit;
//                break;
//        }
        selectAllCheckboxes(findAll(locator));
    }

    /** Adding selected Addition to a specyfic dog
     * for first dog on a list use id = 0, for second id = 1 .. */
    public void addAdditiontoSelectedDog(int id, By locator) {
       List<WebElement> elements = findAll(locator);
       WebElement toAdd = elements.get(id);
       toAdd.click();
    }

    /** Clicking Complete the Adoption */
    public OrderPage completeAdoption () {
        log.info("Completing the Adoption");
        click(completeAdoptionButton);
      return new OrderPage(driver, log);
    }

    /** Check if Chew Toy is checked */
    public boolean isChewToySelected() {
        return find(chewToy).isSelected();
    }

    /** Check if Travel Carrier is checked */
    public boolean isCarrierSelected() {
        return find(travelCarrier).isSelected();
    }

    /** Check if Collar & Leash  is checked */
    public boolean isCollarLeashSelected() {
        return find(collarLeash).isSelected();
    }

    /** Check if Collars & Leashs  are checked for all dogs*/
    public boolean areAllCollarLeashesSelected() {
        for (WebElement element : findAll(collarLeash)) {
            if (!element.isSelected()) return false;
        }
        return true;
    }

    /** Check if addition for specyfied dog is selected */
    public boolean isAdditionSelected(int id, By locator) {
        List<WebElement> addition = findAll(locator);
        WebElement selectedElement = addition.get(id);
        boolean isSelectedElementChecked = selectedElement.isSelected();
        return isSelectedElementChecked;




    }

}
