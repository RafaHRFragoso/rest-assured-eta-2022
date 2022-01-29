package requests;

import com.google.gson.Gson;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import models.User;

import static io.restassured.RestAssured.given;

public class LoginEndpoits extends RequestBase{

    public static Response postLoginRequest(RequestSpecification spec, User user) {

        Response postLoginResponse =
                given().
                        spec(spec).
                        header("Content-Type", "application/json").
                        and().
                        body(user.getUserCredentials()).
                when().
                        post("/login");

        user.setUserToken(getValueFromResponse(postLoginResponse, "authorization"));
        return postLoginResponse;
    }
}
