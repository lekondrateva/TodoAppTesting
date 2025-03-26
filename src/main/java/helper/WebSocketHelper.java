package helper;

import configuration.Configuration;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;
import java.util.concurrent.CountDownLatch;

public class WebSocketHelper {
    private WebSocketClient client;
    private final CountDownLatch messageLatch = new CountDownLatch(1);
    private String receivedMessage;

    public WebSocketHelper() throws Exception {
        String wsUrl = Configuration.getProperty("ws.url");
        client = new WebSocketClient(new URI(wsUrl)) {
            @Override
            public void onOpen(ServerHandshake handshake) {
            }

            @Override
            public void onMessage(String message) {
                receivedMessage = message;
                messageLatch.countDown();
            }

            @Override
            public void onClose(int code, String reason, boolean remote) {
            }

            @Override
            public void onError(Exception ex) {
                throw new RuntimeException("WebSocket error", ex);
            }
        };
        client.connectBlocking();
    }

    public String waitForMessage(int timeoutSeconds) throws InterruptedException {
        messageLatch.await(timeoutSeconds, java.util.concurrent.TimeUnit.SECONDS);
        return receivedMessage;
    }

    public void close() throws InterruptedException {
        client.closeBlocking();
    }
}
