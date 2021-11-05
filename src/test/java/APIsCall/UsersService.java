package APIsCall;

import io.restassured.response.Response;

import static io.restassured.RestAssured.basePath;

public class UsersService extends GlobalAPICalls{

public Response getUserDataByUsername (String userName)
{
    return requestCall(usersBasePath).queryParam("username", userName).get();
}


}

