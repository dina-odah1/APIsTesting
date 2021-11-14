package apis.calls;

import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import json.model.user.*;
import utils.configuration.ConfigLoader;
import static org.hamcrest.Matchers.*;

public class UsersService extends GlobalAPICalls {
    private final String usersBasePath = ConfigLoader.getInstance().getUsersBasePath();
    private final Integer successStatusCode = ConfigLoader.getInstance().getSuccessCode();

    @Step
    public Response getUserDataByUsername(String userName) {
        Response response = requestCall(usersBasePath).queryParam("username", userName).get();
        verifyStatusCode(response, successStatusCode);
        response.then().body("username", not(empty()));
        return response;
    }

    public User getUserObjectByUsername(String userName) {
        return getUserDataByUsername(userName).
                then().
                contentType(ContentType.JSON).
                extract().
                as(User[].class)[0];
    }

}

