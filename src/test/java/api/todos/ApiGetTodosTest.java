package api.todos;

import annotations.PrepareTodo;
import api.BaseApiTest;
import dataGenerators.TestDataStorage;
import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import models.Todo;
import org.junit.jupiter.api.Test;
import requests.TodosRequest;
import specifications.request.RequestSpec;
import specifications.response.ErrorResponse;

import java.util.List;

@Feature("API GET Todos Tests")
public class ApiGetTodosTest extends BaseApiTest {

    @Test
    @PrepareTodo(3)
    @Description("Авторизованный пользователь может получить список задач")
    public void testGetTodosWithValidAuth() {
        List<Todo> todos = todosRequester.getValidatedRequest().readAll();

        softly.assertThat(todos).hasSize(TestDataStorage.getInstance().getStorage().size());
    }

    @Test
    @Description("Неавторизованный пользователь не может получить список задач")
    public void testGetTodosWithoutAuth() {
        new TodosRequest(RequestSpec.unauthSpec()).readAll()
                .then().assertThat().spec(ErrorResponse.userIsUnauthorized());
    }

    @Test
    @PrepareTodo(5)
    @Description("При передаче параметров offset и limit система возвращает корректные результаты")
    public void testGetTodosWithOffsetAndLimit() {
        List<Todo> todos = todosRequester.getValidatedRequest().readAll(2, 2);

        softly.assertThat(todos).hasSize(2);
    }

    @Test
    @Description("Если передать некорректный offset, сервер возвращает ошибку 400")
    public void testGetTodosWithInvalidOffset() {
        new TodosRequest(RequestSpec.authSpecAsAdmin()).readAll(-1, 10)
                .then().assertThat().spec(ErrorResponse.invalidRequestParameters());
    }

    @Test
    @Description("Если передать некорректный limit, сервер возвращает ошибку 400")
    public void testGetTodosWithInvalidLimit() {
        new TodosRequest(RequestSpec.authSpecAsAdmin()).readAll(-1, 10)
                .then().assertThat().spec(ErrorResponse.invalidRequestParameters());
    }

}