package api.todos;

import annotations.PrepareTodo;
import api.BaseApiTest;
import io.qameta.allure.Description;
import models.Todo;
import org.junit.jupiter.api.Test;
import requests.TodosRequest;
import specifications.request.RequestSpec;
import specifications.response.ErrorResponse;

import static dataGenerators.TestDataGenerator.generateTestData;

public class ApiPutTodosTest extends BaseApiTest {

    @Test
    @PrepareTodo(1)
    @Description("Авторизованный пользователь может обновить существующую задачу")
    public void testUpdateTodoWithValidAuth() {
        Long existingTodoId = todosRequester.getValidatedRequest().readAll().get(0).getId();
        Todo updatedTodo = generateTestData(Todo.class).toBuilder().id(existingTodoId).build();

        todosRequester.getValidatedRequest().update(existingTodoId, updatedTodo);

        softly.assertThat(todosRequester.getValidatedRequest().readAll()).contains(updatedTodo);
    }

    @Test
    @PrepareTodo(1)
    @Description("Неавторизованный пользователь не может обновить задачу и получает ошибку 401")
    public void testUpdateTodoWithoutAuthHeader() {
        Long existingTodoId = todosRequester.getValidatedRequest().readAll().get(0).getId();
        Todo updatedTodo = generateTestData(Todo.class).toBuilder().id(existingTodoId).build();

        new TodosRequest(RequestSpec.unauthSpec()).update(existingTodoId, updatedTodo)
                .then().assertThat().spec(ErrorResponse.userIsUnauthorized());
    }

    @Test
    @Description("Если попытаться обновить несуществующую задачу, сервер возвращает ошибку 404")
    public void testUpdateNonExistentTodo() {
        Todo updatedTodo = generateTestData(Todo.class).toBuilder().id(0L).build();

        todosRequester.getRequest().update(0L, updatedTodo)
                .then().assertThat().spec(ErrorResponse.notFound());
    }

}