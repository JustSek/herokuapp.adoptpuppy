package herokuapp.adoptpuppy.pages;

import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

public class OrderPage extends BasePageObject {

    private By nameLocator = By.id("order_name");
    private By addressLocator = By.id("order_address");
    private By emailLocator = By.id("order_email");
    private By payType = By.id("order_pay_type");
    private By orderButton = By.xpath("//input[@name='commit']");



    public OrderPage(WebDriver driver, Logger log) {
        super(driver, log);
    }

    /** Fulfilling Order Details */
    public void provideOrderDetails(String name, String address, String email) {
        log.info("Executing paymnet using name: " + name + ", address: " + address +" and email: " + email);
        type(name, nameLocator);
        type(address, addressLocator);
        type(email, emailLocator);
    }

    /** This method selects given option for payment method from dropdown.
     * Use value: Check, Credit card, Purchase order */
    public String selectPaymentMethod(String paymentMethod) {
        log.info("Selecting " + paymentMethod + " as payment method");
        WebElement dropdownElement = find(payType);
        Select select = new Select(dropdownElement);
        select.selectByValue(paymentMethod);
        return select.getFirstSelectedOption().getText();
    }

    /** Clicking Place order button */
    public MainPage placeOrder() {
        log.info("Finalizing an adoption");
        click(orderButton);
        return new MainPage(driver, log);
    }

}
