package APIsCall;

import io.restassured.http.ContentType;
import json.model.user.*;

public class UsersService extends GlobalAPICalls{


public User getUserDataByUsername ( String userName) {
    return requestCall(usersBasePath).
            queryParam("username", userName).
            get().then().contentType(ContentType.JSON).extract().as(User[].class)[0];
}

}

