package APIsCall;

import io.restassured.http.ContentType;
import json.model.comment.Comment;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class CommentsService extends GlobalAPICalls {
    public Comment[] getCommentsByPostId (int postId) {
        return requestCall(commentsBasePath).queryParam("postId", postId).get()
                .then().contentType(ContentType.JSON).extract().as(Comment[].class);
    }


    public List<Integer> getCommentsIdByPostId (int postId) {
        Comment[] comments = getCommentsByPostId(postId);
        return   Arrays.asList(comments).stream()
                .map(Comment::getId)
                .collect(Collectors.toList());
    }

public List<String> getEmailsByPostId (int postId) {
    Comment[] comments = getCommentsByPostId(postId);
    return   Arrays.asList(comments).stream()
            .map(Comment::getEmail)
            .collect(Collectors.toList());
}

public boolean emailValidation (String email) {
    String regExpression = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
    return Pattern.compile(regExpression)
            .matcher(email)
            .matches();

}

}

