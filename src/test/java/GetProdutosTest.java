import io.restassured.builder.RequestSpecBuilder;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import models.Product;
import models.User;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static requests.LoginEndpoits.postLoginRequest;
import static requests.ProductEndpoints.*;
import static requests.UserEndpoints.deleteUserRequest;
import static requests.UserEndpoints.postUserRequest;

public class GetProdutosTest extends TestBase{

    List<Product> listOfRegisteredProds = new ArrayList<>();
    private User validUser;
    private Product validProd1;
    private Product validProd2;
    private Product validProd3;
    private String invalid_prod_id = "A897v9FDFJK7bKJ97";

    @BeforeClass
    public void generateTestData(){
        validUser = new User("Santiago", "s.fragoso@gmail.com", "San12345", "true");
        postUserRequest(SPEC, validUser);
        postLoginRequest(SPEC, validUser);
        validProd1 = new Product("Canela de Velho", "10", "Pomada pra canela de velho", "100");
        postProductsRequest(SPEC, validProd1, validUser);
        listOfRegisteredProds.add(validProd1);
        validProd2 = new Product("Lenço Umedecido", "10", "Lenço Umedecido", "100");
        postProductsRequest(SPEC, validProd2, validUser);
        listOfRegisteredProds.add(validProd2);
        validProd3 = new Product("Fralda XG", "10", "Fralda XG", "100");
        postProductsRequest(SPEC, validProd3, validUser);
        listOfRegisteredProds.add(validProd3);
}

    @Test
    public void shouldReturnedAllProductsAndStatusCode200(){
        Response getProductsResponse = getProductsRequest(SPEC);
        getProductsResponse.
                then().
                assertThat().
                statusCode(200).
                body("quantidade", equalTo(listOfRegisteredProds.size() + 2)).
                body("quantidade", instanceOf(Integer.class)).
                body("produtos", instanceOf(List.class));
    }

    @Test
    public void shouldReturnedMessageProductNotFoundAndStatusCode400(){
        Response getProductByIdRequest = getProductByIdRequest(SPEC, invalid_prod_id);
        getProductByIdRequest.
                then().
                assertThat().
                statusCode(400).
                body("message", equalTo(Constants.MESSAGE_PRODUCT_NOT_FOUND)).
                body("nome", nullValue());
    }

    @AfterClass
    public void removeTestData() {
        deleteProductsRequest(SPEC, validProd1, validUser);
        deleteProductsRequest(SPEC, validProd2, validUser);
        deleteProductsRequest(SPEC, validProd3, validUser);
        deleteUserRequest(SPEC, validUser);
    }
}
