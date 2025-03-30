package api.todos;

import annotations.PrepareTodo;
import api.BaseApiTest;
import io.qameta.allure.Feature;
import models.Todo;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import requests.TodosRequester;
import specifications.request.RequestSpec;
import specifications.response.ErrorResponse;

import static dataGenerators.TestDataGenerator.generateTestData;

@Feature("API PUT Todos Tests")
public class ApiPutTodosTest extends BaseApiTest {

    @Test
    @PrepareTodo(1)
    @DisplayName("TC-8 Authorized user updates a Todo")
    public void testUpdateTodoWithValidAuth() {
        Long existingTodoId = todosRequester.getValidatedRequest().readAll().get(0).getId();
        Todo updatedTodo = generateTestData(Todo.class).toBuilder().id(existingTodoId).build();

        todosRequester.getValidatedRequest().update(existingTodoId, updatedTodo);

        softly.assertThat(todosRequester.getValidatedRequest().readAll()).contains(updatedTodo);
    }

    @Test
    @PrepareTodo(1)
    @DisplayName("TC-9 Unauthorized user updates a Todo")
    public void testUpdateTodoWithoutAuth() {
        Long existingTodoId = todosRequester.getValidatedRequest().readAll().get(0).getId();
        Todo updatedTodo = generateTestData(Todo.class).toBuilder().id(existingTodoId).build();

        new TodosRequester(RequestSpec.unauthSpec()).getValidatedRequest().update(existingTodoId, updatedTodo);

        softly.assertThat(todosRequester.getValidatedRequest().readAll()).contains(updatedTodo);
    }

    @Test
    @DisplayName("TC-10 Error returned when updating a non-existent Todo")
    public void testUpdateNonExistentTodo() {
        Todo updatedTodo = generateTestData(Todo.class).toBuilder().id(0L).build();

        todosRequester.getTodosRequest().update(0L, updatedTodo)
                .then().assertThat().spec(ErrorResponse.notFound());
    }

}