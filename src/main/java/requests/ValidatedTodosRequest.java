package requests;

import dataGenerators.TestDataStorage;
import interfaces.CrudInterface;
import io.restassured.specification.RequestSpecification;
import models.Todo;
import org.apache.http.HttpStatus;

import java.util.List;

public class ValidatedTodosRequest extends RequestBase implements CrudInterface<Todo> {

    private TodosRequest todosRequest;

    public ValidatedTodosRequest(RequestSpecification reqSpec) {
        super(reqSpec);
        todosRequest = new TodosRequest(reqSpec);
    }

    @Override
    public String create(Todo entity) {
        var response = todosRequest.create(entity)
                .then()
                .assertThat()
                .statusCode(HttpStatus.SC_CREATED).extract().asString();
        TestDataStorage.getInstance().addData(entity);
        return response;
    }

    @Override
    public Todo update(long id, Todo entity) {
        return todosRequest.update(id, entity)
                .then()
                .statusCode(HttpStatus.SC_OK).extract().as(Todo.class);
    }

    @Override
    public String delete(long id) {
        return todosRequest.delete(id)
                .then()
                .statusCode(HttpStatus.SC_NO_CONTENT)
                .extract().body()
                .asString();
    }

    @Override
    public List<Todo> readAll(int offset, int limit) {
        Todo[] todos = todosRequest.readAll(offset, limit)
                .then()
                .statusCode(HttpStatus.SC_OK)
                .extract().as(Todo[].class);
        return List.of(todos);
    }

    public List<Todo> readAll() {
        Todo[] todos = todosRequest.readAll()
                .then()
                .statusCode(HttpStatus.SC_OK)
                .extract().as(Todo[].class);
        return List.of(todos);
    }

}
