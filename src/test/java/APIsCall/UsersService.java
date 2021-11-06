package APIsCall;

import io.restassured.response.Response;

import java.util.List;

import static io.restassured.RestAssured.basePath;

public class UsersService extends GlobalAPICalls{


public Response getUserDataByUsername ( List<String> userName) {
    return requestCall(usersBasePath).queryParam("username", userName).get();
}

}

