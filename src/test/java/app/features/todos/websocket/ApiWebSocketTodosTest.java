package app.features.todos.websocket;

import io.qameta.allure.Feature;
import io.qameta.allure.internal.shadowed.jackson.databind.ObjectMapper;
import app.features.todo.model.Todo;
import app.features.todo.websocket.WebSocketResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static app.common.dataGenerators.TestDataGenerator.generateTestData;
import static org.assertj.core.api.Assertions.assertThat;

@Feature("WS Todos Tests")
public class ApiWebSocketTodosTest extends BaseWebSocketTest {

    @Test
    @DisplayName("TC-14 WebSocket receives a message with the created task")
    public void testWebSocketReceivesNewTodoMessage() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        Todo newTodo = generateTestData(Todo.class);

        todosRequester.getValidatedRequest().create(newTodo);

        String message = wsHelper.waitForMessage(5);

        WebSocketResponse response = objectMapper.readValue(message, WebSocketResponse.class);
        assertThat(newTodo).isEqualTo(response.getData());
    }

}