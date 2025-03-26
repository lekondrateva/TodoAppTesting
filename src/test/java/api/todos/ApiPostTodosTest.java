package api.todos;

import api.BaseApiTest;
import io.qameta.allure.Description;
import models.Todo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import requests.TodosRequest;
import specifications.request.RequestSpec;
import specifications.response.ErrorResponse;

import java.util.stream.Stream;

import static dataGenerators.TestDataGenerator.generateTestData;

public class ApiPostTodosTest extends BaseApiTest {

    @Test
    @Description("Авторизованный пользователь может создать задачу")
    public void testCreateTodoWithValidAuth() {
        Todo newTodo = generateTestData(Todo.class);
        todosRequester.getValidatedRequest().create(newTodo);

        softly.assertThat(todosRequester.getValidatedRequest().readAll()).contains(newTodo);
    }

    @Test
    @Description("Неавторизованный пользователь не может создать задачу и получает ошибку 401")
    public void testCreateTodoWithoutAuthHeader() {
        Todo newTodo = generateTestData(Todo.class);

        new TodosRequest(RequestSpec.unauthSpec()).create(newTodo)
                .then().assertThat().spec(ErrorResponse.userIsUnauthorized());
    }

    @ParameterizedTest
    @MethodSource("provideTodo")
    @Description("Если отправить пустое поле text/id, сервер возвращает ошибку 400")
    public void testCreateTodoWithEmptyBody(Todo newTodo) {
        new TodosRequest(RequestSpec.authSpecAsAdmin()).create(newTodo)
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