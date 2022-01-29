package requests;

import com.google.gson.Gson;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import models.Product;
import models.User;

import static io.restassured.RestAssured.given;

public class ProductEndpoints extends RequestBase{

    public static Response getProductsRequest(RequestSpecification spec) {
        Response getProductsResponse =
                given().
                        spec(spec).
                when().
                        get("/produtos");
        return getProductsResponse;
    }

    public static Response getProductByIdRequest(RequestSpecification spec, String invalid_prod_id) {
        Response getProductsResponse =
                given().
                        spec(spec).
                        pathParam("_id", invalid_prod_id).
                        when().
                        get("/produtos/{_id}");

        return getProductsResponse;
    }

    public static Response postProductsRequest(RequestSpecification spec, Product product, User user) {
        Gson gson = new Gson();
        String productJsonRepresentation = gson.toJson(product);
        Response postProductsResponse =
                given().
                        spec(spec).
                        header("Content-Type", "application/json").
                        header("Authorization", user.getUserToken()).
                        and().
                            body(productJsonRepresentation).
                        log().all().
                when().
                        post("/produtos");
        product.setProdId(getValueFromResponse(postProductsResponse, "_id"));
        return postProductsResponse;
    }

    public static Response deleteProductsRequest(RequestSpecification spec, Product product, User user) {

        Response deleteProductsResponse =
                given().
                        spec(spec).
                        pathParam("_id", product._id).
                        header("Authorization", user.getUserToken()).
                        when().
                        delete("/produtos/{_id}");

        return deleteProductsResponse;
    }
}
