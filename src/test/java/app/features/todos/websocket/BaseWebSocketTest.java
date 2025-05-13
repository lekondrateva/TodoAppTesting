package app.features.todos.websocket;

import app.common.helper.WebSocketHelper;
import app.features.todos.api.BaseApiTest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

public abstract class BaseWebSocketTest extends BaseApiTest {

    protected WebSocketHelper wsHelper;

    @BeforeEach
    public void setUpWebSocket() throws Exception {
        wsHelper = new WebSocketHelper();
    }

    @AfterEach
    public void tearDownWebSocket() throws Exception {
        if (wsHelper != null) {
            wsHelper.close();
        }
    }

}