import io.restassured.builder.RequestSpecBuilder;
import io.restassured.response.Response;
import io.restassured.specification.FilterableRequestSpecification;
import io.restassured.specification.RequestSpecification;
import models.User;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.StreamHandler;
import static io.restassured.RestAssured.*;
import static requests.UserEndpoints.*;
import static org.hamcrest.Matchers.*;

public class GetUsersTest extends TestBase{

    List<User> listOfRegisteredUsers = new ArrayList<>();
    private User validUser1;
    private User validUser2;
    private User validUser3;
    private User invalidUser1;

    @BeforeClass
    public void generateTestData(){
        validUser1 = new User("Santiago", "s.fragoso@gmail.com", "San12345", "false");
        postUserRequest(SPEC, validUser1);
        listOfRegisteredUsers.add(validUser1);
        validUser2 = new User("Rafael", "r.fragoso@gmail.com", "San12345", "false");
        postUserRequest(SPEC, validUser2);
        listOfRegisteredUsers.add(validUser2);
        validUser3 = new User("Maria", "mariasilva@email.com", "lalala123", "false");
        postUserRequest(SPEC, validUser3);
        listOfRegisteredUsers.add(validUser3);
        invalidUser1 = new User("Carlos", "carlos@email.com", "minhasenha", "0");
    }

    @DataProvider(name = "userQueryData")
    public Object[][] createQueryData() {
        return new Object[][] {
                {"nome", validUser1.nome},
                {"email", validUser2.email}
        };
    }

    @Test
    public void shouldReturnedAllUsersAndStatusCode200(){
        Response getUserResponse = getUserRequest(SPEC);
        getUserResponse.
                then().
                    assertThat().
                    statusCode(200).
                    body("quantidade", equalTo(listOfRegisteredUsers.size())).
                    body("quantidade", instanceOf(Integer.class)).
                    body("usuarios", instanceOf(List.class));
    }

    @Test(dataProvider = "userQueryData")
    public void shouldReturnUserForQueryAndStatusCode200(String query, String queryValue) {
        SPEC.queryParam(query, queryValue);
        Response getUsersResponse = getUserRequest(SPEC);
        getUsersResponse.
                then().
                log().all();

        FilterableRequestSpecification filterableRequestSpecification = (FilterableRequestSpecification) SPEC;
        filterableRequestSpecification.removeQueryParam(query);
    }

    @AfterClass
    public void removeTestData() {
        deleteUserRequest(SPEC, validUser1);
        deleteUserRequest(SPEC, validUser2);
        deleteUserRequest(SPEC, validUser3);
    }
}
