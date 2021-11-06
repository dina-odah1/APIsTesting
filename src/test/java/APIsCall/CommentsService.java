package APIsCall;

import io.restassured.response.Response;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CommentsService extends GlobalAPICalls {

    private final String regExpression = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";

public ArrayList<Integer> getCommentsIdByPostId (int Id)
{
    ArrayList<Integer> commentsIds = requestCall(commentsBasePath).queryParam("postId", Id).get().then().extract().path("id");
    return  commentsIds;
}

public ArrayList<String> getEmailsByPostId (int postId)
{
    ArrayList<String> commentEmail = requestCall(commentsBasePath).queryParam("postId", postId).get().then().extract().path("email");
    return commentEmail;
}

public boolean emailValidation (String email)
{
    return Pattern.compile(regExpression)
            .matcher(email)
            .matches();

}

}

