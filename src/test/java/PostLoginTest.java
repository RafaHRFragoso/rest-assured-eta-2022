import io.restassured.response.Response;
import models.User;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static requests.LoginEndpoits.postLoginRequest;
import static requests.UserEndpoints.deleteUserRequest;
import static requests.UserEndpoints.postUserRequest;
import static org.hamcrest.Matchers.*;

public class PostLoginTest extends TestBase{

    private User validUser;
    private User invalidUser;

    @BeforeClass
    public void generateTestData(){
        validUser = new User("Rafa", "rafa@email.com", "123abc", "true");
        postUserRequest(SPEC, validUser);
        invalidUser = new User("Rafa", "rafa@email.com", "asdas", "true");
    }

    @Test
    public void shouldReturnSuccessMessageAuthTokenAndStatus200(){
        Response loginSuccessResponse = postLoginRequest(SPEC, validUser);
        loginSuccessResponse.
                then().
                assertThat().
                statusCode(200).
                body("message", equalTo(Constants.MESSAGE_SUCCESS_LOGIN)).
                body("authorization", notNullValue());

    }

    @Test
    public void shouldReturnFailureMessageAndStatus401(){

        Response loginFailureResponse = postLoginRequest(SPEC, invalidUser);
        loginFailureResponse.
                then().
                assertThat().
                statusCode(401).
                body("message", equalTo(Constants.MESSAGE_FAILED_LOGIN)).
                body("authorization", nullValue());
    }

    @AfterClass
    public void removeTestData(){
        deleteUserRequest(SPEC, validUser);
    }
}