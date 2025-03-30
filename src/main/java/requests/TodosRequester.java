package requests;

import io.restassured.specification.RequestSpecification;
import lombok.Getter;

@Getter
public class TodosRequester {

    private TodosRequest todosRequest;
    private ValidatedTodosRequest validatedRequest;

    public TodosRequester(RequestSpecification requestSpecification) {
        this.todosRequest = new TodosRequest(requestSpecification);
        this.validatedRequest = new ValidatedTodosRequest(requestSpecification);
    }

}