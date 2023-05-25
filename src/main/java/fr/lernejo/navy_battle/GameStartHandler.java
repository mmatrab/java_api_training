package fr.lernejo.navy_battle;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public class GameStartHandler implements HttpHandler {
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        if (!"POST".equalsIgnoreCase(exchange.getRequestMethod())) {
            HttpResponseUtils.sendResponse(exchange, 404, "");
            return;
        }

        Map<String, String> requestBody = parseRequestBody(exchange.getRequestBody());
        String id = requestBody.get("id");
        String url = requestBody.get("url");
        String message = requestBody.get("message");

        if (id == null || url == null || message == null) {
            HttpResponseUtils.sendResponse(exchange, 400, "");
            return;
        }

        String response = String.format("{\"id\":\"%s\", \"url\":\"%s\", \"message\":\"%s\"}",
            "2aca7611-0ae4-49f3-bf63-75bef4769028", "http://localhost:9876", "May the best code win");

        HttpResponseUtils.sendResponse(exchange, 404, Integer.toString(-1));
        try (OutputStream os = exchange.getResponseBody()) {
            os.write(response.getBytes());
        }
    }

    private Map<String, String> parseRequestBody(InputStream inputStream) throws IOException {
        return objectMapper.readValue(inputStream, Map.class);
    }
}

