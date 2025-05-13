package app.features.todo.request;

import app.common.interfaces.CrudInterface;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import app.features.todo.model.Todo;

import static io.restassured.RestAssured.given;

public class TodosRequest extends RequestBase implements CrudInterface<Todo> {

    private static final String TODO_ENDPOINT = "/app";

    public TodosRequest(RequestSpecification reqSpec) {
        super(reqSpec);
    }

    @Override
    @Step("Create {entity}")
    public Response create(Todo entity) {
        return given()
                .spec(reqSpec)
                .body(entity)
                .post(TODO_ENDPOINT);
    }

    @Override
    @Step("Update {id}")
    public Response update(long id, Todo entity) {
        return given()
                .spec(reqSpec)
                .body(entity)
                .put(TODO_ENDPOINT + "/" + id);
    }

    @Override
    @Step("Delete {id}")
    public Response delete(long id) {
        return given()
                .spec(reqSpec)
                .delete(TODO_ENDPOINT + "/" + id);
    }

    @Override
    @Step("Read all")
    public Response readAll() {
        return given()
                .spec(reqSpec)
                .get(TODO_ENDPOINT);
    }

    @Override
    public Response readAll(int offset, int limit) {
        return given()
                .spec(reqSpec)
                .queryParam("offset", offset)
                .queryParam("limit", limit)
                .when()
                .get(TODO_ENDPOINT);
    }

}
