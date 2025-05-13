package app.features.todos.api.post;

import app.features.todos.api.BaseApiTest;
import io.qameta.allure.Feature;
import app.features.todo.model.Todo;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import app.features.todo.requester.TodosRequester;
import app.features.todo.specifications.request.RequestSpec;
import app.features.todo.specifications.response.ErrorResponse;

import java.util.stream.Stream;

import static app.common.dataGenerators.TestDataGenerator.generateTestData;

@Feature("API POST Todos Tests")
public class ApiPostTodosTest extends BaseApiTest {

    @Test
    @DisplayName("TC-1 Authorized user creates a Todo")
    public void testCreateTodoWithValidAuth() {
        Todo newTodo = generateTestData(Todo.class);

        todosRequester.getValidatedRequest().create(newTodo);

        softly.assertThat(todosRequester.getValidatedRequest().readAll()).contains(newTodo);
    }

    @Test
    @DisplayName("TC-2 Unauthorized user creates a Todo")
    public void testCreateTodoWithoutAuth() {
        Todo newTodo = generateTestData(Todo.class);

        new TodosRequester(RequestSpec.unauthSpec()).getValidatedRequest().create(newTodo);

        softly.assertThat(todosRequester.getValidatedRequest().readAll()).contains(newTodo);
    }

    @ParameterizedTest
    @MethodSource("provideTodo")
    @DisplayName("TC-3 Todo with empty 'text' or 'id' fields is not created")
    public void testCreateTodoWithEmptyParameters(Todo newTodo) {
        todosRequester.getTodosRequest().create(newTodo)
                .then().assertThat().spec(ErrorResponse.invalidRequestParameters());
    }

    static Stream<Todo> provideTodo() {
        Todo initTodo = generateTestData(Todo.class);

        return Stream.of(
                initTodo.toBuilder()
                        .id(null)
                        .build(),
                initTodo.toBuilder()
                        .text(null)
                        .build());
    }

}