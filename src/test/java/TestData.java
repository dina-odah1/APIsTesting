
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.BeforeClass;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static io.restassured.RestAssured.*;

public class TestData {

    // Input Data that are meant to query with
    List<String> userName = Arrays.asList("Delphine");

    // Parameters returned by APIs response
    List<Integer> userID;
    List<Integer> actualUserId;
    List<Integer> postIDs;
    List<String> actualUserName;
    List<String> name;
    List<String> email;
    List<String> title;
    List<String> body;
    ArrayList<Integer> commentsID;
    ArrayList<String> commentsEmail;
}
