package requests;

import io.restassured.specification.RequestSpecification;
import lombok.Getter;

@Getter
public class TodosRequester {

    private TodosRequest request;
    private ValidatedTodosRequest validatedRequest;

    public TodosRequester(RequestSpecification requestSpecification) {
        this.request = new TodosRequest(requestSpecification);
        this.validatedRequest = new ValidatedTodosRequest(requestSpecification);
    }

}
