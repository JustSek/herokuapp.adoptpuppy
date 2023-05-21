package herokuapp.adoptpuppy;

import herokuapp.adoptpuppy.base.BaseTest;
import herokuapp.adoptpuppy.pages.CartPage;
import herokuapp.adoptpuppy.pages.MainPage;
import org.testng.Assert;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import herokuapp.adoptpuppy.pages.OrderPage;
import herokuapp.adoptpuppy.pages.PuppiesPage;

public class TwoDogsWithTheSameAddition extends BaseTest {
    @Parameters({ "username", "password", "address" })
    @Test
    public void adopt2RandomDogs(String username, String password, String address)  {
        // Adopt first dog
        MainPage mainPage = new MainPage(driver, log);
        mainPage.openPage();
        PuppiesPage puppyPage = mainPage.clickViewDetails("Maggie Mae");
        CartPage cart = puppyPage.adoptDog();

        //Adopt second dog
        puppyPage.goBackToAddoptionPage();
        mainPage.clickViewDetails("Twinkie");
        puppyPage.adoptDog();

        //add a Collar & Leash to each
        cart.addAdditionsToAllDogs(cart.collarLeash);
        Assert.assertTrue(cart.areAllCollarLeashesSelected(), "Collar & Leash not selected");
        OrderPage orderPage = cart.completeAdoption();

        //pay with Credit Card
        orderPage.provideOrderDetails(username, password, address);
        String selectedPaymentMethod = orderPage.selectPaymentMethod("Credit card");
        Assert.assertEquals(selectedPaymentMethod, "Credit card");
        orderPage.placeOrder();
        String confirmationAdoptionMessage = mainPage.adoptionConfirmation();
        Assert.assertEquals(confirmationAdoptionMessage, mainPage.expectedAdoptionConfirmationMessage);
    }
}
