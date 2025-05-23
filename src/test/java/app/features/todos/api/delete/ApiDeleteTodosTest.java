package app.features.todos.api.delete;

import app.common.annotations.PrepareTodo;
import app.features.todos.api.BaseApiTest;
import io.qameta.allure.Feature;
import app.features.todo.model.Todo;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import app.features.todo.request.TodosRequest;
import app.features.todo.specifications.request.RequestSpec;
import app.features.todo.specifications.response.ErrorResponse;

@Feature("API DELETE Todos Tests")
public class ApiDeleteTodosTest extends BaseApiTest {

    @Test
    @PrepareTodo(1)
    @DisplayName("TC-11 Authorized user deletes a Todo")
    public void testDeleteExistingTodoWithAuth() {
        Todo createdTodo = todosRequester.getValidatedRequest().readAll().get(0);

        todosRequester.getValidatedRequest().delete(createdTodo.getId());

        softly.assertThat(todosRequester.getValidatedRequest().readAll()).doesNotContain(createdTodo);
    }

    @Test
    @PrepareTodo(1)
    @DisplayName("TC-12 Unauthorized user cannot delete a Todo")
    public void testDeleteTodoWithoutAuthHeader() {
        Todo existingTodo = todosRequester.getValidatedRequest().readAll().get(0);

        new TodosRequest(RequestSpec.unauthSpec()).delete(existingTodo.getId())
                .then().assertThat().spec(ErrorResponse.userIsUnauthorized());

        softly.assertThat(todosRequester.getValidatedRequest().readAll()).contains(existingTodo);
    }

    @Test
    @DisplayName("TC-13 Deleting a non-existent Todo returns an error")
    public void testDeleteNonExistentTodo() {
        long nonExistentId = 0L;

        todosRequester.getTodosRequest().delete(nonExistentId)
                .then().assertThat().spec(ErrorResponse.notFound());
    }

}