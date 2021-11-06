package APIsCall;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.*;
import static io.restassured.RestAssured.given;

public class GlobalAPICalls {

    public String usersBasePath = "/users";
    public String postsBasePath = "/posts";
    public String commentsBasePath = "/comments";
    public int usersStatusCode = 200;
    public int postsStatusCode = 200;
    public int commentsStatusCode = 200;

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
