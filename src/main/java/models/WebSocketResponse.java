package models;

import lombok.Getter;

@Getter
public class WebSocketResponse {

    private String type;
    private Todo data;

}