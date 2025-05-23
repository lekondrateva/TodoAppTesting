package app.features.todos.api.put;

import app.common.annotations.PrepareTodo;
import app.features.todos.api.BaseApiTest;
import io.qameta.allure.Feature;
import app.features.todo.model.Todo;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import app.features.todo.requester.TodosRequester;
import app.features.todo.specifications.request.RequestSpec;
import app.features.todo.specifications.response.ErrorResponse;

import static app.common.dataGenerators.TestDataGenerator.generateTestData;

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