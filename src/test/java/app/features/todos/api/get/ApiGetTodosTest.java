package app.features.todos.api.get;

import app.common.annotations.PrepareTodo;
import app.common.dataGenerators.TestDataStorage;
import app.features.todos.api.BaseApiTest;
import io.qameta.allure.Feature;
import app.features.todo.model.Todo;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import app.features.todo.request.ValidatedTodosRequest;
import app.features.todo.specifications.request.RequestSpec;
import app.features.todo.specifications.response.ErrorResponse;

import java.util.List;

@Feature("API GET Todos Tests")
public class ApiGetTodosTest extends BaseApiTest {

    @Test
    @PrepareTodo(3)
    @DisplayName("TC-4 Authorized user retrieves the list of Todos")
    public void testGetTodosWithAuth() {
        List<Todo> todos = todosRequester.getValidatedRequest().readAll();

        softly.assertThat(todos).hasSize(TestDataStorage.getInstance().getStorage().size());
    }

    @Test
    @PrepareTodo(3)
    @DisplayName("TC-5 Unauthorized user retrieves the list of Todos")
    public void testGetTodosWithoutAuth() {
        List<Todo> todos = new ValidatedTodosRequest(RequestSpec.unauthSpec()).readAll();

        softly.assertThat(todos).hasSize(TestDataStorage.getInstance().getStorage().size());
    }

    @Test
    @PrepareTodo(5)
    @DisplayName("TC-6 Results are filtered by offset and limit")
    public void testGetTodosWithOffsetAndLimit() {
        List<Todo> todos = todosRequester.getValidatedRequest().readAll(0, 2);

        softly.assertThat(todos).hasSize(2);
    }

    @ParameterizedTest
    @DisplayName("TC-7 Error returned for invalid offset or limit")
    @CsvSource({
            "-1, 10",
            "10, -1"
    })
    public void testGetTodosWithInvalidOffset(int offset, int limit) {
        todosRequester.getTodosRequest().readAll(offset, limit)
                .then().assertThat().spec(ErrorResponse.invalidRequestParameters());
    }

}