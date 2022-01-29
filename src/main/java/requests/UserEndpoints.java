package requests;
import com.google.gson.Gson;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import models.User;

import static io.restassured.RestAssured.given;

public class UserEndpoints extends RequestBase{

    public static Response getUserRequest(RequestSpecification spec) {
        Response getUsersResponse =
                given().
                        spec(spec).
                when().
                        get("/usuarios");
        return getUsersResponse;
    }

    public static Response postUserRequest(RequestSpecification spec, User user) {
        Gson gson = new Gson();
        String userJsonRepresentation = gson.toJson(user);

        Response postUsersResponse =
                given().
                        spec(spec).
                        header("Content-Type", "application/json").
                        and().
                        body(userJsonRepresentation).
                when().
                        post("/usuarios");
        user.setUserId(getValueFromResponse(postUsersResponse, "_id"));
        return postUsersResponse;
    }

    public static Response deleteUserRequest(RequestSpecification spec, User user) {

        Response deleteUsersResponse =
                given().
                        spec(spec).
                        pathParam("_id", user._id).
                when().
                        delete("/usuarios/{_id}");

        return deleteUsersResponse;
    }
}
