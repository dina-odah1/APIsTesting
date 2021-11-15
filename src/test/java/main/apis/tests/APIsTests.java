package main.apis.tests;

import apis.calls.CommentsService;
import apis.calls.PostsService;
import apis.calls.UsersService;

import org.hamcrest.Matchers;
import utils.configuration.ConfigLoader;
import io.qameta.allure.*;
import io.restassured.response.Response;
import json.model.post.Post;
import json.model.user.User;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import java.util.List;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class APIsTests {

    UsersService newUsersServicesCall = new UsersService();
    PostsService newPostsServiceCall = new PostsService();
    CommentsService newCommentsServiceCall = new CommentsService();

    User currentUser;
    Post[] userPosts;
    List<Integer> postIDs;

    @Test (description = "Verify GET users by username")
    @Description("Query the GET users API with certain username and store users data")
    @Severity(SeverityLevel.CRITICAL)
    @Epic("Users API Tests")
    @Story("Story name: Querying users data against certain username")
    @BeforeClass
    /* Give a user who has specific username
     * When I query the GET users API with that username
     * Then I get the data related to that particular username */
    private void getUserData() {
         currentUser = newUsersServicesCall.getUserObjectByUsername(ConfigLoader.getInstance().getUsername());
         assertThat(currentUser.getUsername(), equalTo(ConfigLoader.getInstance().getUsername()));
    }

    @Test(description = "Verify GET user posts by userId")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Query the GET posts API with certain userId and store posts data")
    @Epic("Posts API Tests")
    @Story("Story name: User's Posts should be successfully returned upon querying against certain userID")
    @BeforeClass
    /* Give a username that has a unique userId
     * When I query the GET posts API with that userId
     * Then I should be getting all posts made by the user */
    private void getUserPosts() {
        Response response = newPostsServiceCall.getPostsByUserId("userId", currentUser.getId());
        userPosts = response.as(Post[].class);
        postIDs = newPostsServiceCall.getPostsIdsByUserId("userId", currentUser.getId());
    }

    @Test (description = "Verify Posts returned belong to the targeted user")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Verify GET list of posts actually belongs to the targeted user")
    @Epic("Posts API Tests")
    @Story("Story name: Post/s returned by POSTS API should be linked to an ID that matches the given user's ID")
    /* Give a user that has a unique userId
     * When I query the GET posts API with that userId
     * Then I should be getting all posts made that indeed linked to that particular Id */
    public void verifyPostsBelongToUser() {
        for (Post post : userPosts) {
            assertThat(post.getUserId(), equalTo(currentUser.getId()));
        }
    }

    @Test (description = "Verify GET comments made on a certain Post")
    @Severity(SeverityLevel.NORMAL)
    @Description("Query GET comments API to return comments made on certain Post")
    @Epic("Comments API Tests")
    @Story("Story name: Querying comments GET API with certain postid should return comments made on that post")
    /* Give a user that has one or more posts
     * When I query the GET comments API with the post/s made by that user
     * Then I should be getting all comments made on that post along with their details */
    public void getCommentsByPost() {
        for (Post post : userPosts) {
            List<Integer> commentsID = newCommentsServiceCall.getCommentsIdByPostId(post.getId());
            List<String> commentsEmail = newCommentsServiceCall.getEmailsByPostId(post.getId());
            assertThat(commentsID, hasItem(notNullValue()));
            assertThat(commentsEmail, hasItem(notNullValue()));
        }
    }

    @Test(description = "Verify comments emails made on Post are valid")
    @Severity(SeverityLevel.NORMAL)
    @Description("Verify emails linked to comments made on certain posts are valid")
    @Epic("Comments API Tests")
    @Story("Story name: Email address for users who made certain comment should be valid")
    /* Give a user that has one or more posts
     * When I query the GET comments API with the post/s made by that user
     * Then I should be getting all comments made on that post with their details */
    public void validateCommentsEmails() {
        for (Post post : userPosts) {
            List<String> commentsEmail = newCommentsServiceCall.getEmailsByPostId(post.getId());
            for (String comment : commentsEmail) {
                boolean isEmailValid = newCommentsServiceCall.emailValidation(comment);
                assertThat(isEmailValid,equalTo(true));
            }
        }
    }

    @Test(description = "Verify the ability to post a new post")
    @Severity(SeverityLevel.CRITICAL)
    @Description ("User should be able to Post new post")
    @Epic("Posts API Tests")
    @Story("Story name: Users should be able to add posts with valid content via POST posts API")
    /* Give a user who wants to post a new post
     * When I hit the POST post API with the new post details
     * Then I should be a successful response with the correct status code and body */
    public void postNewUserPost() {
        Post reqPost = newPostsServiceCall.postBuilder(currentUser.getId());
        Response postAPIResponse = newPostsServiceCall.postAPIResponse(ConfigLoader.
                getInstance().getPostsBasePath(), reqPost);
        newPostsServiceCall.verifyStatusCode(postAPIResponse, ConfigLoader.getInstance().
                getCreateSuccessCode());
        newPostsServiceCall.assertPostEqual(postAPIResponse.as(Post.class), reqPost);
    }

    @Test (description = "Verify new post is added to existing ones")
    @Severity(SeverityLevel.CRITICAL)
    @Description ("Newly posted post should be returned in the GET posts API")
    @Issue("MBQ-111")
    @Epic("Posts API Tests")
    @Story("Story name: Users should be able to get newly added post successfully")
    /* Given a user who has added new post
     * When I query the GET posts API with that userId
     * Then I should be getting the new post added to the existing list*/
    public void confirmNewPostsAdded() {
        Post reqPost = newPostsServiceCall.postBuilder(currentUser.getId());
        Post responsePost = newPostsServiceCall.postAPIResponse(ConfigLoader.getInstance().
                getPostsBasePath(), reqPost).as(Post.class);
        List<Integer> updatedPostIds = newPostsServiceCall.
                getPostsIdsByUserId("userId", currentUser.getId());
        assertThat(updatedPostIds, hasItem(responsePost.getId()));
    }

    @Test (description = "Verify updating an existing post")
    @Severity(SeverityLevel.NORMAL)
    @Description ("User should be able to update an existing post")
    @Epic("Posts API Tests")
    @Story("Story name: Users should be able to update previous posts")
    /* Given a user who wants to update an existing post
     * When I hit the PUT posts API with the updated post body request
     * Then I should be getting the same parameters sent in the API response body */
    public void updateUserPost() {
        Post reqPost = newPostsServiceCall.postBuilder(currentUser.getId());
        Response putAPIResponse = newPostsServiceCall.putAPIResponse(ConfigLoader.
                getInstance().getPostsBasePath(), userPosts[0].getId(), reqPost);
        assertThat(putAPIResponse.statusCode(), equalTo(ConfigLoader.getInstance().
                getSuccessCode()));
        newPostsServiceCall.assertPostEqual(putAPIResponse.as(Post.class), reqPost);
    }

    @Test (description = "Verify existing post is updated")
    @Severity(SeverityLevel.NORMAL)
    @Description ("The update to an existing post should be reflected in the GET posts API")
    @Issue("MBQ-112")
    @Epic("Posts API Tests")
    @Story("Story name: Users should be able to get the updated post through GET posts API")
    /* Given a user who has updated a previous posts
     * When I hit the GET posts API request
     * Then  I should be getting the updated post details within the response */
    public void confirmPostsActuallyUpdated() {
        Post reqPost = newPostsServiceCall.postBuilder(currentUser.getId());
        newPostsServiceCall.putAPIResponse(ConfigLoader.getInstance().getPostsBasePath(),
                userPosts[0].getId(), reqPost);
        Post updatedPost = newPostsServiceCall.getPostById(userPosts[0].getId()).as(Post.class);
        newPostsServiceCall.assertPostEqual(updatedPost, reqPost);
    }

    @Test (description = "Verify Delete post functionality")
    @Severity(SeverityLevel.NORMAL)
    @Description ("Users should be able to delete previously posted posts")
    @Issue("MBQ-112")
    @Epic("Posts API Tests")
    @Story("Story name: Users should be able to delete posts")
    /* Given a user who wants to delete an existing post
     * When I hit the DELETE posts API request
     * Then I should be getting the right response and post should be removed */
    public void confirmDeletePost() {
        String postsBasePath = ConfigLoader.getInstance().getPostsBasePath();
        Response response = newPostsServiceCall.deleteAPIResponse(postsBasePath,userPosts[0].getId());
        newPostsServiceCall.verifyStatusCode(response, ConfigLoader.getInstance().getSuccessCode());
        response.then().body("id", equalTo(null));
        newPostsServiceCall.requestCall(postsBasePath).get("/"+userPosts[0].getId()).
                then().body("id", Matchers.hasSize(0));
    }
}



