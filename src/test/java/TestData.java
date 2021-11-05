
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.BeforeClass;

import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.*;

public class TestData {
    String username = "Delphine";
    String host ="https://jsonplaceholder.typicode.com";
    String usersBasePath = "/users";
    String postsBasePath = "/posts";
    String commentsBasePath = "/comments";
    int usersStatusCode = 200;
    int userID;
    int postID;
    List<Integer> userid2;
    List<Integer> postIDs;
    RequestSpecification request;

    @BeforeClass
    public static void postsService() {
        baseURI ="https://jsonplaceholder.typicode.com";
        basePath ="/posts";
        port =8888;
    }

    @BeforeClass
    public static void commentsService() {
        baseURI ="https://jsonplaceholder.typicode.com";
        basePath ="/posts";
        port =8888;
    }

}
