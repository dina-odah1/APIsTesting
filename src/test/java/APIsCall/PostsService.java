package APIsCall;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import json.model.post.*;
import io.qameta.allure.Step;
import org.junit.jupiter.api.Tag;
import org.testng.annotations.Test;
import utils.configuration.ConfigLoader;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static io.restassured.RestAssured.given;
import static io.restassured.http.Method.PUT;
import static org.apache.http.client.methods.RequestBuilder.post;

public class PostsService extends GlobalAPICalls {


    public Response getPostsByUserId(String variable, Integer Id) {
        return requestCall(ConfigLoader.getInstance().getPostsBasePath()).queryParam(variable,Id).get();
    }

    public Response getPostById(Integer Id) {
        return requestCall(ConfigLoader.getInstance().getPostsBasePath()).get(Id.toString());

    }

    @Step ("getting posts related to certain ID that belongs to a particular username")
    public List<Integer> getPostsIdsByUserId(String variable, Integer Id) {
        return getPostsByUserId(variable, Id).then().extract().path("id");
    }

//    @Step ("getting posts related to certain ID that belongs to a particular username")
//    public List<Integer> getPostsIdsByUserId(String variable, Integer Id) {
//        Post[] posts = getPostsByUserId(variable, Id);
//        return   Arrays.asList(posts).stream()
//                .map(Post::getId)
//                .collect(Collectors.toList());
//    }

}
