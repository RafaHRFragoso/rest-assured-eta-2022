import io.restassured.response.Response;
import models.Product;
import models.User;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static requests.LoginEndpoits.postLoginRequest;
import static requests.ProductEndpoints.*;
import static requests.UserEndpoints.*;
import models.User;
import org.testng.annotations.Test;

public class PostProdutosTest extends TestBase{

    private Product validProd1;
    private Product validProd2;
    private Product validProd3;
    private User validUser;
    private User simpleUser;

    @BeforeClass
    public void generateTestData(){
        validUser = new User("Santiago", "s.fragoso1@gmail.com", "San12345", "true");
        postUserRequest(SPEC, validUser);
        postLoginRequest(SPEC, validUser);
        simpleUser = new User("Simple User", "simple_user@gmail.com", "San12345", "false");
        postUserRequest(SPEC, simpleUser);
        postLoginRequest(SPEC, simpleUser);
        validProd1 = new Product("Canela de Velho2", "10", "Pomada pra canela de velho", "100");
        validProd2 = new Product("Remedio pra dor", "10", "Remedio pra dor", "100");
        validProd3 = new Product("Canela de Velho2", "12", "Outro remédio", "50");

    }

    @Test
    public void shouldReturnAllProductsAndStatusCode200() {
        Response postProductsResponse = postProductsRequest(SPEC, validProd1, validUser);
        postProductsResponse.
                then().
                assertThat().
                statusCode(201).
                body("message", equalTo(Constants.MESSAGE_SUCCESS_POST_PRODUTOS)).
                body("_id", notNullValue());
    }

    @Test
    public void shouldReturnedMessageExclusiveRouteForAdminsAndStatusCode403() {
        Response postProductsResponse = postProductsRequest(SPEC, validProd2, simpleUser);
        postProductsResponse.
                then().
                assertThat().
                statusCode(403).
                body("message", equalTo(Constants.MESSAGE_EXCLUSIVE_ROUTE_FOR_ADMINS));
    }

    @Test
    public void shouldReturnedMessageExistingProductNameAndStatusCode400() {
        Response postProductsResponse = postProductsRequest(SPEC, validProd3, validUser);
        postProductsResponse.
                then().
                assertThat().
                statusCode(400).
                body("message", equalTo(Constants.MESSAGE_EXISTING_PRODUCT_NAME));
    }

    @AfterClass
    public void removeTestData() {
        deleteProductsRequest(SPEC, validProd1, validUser);
        deleteUserRequest(SPEC, validUser);
        deleteUserRequest(SPEC, simpleUser);
    }
}
