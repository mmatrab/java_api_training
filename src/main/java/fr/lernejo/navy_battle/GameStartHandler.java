package fr.lernejo.navy_battle;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;
import java.util.UUID;

public class GameStartHandler implements HttpHandler { // Partie 2
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        if (!"POST".equalsIgnoreCase(exchange.getRequestMethod())) {
            sendResponse(exchange, 404, "");
            return;
        }

        ObjectMapper mapper = new ObjectMapper();
        Map requestJson;

        try {
            requestJson = mapper.readValue(exchange.getRequestBody(), Map.class);
            if (!requestJson.containsKey("id") || !requestJson.containsKey("url") || !requestJson.containsKey("message")) {
                throw new IllegalArgumentException();
            }
        } catch (Exception e) {
            sendResponse(exchange, 400, "");
            return;
        }

        Map<String, String> responseJson = Map.of(
            "id", UUID.randomUUID().toString(),
            "url", "http://localhost:" + exchange.getLocalAddress().getPort(),
            "message", "May the best code win"
        );

        sendResponse(exchange, 202, mapper.writeValueAsString(responseJson));
    }

    private void sendResponse(HttpExchange exchange, int status, String response) throws IOException {
        exchange.sendResponseHeaders(status, response.length());
        OutputStream os = exchange.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }
}
