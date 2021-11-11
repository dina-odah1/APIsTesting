import APIsCall.CommentsService;
import APIsCall.GlobalAPICalls;
import APIsCall.PostsService;
import APIsCall.UsersService;

import io.qameta.allure.*;

import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.apache.commons.lang3.ObjectUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.lang.annotation.Target;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Properties;

import static io.restassured.RestAssured.*;
import static io.restassured.http.ContentType.JSON;
import static io.restassured.path.json.JsonPath.from;
import static java.util.Optional.empty;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class APIsTests {

    TestData newTest = new TestData();
    CommentsService newCommentsServiceCall = new CommentsService();
    UsersService newUsersServicesCall = new UsersService();
    PostsService newPostsServiceCall = new PostsService();

    @Test
    @DisplayName("Verify GET users by username")
    @Description("Query the GET users API with certain username and store users data")
    @Severity(SeverityLevel.BLOCKER)
    @Epic("Users API Tests")
    @Story("Story name: Querying users data against certain username should return all relevant user's data")
    @BeforeClass
    private void getUserData() {
        Response userResponse = newUsersServicesCall.getUserDataByUsername(newTest.userName);
        JsonPath jsonPath = userResponse.jsonPath();
        newTest.actualUserName = jsonPath.get("username");
        newTest.userID = jsonPath.get("id");
        newTest.name = jsonPath.get("name");
        newTest.email = jsonPath.get("email");
    }

    @Test
    @Severity(SeverityLevel.BLOCKER)
    @DisplayName("Verify GET user posts by userId")
    @Description("Query the GET posts API with certain userId and store posts data")
    @Epic("POSTS API Tests")
    @Story("Story name: Posts made by certain user should be successfully returned upon querying against certain userID")
    @BeforeClass
    private void getUserPosts() {
        Response postResponse = newPostsServiceCall.getPostsByUserId("userId", newTest.userID);
        JsonPath jsonPath = postResponse.jsonPath();
        newTest.title = jsonPath.get("title");
        newTest.actualUserId = jsonPath.get("userId");
        newTest.body = jsonPath.get("body");
        newTest.postIDs = jsonPath.get("id");
    }

    @Test
    @Severity(SeverityLevel.BLOCKER)
    @DisplayName("Verify GET users by username actually returns users with the right username")
    @Description("Query get Users API with x username and verify that the returned username matches given username")
    @Epic("Users API Tests")
    @Story("Story name: Upon querying the GET users API with username, username returned should match the given username")

    public void verifySearchByUsername() {
       assertThat(newTest.actualUserName, equalTo(newTest.userName));
    }

    @Test
    @DisplayName("Verify GET user Posts by userID")
    @Description("Query GET posts API to return posts posted by the ID linked to a certain username")
    @Epic("POSTS API Tests")
    @Story("Story name: Querying Posts API with certain UserId should return posts made by that user")
    public void searchPostsByUserId() {
           newTest.postIDs = newPostsServiceCall.getPostsIdsByUserId("userId", newTest.userID);
        }

    @Test
    @DisplayName("Verify GET list of posts actually belongs to the targeted user")
    @Description("Verify GET list of posts actually belongs to the targeted user")
    @Epic("POSTS API Tests")
    @Story("Story name: Post/s returned by POSTS API should be linked to an ID that matches the given user's ID")
    public void verifyPostsBelongToUser() {

        for (int i = 0; i < newTest.postIDs.size(); i++) {
            assertThat(Collections.singletonList(newTest.actualUserId.get(i)), equalTo(newTest.userID));
        }
    }

    @Test
    @DisplayName("Verify GET comments made on a certain Post")
    @Description("Query GET comments API to return comments made on certain Post")
    @Epic("Comments API Tests")
    @Story("Story name: Querying comments GET API with certain postid should return comments made on that post")
    public void getCommentsByPost() {
        for (int i = 0; i < newTest.postIDs.size(); i++) {
            newTest.commentsID = newCommentsServiceCall.getCommentsIdByPostId(newTest.postIDs.get(i));
            newTest.commentsEmail = newCommentsServiceCall.getEmailsByPostId(newTest.postIDs.get(i));
        }
        assertThat(newTest.commentsID, hasItem(notNullValue()));
        assertThat(newTest.commentsEmail, hasItem(notNullValue()));
    }

    @Test
    @DisplayName("Verify GET comments made on a certain Post")
    @Description("Query GET comments API to return comments made on certain Post")
    @Epic("Comments API Tests")
    @Story("Story name: Email address for users who made certain comment should be valid")
    public void validateCommentsEmails() {

                for (int f=0; f< newTest.commentsEmail.size(); f++)
                {
                    boolean isEmailValid = newCommentsServiceCall.emailValidation(newTest.commentsEmail.get(f));
                    assertThat(isEmailValid,equalTo(true));
                }
    }

    @Test
    @DisplayName("Verify that the status code of GET users API matches the expected code")
    @Description ("Check GET users API status code")
    @Issue("MBQ-111")
    @Epic("Users API Tests")
    @Story("Story name: Users GET API should return the expected status code upon successful call")
    public void checkUsersStatusCode() {
             /* Given The users API that is supposed to return a list of users with their details
                When I hit the API request
                Then  I should be getting the desired response code defined for this API,
                e.g.  200 for success */
        newUsersServicesCall.verifyStatusCode(newUsersServicesCall.usersBasePath, newUsersServicesCall.usersStatusCode);
    }

    @Test
    @DisplayName("Verify that the status code of GET users API matches the expected code")
    @Description ("Check GET Posts API status code")
    @Issue("MBQ-111")
    @Epic("Comments API Tests")
    @Story("Story name: Posts GET API should return the expected status code upon successful call")
    public void checkPostsStatusCode() {
             /* Given The posts API that is supposed to return a list of posts with their details
                When I hit the API request
                Then  I should be getting the desired response code defined for this API,
                e.g.  200 for success */
        newPostsServiceCall.verifyStatusCode(newPostsServiceCall.postsBasePath, newPostsServiceCall.postsStatusCode);
    }

    @Test
    @DisplayName("Verify that the status code of GET comments API matches the expected code")
    @Description ("Check GET comments API status code")
    @Issue("MBQ-111")
    @Epic("Comments API Tests")
    @Story("Story name: Comments GET API should return the expected status code upon successful call")
    public void checkCommentsStatusCode() {
             /* Given The comments API that is supposed to return a list of posts with their details
                When I hit the API request
                Then  I should be getting the desired response code defined for this API,
                e.g.  200 for success */
        newCommentsServiceCall.verifyStatusCode(newCommentsServiceCall.commentsBasePath, newCommentsServiceCall.commentsStatusCode);
    }

}



