package app.features.todo.websocket;

import lombok.Getter;
import app.features.todo.model.Todo;

@Getter
public class WebSocketResponse {

    private String type;
    private Todo data;

}