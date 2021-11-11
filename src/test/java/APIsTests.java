import APIsCall.CommentsService;
import APIsCall.PostsService;
import APIsCall.UsersService;

import io.qameta.allure.*;

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
    String userName = "Delphine";

    public APIsTests() {
       currentUser = getUserData();
       userPosts = getUserPosts();
       postIDs = searchPostsByUserId();
  }

    @Test
    @DisplayName("Verify GET users by username")
    @Description("Query the GET users API with certain username and store users data")
    @Severity(SeverityLevel.BLOCKER)
    @Epic("Users API Tests")
    @Story("Story name: Querying users data against certain username should return all relevant user's data")
    @BeforeClass
    private User getUserData() {
        return newUsersServicesCall.getUserDataByUsername(userName);
    }

    @Test
    @Severity(SeverityLevel.BLOCKER)
    @DisplayName("Verify GET user posts by userId")
    @Description("Query the GET posts API with certain userId and store posts data")
    @Epic("POSTS API Tests")
    @Story("Story name: Posts made by certain user should be successfully returned upon querying against certain userID")
    @BeforeClass
    private Post[] getUserPosts() {
        return newPostsServiceCall.getPostsByUserId("userId", currentUser.getId());
    }

    @Test
    @Severity(SeverityLevel.BLOCKER)
    @DisplayName("Verify GET users by username actually returns users with the right username")
    @Description("Query get Users API with x username and verify that the returned username matches given username")
    @Epic("Users API Tests")
    @Story("Story name: Upon querying the GET users API with username, username returned should match the given username")
    public void verifySearchByUsername() {
       assertThat(currentUser.getUsername(), equalTo(userName));
    }

    @Test
    @DisplayName("Verify GET user Posts by userID")
    @Description("Query GET posts API to return posts posted by the ID linked to a certain username")
    @Epic("POSTS API Tests")
    @Story("Story name: Querying Posts API with certain UserId should return posts made by that user")
    public List<Integer> searchPostsByUserId() {
        return newPostsServiceCall.getPostsIdsByUserId("userId", currentUser.getId());
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



