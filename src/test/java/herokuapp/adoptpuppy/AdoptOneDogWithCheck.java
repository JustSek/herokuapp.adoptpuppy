package herokuapp.adoptpuppy;

import herokuapp.adoptpuppy.base.BaseTest;
import herokuapp.adoptpuppy.pages.CartPage;
import herokuapp.adoptpuppy.pages.MainPage;
import org.testng.Assert;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import herokuapp.adoptpuppy.pages.OrderPage;
import herokuapp.adoptpuppy.pages.PuppiesPage;

public class AdoptOneDogWithCheck extends BaseTest {

    @Parameters({"username", "password", "address"})
    @Test
    public void adoptBrook(String username, String password, String address) {
        // Adopt Brooke,
        MainPage mainPage = new MainPage(driver, log);
        mainPage.openPage();
        PuppiesPage puppyPage = mainPage.clickViewDetails("Brook");
        CartPage cart = puppyPage.adoptDog();

        // add a Chewy Toy and a Travel Carrier
        cart.addAdditions("toy");
        cart.addAdditions("carrier");
        Assert.assertTrue(cart.isChewToySelected(), "Chew toy not selected");
        Assert.assertTrue(cart.isCarrierSelected(), "Carrier not selected");
        OrderPage orderPage = cart.completeAdoption();

        // pay with Check
        orderPage.provideOrderDetails(username, password, address);
        String selectedPaymentMethod = orderPage.selectPaymentMethod("Check");
        Assert.assertEquals(selectedPaymentMethod, "Check");
        orderPage.placeOrder();
        String confiramtionAdoptionMessage = mainPage.adoptionConfirmation();

        Assert.assertEquals(confiramtionAdoptionMessage, mainPage.expectedAdoptionConfirmationMessage);
    }
}