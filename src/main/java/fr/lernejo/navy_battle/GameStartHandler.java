package fr.lernejo.navy_battle;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Map;
import java.util.UUID;

public class GameStartHandler implements HttpHandler {
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        if (!"POST".equalsIgnoreCase(exchange.getRequestMethod())) {
            sendResponse(exchange, 404, "");
            return;
        }

        Map<String, String> requestBody = parseRequestBody(exchange.getRequestBody());
        String id = requestBody.get("id");
        String url = requestBody.get("url");
        String message = requestBody.get("message");

        if (id == null || url == null || message == null) {
            sendResponse(exchange, 400, "");
            return;
        }

        String response = String.format("{\"id\":\"%s\", \"url\":\"%s\", \"message\":\"%s\"}",
            UUID.randomUUID().toString(), "http://localhost:" + exchange.getLocalAddress().getPort(), "May the best code win");

        sendResponse(exchange, 202, response);
    }

    private void sendResponse(HttpExchange exchange, int status, String response) throws IOException {
        exchange.sendResponseHeaders(status, response.length());
        try (OutputStream os = exchange.getResponseBody()) {
            os.write(response.getBytes());
        }
    }

    private Map<String, String> parseRequestBody(InputStream inputStream) throws IOException {
        return objectMapper.readValue(inputStream, Map.class);
    }
}


