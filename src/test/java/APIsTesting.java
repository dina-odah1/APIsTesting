import APIsCall.CommentsService;
import APIsCall.GlobalAPICalls;
import APIsCall.PostsService;
import APIsCall.UsersService;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import jdk.jfr.Description;
import org.testng.annotations.Test;

import java.lang.annotation.Target;
import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.*;
import static io.restassured.http.ContentType.JSON;
import static io.restassured.path.json.JsonPath.from;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItem;

public class APIsTesting {

    TestData newTest = new TestData();
    GlobalAPICalls newCalls = new GlobalAPICalls();
    CommentsService newCommentsServiceCall = new CommentsService();
    UsersService newUsersServicesCall = new UsersService();
    PostsService newPostsServiceCall = new PostsService();

    @Test

    @Description("Query Against the Users Service with certain given name and verify that the returned username matches given username")

    public void verifySearchByUsername()
    {
       newUsersServicesCall.getUserDataByUsername(newTest.username).then().assertThat().body("username", hasItem(newTest.username));
    }

    @Test
    public void searchByUserName(){
            baseURI = newTest.host;
            basePath = newTest.usersBasePath;
            RequestSpecification request = given();
            Response response = get();
            response.getBody();
//            System.out.println("Response: " +response.asString());
//            System.out.println("Status code : "+response.getStatusCode());

//            String userName = response.body("userName");
//            Assert.assertEquals(, username);
//            Response user = request.queryParam("username",newTest.username)
//                    .get();

            Response user = newCalls.requestCall(basePath).queryParam("username",newTest.username)
                    .get();
            System.out.println("user :" +user.asString());
            JsonPath jsonPath = new JsonPath(user.asString());
            int userid1 = jsonPath.get("[0].id");
            System.out.println("userid1 :" +userid1);
            newTest.userID=userid1;
        }

        public Response getPosts(){
            baseURI = newTest.host;
            basePath = newTest.postsBasePath;
            String response = get().asString();
            RequestSpecification request = given();
            return request.queryParam("userId",newTest.userID)
                    .get();
        }

        @Test
        public void searchPostsByUserID(){
            Response posts = getPosts();
            System.out.println("response :" +posts.asString());
            newTest.postIDs = posts.then().extract().path("id");

//            public ArrayList<Integer> getAllPostsIdsOfAUser(int userId) {
//                return getWithQueryParam(endpoint, "userId", userId).then().extract().path("id");
////            }

            System.out.println("PostsID :" +newTest.postIDs);
        }

    public Response getCommentsByPost(){
        baseURI = newTest.host;
        basePath = newTest.commentsBasePath;
        String response = get().asString();
        RequestSpecification request = given();
        return request.queryParam("postId",newTest.postID)
                .get();
    }

        @Test
         public void searchCommentsOnPost (){
            List<Integer> postIDs = newPostsServiceCall.getPostsIdsByUserId("userId", newTest.userID);
            System.out.println("User Post IDs :" + postIDs);
            String response = get().asString();
            RequestSpecification request = given();

                for (int i=0; i < postIDs.size(); i++ )
            {
                ArrayList <Integer> commentsID =  newCommentsServiceCall.getCommentsIdByPostId(postIDs.get(i));
                ArrayList <String> commentsEmail = newCommentsServiceCall.getEmailsByPostId(postIDs.get(i));

                System.out.println("commentId :" +commentsID);
                System.out.println("commentEmail :" +commentsEmail);
                for (int f=0; f< commentsEmail.size(); f++)
                {
                    boolean isEmailValid = newCommentsServiceCall.emailValidation(commentsEmail.get(f));
                    System.out.println("Email Is Valid :" +isEmailValid);
                    System.out.println("comment Email Is :" +commentsEmail.get(f));
                }

            }

        }

    @Test
    @Description ("Check users API status code")
    public void checkUsersStatusCode(){
             /* Given The users API that is supposed to return a list of users with their details
                When I hit the API request
                Then  I should be getting the desired response code defined for this API,
                e.g.  200 for success*/
            baseURI = newTest.host;
            basePath = newTest.usersBasePath;
            int statusCode = newTest.usersStatusCode;
    given ().
        when().
            get().
            then().
            statusCode(newTest.usersStatusCode);
    }
}
