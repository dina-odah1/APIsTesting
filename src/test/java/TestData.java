
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.BeforeClass;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static io.restassured.RestAssured.*;

public class TestData {

    // Input Data that are meant to query with
    List<String> userName = List.of("Delphine") ;


    // Parameters returned by APIs response
    List<String> actualUserName;
    List<String> name;
    List<String> email;

    int usersStatusCode = 200;
    List<Integer> userID;
    List<Integer> actualUserId;
    List<String> title;
    List<String> body;
    int postID;
    List<Integer> userid2;
    List<Integer> postIDs;
    RequestSpecification request;
    ArrayList<Integer> commentsID;
    ArrayList<String> commentsEmail;

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
