package APIsCall;

import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.util.List;

import static io.restassured.RestAssured.*;
import static io.restassured.RestAssured.given;

public class PostsService extends GlobalAPICalls {

    public Response getPostsByUserId( String variable, int Id){
        return requestCall(postsBasePath).queryParam(variable,Id).get();
    }

    public List<Integer> getPostsIdsByUserId(String variable, int Id){
        return getPostsByUserId(variable, Id).then().extract().path("id");
    }
}
