package APIsCall;

import io.restassured.http.ContentType;
import json.model.post.*;
import io.qameta.allure.Step;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static io.restassured.RestAssured.given;

public class PostsService extends GlobalAPICalls {

    public Post[] getPostsByUserId( String variable, Integer Id) {
        return requestCall(postsBasePath).queryParam(variable,Id).get()
                .then().contentType(ContentType.JSON).extract().as(Post[].class);
    }

    @Step ("getting posts related to certain ID that belongs to a particular username")
    public List<Integer> getPostsIdsByUserId(String variable, Integer Id) {
        Post[] posts = getPostsByUserId(variable, Id);
        return   Arrays.asList(posts).stream()
                .map(Post::getId)
                .collect(Collectors.toList());
    }
}
