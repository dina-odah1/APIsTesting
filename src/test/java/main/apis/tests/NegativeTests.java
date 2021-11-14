package main.apis.tests;

import apis.calls.PostsService;
import apis.calls.UsersService;
import io.qameta.allure.*;
import io.restassured.response.Response;
import org.hamcrest.Matchers;
import org.testng.Assert;
import org.testng.annotations.Test;
import utils.configuration.ConfigLoader;

public class NegativeTests {

    UsersService newUserServices = new UsersService();
    PostsService newPostService = new PostsService();

    @Test(description = "Query With invalid parameter name")
    @Severity(SeverityLevel.MINOR)
    @Description("Verifying querying with incorrect parameter name")
    @Issue("MBQ-113")
    @Epic("Users API Tests")
    @Story("Story name: Query users GET API with invalid parameter name")
    /* Give a user who is trying to search with invalid parameter name
     * When I query the GET users API with incorrect parameter ID
     * Then I should be not be getting a response */
    public void queryAPIWithInvalidParameter() {
        Response response = newUserServices.requestCall(ConfigLoader.getInstance().
                getUsersBasePath()).queryParam(ConfigLoader.getInstance().
                getInvalidParm(), ConfigLoader.getInstance().getUsername()).get();
        response.then().body("username", Matchers.hasSize(0));
    }

    @Test (description = "Query Posts API with an ID that doesn't exist")
    @Severity(SeverityLevel.MINOR)
    @Description("Verifying searching for a post that does a post with no body content")
    @Epic("Posts API Tests")
    @Story("Story name: Query Posts GET API with post ID")
    /* Give a user who wants to search for a user that doesn't exist
     * When I query the GET posts API with that ID
     * Then I should be getting not found error with no response */
    public void queryWithInvalidBasePath() {
         Response response = newPostService.requestCall(ConfigLoader.getInstance().
                        getPostsBasePath()).get("/"+ConfigLoader.getInstance().getRandomPostId());
        newPostService.verifyStatusCode(response, ConfigLoader.getInstance().getNotFound());
    }

    @Test (description = "Check Put post API with invalid request body")
    @Severity(SeverityLevel.NORMAL)
    @Description("Verifying updating post with invalid content")
    @Issue("MBQ-114")
    @Epic("Posts API Tests")
    @Story("Story name: Users should not be able to submit unexpected post body contenet")
    /* Give a user who wants to update an existing post
     * When I try to an update a post with unexpected body parameters
     * Then my post update shouldn't be accepted  */
    public void putWithInvalidBody() {
        Response response = newPostService.requestCall(ConfigLoader.getInstance().
                getPostsBasePath()).body("{\n" +
                "    \"image\": \"org.example\"\n" +
                "}").when().put("/9");
        Assert.assertNotEquals(response.getStatusCode(), ConfigLoader.getInstance().getSuccessCode());
    }

    @Test (description = "Check POST post API with invalid request body")
    @Severity(SeverityLevel.NORMAL)
    @Description("Verifying posting a post with no body content")
    @Issue("MBQ-115")
    @Epic("Posts API Tests")
    @Story("Story name: Users should be able to add posts with valid content via POST posts API")
    /* Give a user who wants to submit a new post
     * When I try to submit  post with no content
     * Then I shouldn't be able to submit that post  */
    public void postWithInvalidBody() {
        Response response = newPostService.requestCall(ConfigLoader.getInstance().getPostsBasePath()).
                post();
        Assert.assertNotEquals(response.getStatusCode(), ConfigLoader.getInstance().getCreateSuccessCode());
    }

    @Test (description = "Check Query GET users API with inaccurate username")
    @Severity(SeverityLevel.MINOR)
    @Description("Querying the GET users API with inaccurate username")
    @Epic("Users API Tests")
    @Story("Story name: Querying users data against certain username")
    /* Give a user who has specific username
     * When I query the GET users API with inaccurate (all capital or small) or invalid username
     * Then I get no data as the username is case-sensitive */
    public void searchUserCapitalUsername() {
        String usernameCapitalized = ConfigLoader.getInstance().getUsername().toUpperCase();
        Response response = newUserServices.requestCall(ConfigLoader.getInstance().
                getUsersBasePath()).queryParam("username",usernameCapitalized).get();
        response.then().body("", Matchers.hasSize(0));
    }
}
