package api.todos;

import api.BaseApiTest;
import helper.WebSocketHelper;
import io.qameta.allure.Description;
import io.qameta.allure.internal.shadowed.jackson.databind.ObjectMapper;
import models.Todo;
import models.WebSocketResponse;
import org.junit.jupiter.api.Test;

import static dataGenerators.TestDataGenerator.generateTestData;
import static org.assertj.core.api.Assertions.assertThat;

public class ApiWebSocketTodosTest extends BaseApiTest {

    @Test
    @Description("После создания задачи через REST WebSocket получает сообщение о новой задаче")
    public void testWebSocketReceivesNewTodoMessage() throws Exception {
        WebSocketHelper wsHelper = new WebSocketHelper();
        ObjectMapper objectMapper = new ObjectMapper();

        Todo newTodo = generateTestData(Todo.class);
        todosRequester.getValidatedRequest().create(newTodo);

        String message = wsHelper.waitForMessage(5);
        wsHelper.close();

        WebSocketResponse response = objectMapper.readValue(message, WebSocketResponse.class);
        assertThat(newTodo).isEqualTo(response.getData());
    }

}
