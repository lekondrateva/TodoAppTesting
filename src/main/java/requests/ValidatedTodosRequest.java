package requests;

import dataGenerators.TestDataStorage;
import interfaces.CrudInterface;
import io.qameta.allure.Step;
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
    @Step("Create {entity} and validate response")
    public String create(Todo entity) {
        var response = todosRequest.create(entity)
                .then()
                .assertThat()
                .statusCode(HttpStatus.SC_CREATED).extract().asString();
        TestDataStorage.getInstance().addData(entity);
        return response;
    }

    @Override
    @Step("Update {entity} by id = {id} and validate response")
    public Todo update(long id, Todo entity) {
        return todosRequest.update(id, entity)
                .then()
                .statusCode(HttpStatus.SC_OK).extract().as(Todo.class);
    }

    @Override
    @Step("Delete todo by id = {id} and validate response")
    public String delete(long id) {
        return todosRequest.delete(id)
                .then()
                .statusCode(HttpStatus.SC_NO_CONTENT)
                .extract().body()
                .asString();
    }

    @Override
    @Step("Read all todos with offset = {offset}, limit = {limit} and validate response")
    public List<Todo> readAll(int offset, int limit) {
        Todo[] todos = todosRequest.readAll(offset, limit)
                .then()
                .statusCode(HttpStatus.SC_OK)
                .extract().as(Todo[].class);
        return List.of(todos);
    }

    @Step("Read all todos and validate response")
    public List<Todo> readAll() {
        Todo[] todos = todosRequest.readAll()
                .then()
                .statusCode(HttpStatus.SC_OK)
                .extract().as(Todo[].class);
        return List.of(todos);
    }

}