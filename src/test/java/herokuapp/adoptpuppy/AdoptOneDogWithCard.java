package herokuapp.adoptpuppy;

import herokuapp.adoptpuppy.base.BaseTest;
import herokuapp.adoptpuppy.pages.CartPage;
import herokuapp.adoptpuppy.pages.MainPage;
import herokuapp.adoptpuppy.pages.OrderPage;
import herokuapp.adoptpuppy.pages.PuppiesPage;
import org.testng.Assert;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import spotOn.pages.*;

public class AdoptOneDogWithCard extends BaseTest {
    @Parameters({ "username", "password", "address" })
    @Test
    public void adoptSparky(String username, String password, String address)  {
        //Adopt Sparky,
        MainPage mainPage = new MainPage(driver, log);
        mainPage.openPage();
        PuppiesPage puppyPage = mainPage.clickViewDetails("Sparky");
        CartPage cart = puppyPage.adoptDog();

        // add a Collar & Leash,
        cart.addAdditions("collar&leash");
        Assert.assertTrue(cart.isCollarLeashSelected(), "Collar & Leash not selected");
        OrderPage orderPage = cart.completeAdoption();

        // pay with Credit Card
        orderPage.provideOrderDetails(username, password, address);
        String selectedPaymentMethod = orderPage.selectPaymentMethod("Credit card");
        Assert.assertEquals(selectedPaymentMethod, "Credit card");
        orderPage.placeOrder();
        String confirmationAdoptionMessage = mainPage.adoptionConfirmation();
        Assert.assertEquals(confirmationAdoptionMessage, mainPage.expectedAdoptionConfirmationMessage);
    }
}
