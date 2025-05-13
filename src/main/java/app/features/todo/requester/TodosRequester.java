package app.features.todo.requester;

import app.features.todo.request.TodosRequest;
import app.features.todo.request.ValidatedTodosRequest;
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