package APIsCall;

import io.qameta.allure.Step;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import utils.configuration.ConfigLoader;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.equalTo;

public class GlobalAPICalls {
    public static String baseUri = ConfigLoader.getInstance().getBaseUri();

    public static RequestSpecification getRequestSpec(String apiBasePath){
        return new RequestSpecBuilder().
                setBaseUri(baseUri).
                setBasePath(apiBasePath).
                setContentType(ContentType.JSON).
                addFilter(new AllureRestAssured()).
                log(LogDetail.ALL).
                build();
    }

    public static ResponseSpecification getResponseSpec(){
        return new ResponseSpecBuilder().
                log(LogDetail.ALL).
                build();
    }

    public static RequestSpecification requestCall(String apiBasePath) {
        return given(getRequestSpec(apiBasePath));
    }

    public static Response getAPIResponse(String apiBaseBath) {
        return requestCall(apiBaseBath).
                when().get().
                then().spec(getResponseSpec()).
                extract().
                response();
    }

    public Response postAPIResponse(String apiBaseBath, Object reqPost) {
        return requestCall(apiBaseBath).
                body(reqPost).
                when().post().
                then().spec(getResponseSpec()).
                extract().
                response();
    }

    public Response deleteAPIResponse(String apiBaseBath, Integer postId) {
        return requestCall(apiBaseBath).
                when().delete(String.valueOf(postId)).
                then().spec(getResponseSpec()).
                extract().
                response();
    }

    public Response putAPIResponse(String apiBaseBath, Integer postId, Object reqPost) {
        return requestCall(apiBaseBath).
                body(reqPost).
                when().put(String.valueOf(postId)).
                then().spec(getResponseSpec()).
                extract().
                response();
    }

    @Step
    public void verifyStatusCode(String apiBaseBath, int statusCode) {
        requestCall(apiBaseBath).
                when().
                get().
                then().
                log().
                all().
                statusCode(equalTo(statusCode));
    }

}
