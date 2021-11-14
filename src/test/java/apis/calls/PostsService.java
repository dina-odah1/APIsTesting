package apis.calls;

import io.restassured.response.Response;
import json.model.post.*;
import io.qameta.allure.Step;
import utils.configuration.ConfigLoader;
import java.util.List;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class PostsService extends GlobalAPICalls {
    private final String postBasePath = ConfigLoader.getInstance().getPostsBasePath();
    private final Integer getSuccessCode = ConfigLoader.getInstance().getSuccessCode();

    @Step
    public Response getPostsByUserId(String variable, Integer Id) {
        Response response = requestCall(postBasePath).queryParam(variable,Id).get();
        verifyStatusCode(response, getSuccessCode);
        return response;
    }

    public Response getPostById(Integer Id) {
        Response response = requestCall(postBasePath).get(Id.toString());
        verifyStatusCode(response, getSuccessCode);
        return response;
    }

    @Step ("getting posts related to certain ID that belongs to a particular username")
    public List<Integer> getPostsIdsByUserId(String variable, Integer Id) {
        return getPostsByUserId(variable, Id).then().extract().path("id");
    }

    @Step ("Checking the response parameters of post submitted matches the values posted")
    public void assertPostEqual(Post responsePost, Post requestPost) {
        assertThat(responsePost.getUserId(), equalTo(requestPost.getUserId()));
        assertThat(responsePost.getBody(), equalTo(requestPost.getBody()));
        assertThat(responsePost.getTitle(), equalTo(requestPost.getTitle()));
    }

    @Step ("Build a new Post object")
    public Post postBuilder (Integer Id) {
        String postBody = ConfigLoader.getInstance().getPostBody();
        String postTitle = ConfigLoader.getInstance().getPostTitle();
        return new Post().setUserId(Id).setBody(postBody).setTitle(postTitle);
    }
}
