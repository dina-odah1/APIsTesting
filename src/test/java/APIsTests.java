import APIsCall.CommentsService;
import APIsCall.PostsService;
import APIsCall.UsersService;
import Listeners.TestLogger;
import utils.configuration.ConfigLoader;

import io.qameta.allure.*;

import io.restassured.response.Response;
import json.model.post.Post;
import json.model.user.User;
import org.junit.jupiter.api.DisplayName;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class APIsTests {

    UsersService newUsersServicesCall = new UsersService();
    PostsService newPostsServiceCall = new PostsService();
    CommentsService newCommentsServiceCall = new CommentsService();

    // shared values
    User currentUser;
    Post[] userPosts;
    List<Integer> postIDs;

//    public APIsTests() {
//       currentUser = newUsersServicesCall.getUserDataByUsername(userName).then().extract().as(User.class);
//       userPosts = getUserPosts();
//       postIDs = searchPostsByUserId();
//  }

    @Test
    @DisplayName("Verify GET users by username")
    @Description("Query the GET users API with certain username and store users data")
    @Severity(SeverityLevel.BLOCKER)
    @Epic("Users API Tests")
    @Story("Story name: Querying users data against certain username should return all relevant user's data")
    @BeforeClass
    private void getUserData() {
         currentUser = newUsersServicesCall.getUserObjectByUsername(ConfigLoader.getInstance().getUsername());
    }

    @Test
    @Severity(SeverityLevel.BLOCKER)
    @DisplayName("Verify GET user posts by userId")
    @Description("Query the GET posts API with certain userId and store posts data")
    @Epic("POSTS API Tests")
    @Story("Story name: Posts made by certain user should be successfully returned upon querying against certain userID")
    @BeforeClass
    private void getUserPosts() {
       userPosts = newPostsServiceCall.getPostsByUserId("userId", currentUser.getId()).as(Post[].class);
    }

    @Test
    @Severity(SeverityLevel.BLOCKER)
    @DisplayName("Verify GET users by username actually returns users with the right username")
    @Description("Query get Users API with x username and verify that the returned username matches given username")
    @Epic("Users API Tests")
    @Story("Story name: Upon querying the GET users API with username, username returned should match the given username")
    public void verifySearchByUsername() {
       assertThat(currentUser.getUsername(), equalTo(ConfigLoader.getInstance().getUsername()));
    }

    @Test
    @DisplayName("Verify GET user Posts by userID")
    @Description("Query GET posts API to return posts posted by the ID linked to a certain username")
    @Epic("POSTS API Tests")
    @Story("Story name: Querying Posts API with certain UserId should return posts made by that user")
    public void searchPostsByUserId() {
        postIDs = newPostsServiceCall.getPostsIdsByUserId("userId", currentUser.getId());
    }

    @Test
    @DisplayName("Verify GET list of posts actually belongs to the targeted user")
    @Description("Verify GET list of posts actually belongs to the targeted user")
    @Epic("POSTS API Tests")
    @Story("Story name: Post/s returned by POSTS API should be linked to an ID that matches the given user's ID")
    public void verifyPostsBelongToUser() {
        for (Post post : userPosts) {
            assertThat(post.getUserId(), equalTo(currentUser.getId()));
        }
    }

    @Test
    @DisplayName("Verify GET comments made on a certain Post")
    @Description("Query GET comments API to return comments made on certain Post")
    @Epic("Comments API Tests")
    @Story("Story name: Querying comments GET API with certain postid should return comments made on that post")
    public void getCommentsByPost() {
        for (Post post : userPosts) {
            List<Integer> commentsID = newCommentsServiceCall.getCommentsIdByPostId(post.getId());
            List<String> commentsEmail = newCommentsServiceCall.getEmailsByPostId(post.getId());

            assertThat(commentsID, hasItem(notNullValue()));
            assertThat(commentsEmail, hasItem(notNullValue()));
        }
    }

    @Test
    @DisplayName("Verify GET comments made on a certain Post")
    @Description("Query GET comments API to return comments made on certain Post")
    @Epic("Comments API Tests")
    @Story("Story name: Email address for users who made certain comment should be valid")
    public void validateCommentsEmails() {
        for (Post post : userPosts) {
            List<String> commentsEmail = newCommentsServiceCall.getEmailsByPostId(post.getId());
            for (String comment : commentsEmail) {
                boolean isEmailValid = newCommentsServiceCall.emailValidation(comment);
                assertThat(isEmailValid,equalTo(true));
            }
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
        newUsersServicesCall.verifyStatusCode(ConfigLoader.getInstance().getUsersBasePath(), ConfigLoader.getInstance().getSuccessCode());
    }

    @Test
    @DisplayName("Verify that the status code of GET users API matches the expected code")
    @Description ("Check GET Posts API status code")
    @Issue("MBQ-111")
    @Epic("Posts API Tests")
    @Story("Story name: Posts GET API should return the expected status code upon successful call")
    public void checkPostsStatusCode() {
             /* Given The posts API that is supposed to return a list of posts with their details
                When I hit the API request
                Then  I should be getting the desired response code defined for this API,
                e.g.  200 for success */
        newPostsServiceCall.verifyStatusCode(ConfigLoader.getInstance().getPostsBasePath(), ConfigLoader.getInstance().getSuccessCode());
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
        newCommentsServiceCall.verifyStatusCode(ConfigLoader.getInstance().getCommentsBasePath(), ConfigLoader.getInstance().getSuccessCode());
    }

    @Test
    @Description ("User should be able to Post new post")
    @Issue("h")
    @Epic("Posts APIs Tests")
    @Story("Story name: Users should be able to add a new post successfully through POST posts API")
    public void postNewUserPost() {
             /* Given a user who wants to post a new post
                When I hit the POST posts API with specific post body request
                Then  I should be getting the same parameters sent in the API response body */
        Post reqPost = new Post().setUserId(currentUser.getId()).setBody("Bodyyyyyy").setTitle("Titleee");
        Response postAPIResponse = newPostsServiceCall.postAPIResponse(ConfigLoader.getInstance().getPostsBasePath(), reqPost);
        assertThat(postAPIResponse.statusCode(), equalTo(ConfigLoader.getInstance().getCreateSuccessCode()));
        assertPostEqual(postAPIResponse.as(Post.class), reqPost);
    }

    @Test
    @Description ("Newly posted post should be returned in the GET posts API")
    @Issue("h")
    @Epic("Posts APIs Tests")
    @Story("Story name: Users should be able to get newly added post successfully through GET posts API")
    public void confirmNewPostsAdded() {
             /* Given The comments API that is supposed to return a list of posts with their details
                When I hit the API request
                Then  I should be getting the desired response code defined for this API,
                e.g.  200 for success */
        Post reqPost = new Post().setUserId(currentUser.getId()).setBody("Bodyyyyyy").setTitle("Titleee");
        Response postAPIResponse = newPostsServiceCall.postAPIResponse(ConfigLoader.getInstance().getPostsBasePath(), reqPost);
        Post responsePost = postAPIResponse.as(Post.class);
        List<Integer> updatedPostIds = newPostsServiceCall.getPostsIdsByUserId("userId", currentUser.getId());
        assertThat(updatedPostIds, hasItem(responsePost.getId()));
    }

    @Test
    @Description ("User should be able to update an existing post")
    @Issue("h")
    @Epic("Posts APIs Tests")
    @Story("Story name: Users should be able to update previous posts")
    public void updateUserPost() {
             /* Given a user who wants to post a new post
                When I hit the POST posts API with specific post body request
                Then  I should be getting the same parameters sent in the API response body */
        Post reqPost = new Post().setUserId(currentUser.getId()).setTitle("Updated Title").setBody("Updated Body");
        Response putAPIResponse = newPostsServiceCall.putAPIResponse(ConfigLoader.getInstance().getPostsBasePath(), userPosts[0].getId(), reqPost);
        assertThat(putAPIResponse.statusCode(), equalTo(ConfigLoader.getInstance().getSuccessCode()));
        assertPostEqual(putAPIResponse.as(Post.class), reqPost);
    }

    @Test
    @Description ("Newly posted post should be returned in the GET posts API")
    @Issue("h")
    @Epic("Posts APIs Tests")
    @Story("Story name: Users should be able to get newly updated post successfully through GET posts API")
    public void confirmPostsActuallyUpdated() {
             /* Given a user who has updated a previous posts
                When I hit the GET posts API request
                Then  I should be getting the updated post details within the response */
        Post reqPost = new Post().setUserId(currentUser.getId()).setTitle("Updated Title").setBody("Updated Body");
        Response postAPIResponse = newPostsServiceCall.putAPIResponse(ConfigLoader.getInstance().getPostsBasePath(), userPosts[0].getId(), reqPost);
        Post updatedPost = newPostsServiceCall.getPostById(userPosts[0].getId()).as(Post.class);
        assertPostEqual(updatedPost, reqPost);
    }

    @Step ("Checking the response parameters of post submitted matches the values posted")
    private void assertPostEqual(Post responsePost, Post requestPost) {
        assertThat(responsePost.getUserId(), equalTo(requestPost.getUserId()));
        assertThat(responsePost.getBody(), equalTo(requestPost.getBody()));
        assertThat(responsePost.getTitle(), equalTo(requestPost.getTitle()));
    }
}



