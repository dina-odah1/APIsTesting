package APIsCall;

import io.restassured.response.Response;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CommentsService extends GlobalAPICalls {

    public ArrayList<Integer> getCommentsIdByPostId (int Id) {
        return requestCall(commentsBasePath).queryParam("postId", Id).get().then().extract().path("id");
}

public ArrayList<String> getEmailsByPostId (int postId) {
    return requestCall(commentsBasePath).queryParam("postId", postId).get().then().extract().path("email");
}

public boolean emailValidation (String email) {
    String regExpression = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
    return Pattern.compile(regExpression)
            .matcher(email)
            .matches();

}

}

