package APIsCall;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.util.List;

import static io.restassured.RestAssured.*;
import static io.restassured.RestAssured.given;

public class PostsService extends GlobalAPICalls {

    public Response getPostsByUserId( String variable, List<Integer> Id) {
        return requestCall(postsBasePath).queryParam(variable,Id).get();
    }

    @Step ("getting posts related to certain ID that belongs to a particular username")
    public List<Integer> getPostsIdsByUserId(String variable, List<Integer> Id) {
        return getPostsByUserId(variable, Id).then().extract().path("id");
    }


}
