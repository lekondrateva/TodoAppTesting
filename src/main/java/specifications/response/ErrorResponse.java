package specifications.response;

import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.specification.ResponseSpecification;
import org.apache.http.HttpStatus;

public class ErrorResponse {

    public static ResponseSpecification userIsUnauthorized() {
        ResponseSpecBuilder responseSpecBuilder = new ResponseSpecBuilder();
        responseSpecBuilder.expectStatusCode(HttpStatus.SC_UNAUTHORIZED);
        return responseSpecBuilder.build();
    }

    public static ResponseSpecification invalidRequestParameters() {
        ResponseSpecBuilder responseSpecBuilder = new ResponseSpecBuilder();
        responseSpecBuilder.expectStatusCode(HttpStatus.SC_BAD_REQUEST);
        return responseSpecBuilder.build();
    }

    public static ResponseSpecification notFound() {
        ResponseSpecBuilder responseSpecBuilder = new ResponseSpecBuilder();
        responseSpecBuilder.expectStatusCode(HttpStatus.SC_NOT_FOUND);
        return responseSpecBuilder.build();
    }

}
