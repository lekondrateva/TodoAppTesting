package api.todos;

import annotations.PrepareTodo;
import api.BaseApiTest;
import io.qameta.allure.Description;
import models.Todo;
import org.junit.jupiter.api.Test;
import requests.TodosRequest;
import specifications.request.RequestSpec;
import specifications.response.ErrorResponse;

import static specifications.request.RequestSpec.authSpecAsAdmin;

public class ApiDeleteTodosTest extends BaseApiTest {

    @Test
    @PrepareTodo(1)
    @Description("Авторизированный юзер может удалить todo")
    public void testDeleteExistingTodoWithValidAuth() {
        Todo createdTodo = todosRequester.getValidatedRequest().readAll().get(0);

        todosRequester.getValidatedRequest().delete(createdTodo.getId());

        softly.assertThat(todosRequester.getValidatedRequest().readAll()).doesNotContain(createdTodo);
    }

    @Test
    @PrepareTodo(1)
    @Description("Неавторизированный юзер не может удалить todo")
    public void testDeleteTodoWithoutAuthHeader() {
        Todo createdTodo = todosRequester.getValidatedRequest().readAll().get(0);

        new TodosRequest(RequestSpec.unauthSpec()).delete(createdTodo.getId())
                .then().assertThat().spec(ErrorResponse.userIsUnauthorized());

        softly.assertThat(todosRequester.getValidatedRequest().readAll()).contains(createdTodo);
    }

    @Test
    @Description("Авторизованный юзер не может удалить задачу с несуществующим id")
    public void testDeleteNonExistentTodo() {
        long nonExistingId = 0L;

        new TodosRequest(authSpecAsAdmin()).delete(nonExistingId)
                .then().assertThat().spec(ErrorResponse.invalidRequestParameters());
    }

}