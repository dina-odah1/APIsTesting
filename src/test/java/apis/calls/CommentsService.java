package apis.calls;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import json.model.comment.Comment;
import utils.configuration.ConfigLoader;
import java.util.List;
import java.util.regex.Pattern;

public class CommentsService extends GlobalAPICalls {

    private final String commentsBasePath = ConfigLoader.getInstance().getCommentsBasePath();
    private final Integer getSuccessCode = ConfigLoader.getInstance().getSuccessCode();

    private Response getCommentsResponse(int postId) {
        Response response = requestCall(commentsBasePath).
                queryParam("postId", postId).get();
        verifyStatusCode(response, getSuccessCode);
        return response;
    }

    // Not reused now but will be reused in the future.
    public Comment[] getCommentsByPostId (int postId) {
        return getCommentsResponse(postId).then().extract().as(Comment[].class);
    }

    @Step
    public List<Integer> getCommentsIdByPostId (int postId) {
        return getCommentsResponse(postId).then().extract().path("id");
    }

    @Step
    public List<String> getEmailsByPostId (int postId) {
        return getCommentsResponse(postId).then().extract().path("email");
    }

    @Step
    public boolean emailValidation (String email) {
        String regExpression = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
        return Pattern.compile(regExpression)
                .matcher(email)
                .matches();
    }

}

