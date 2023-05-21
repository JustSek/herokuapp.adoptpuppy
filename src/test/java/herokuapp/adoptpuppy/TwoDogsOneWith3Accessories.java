package herokuapp.adoptpuppy;

import herokuapp.adoptpuppy.base.BaseTest;
import herokuapp.adoptpuppy.pages.CartPage;
import herokuapp.adoptpuppy.pages.MainPage;
import org.testng.Assert;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import herokuapp.adoptpuppy.pages.OrderPage;
import herokuapp.adoptpuppy.pages.PuppiesPage;

public class TwoDogsOneWith3Accessories extends BaseTest {
    @Parameters({ "username", "password", "address" })
    @Test
    public void adopt2RandomDogsAndAdd3itemsToOne(String username, String password, String address)  {
        // Adopt first dog
        MainPage mainPage = new MainPage(driver, log);
        mainPage.openPage();
        PuppiesPage puppyPage = mainPage.clickViewDetails("Maggie Mae");
        CartPage cart = puppyPage.adoptDog();

        //Adopt second dog
        puppyPage.goBackToAddoptionPage();
        mainPage.clickViewDetails("Twinkie");
        puppyPage.adoptDog();

        //add a 3 Random Accessories to one of them [in this case to second dog]
        cart.addAdditiontoSelectedDog(1, cart.collarLeash);
        cart.addAdditiontoSelectedDog(1,cart.chewToy);
        cart.addAdditiontoSelectedDog(1, cart.vetVistit);
        Assert.assertTrue(cart.isAdditionSelected(1, cart.collarLeash));
        Assert.assertTrue(cart.isAdditionSelected(1, cart.chewToy));
        Assert.assertTrue(cart.isAdditionSelected(1, cart.vetVistit));
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
