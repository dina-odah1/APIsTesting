package APIsCall;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.*;
import static io.restassured.RestAssured.given;

public class GlobalAPICalls {

    String usersBasePath = "/users";
    String postsBasePath = "/posts";
    String commentsBasePath = "/comments";

    public RequestSpecification requestCall(String apiBasePath)
    {
        baseURI = "https://jsonplaceholder.typicode.com";
        basePath = apiBasePath;
        return given();
    }
    public Response getAPIResponse(String apiBaseBath) {
        return requestCall(apiBaseBath).get();
    }


}
