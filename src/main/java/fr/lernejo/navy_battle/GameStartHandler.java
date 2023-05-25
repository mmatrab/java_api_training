package fr.lernejo.navy_battle;

import fr.lernejo.navy_battle.utils.HttpResponseUtils;
import fr.lernejo.navy_battle.utils.Json;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import java.io.IOException;
import java.util.Map;
import java.util.UUID;

public class GameStartHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        if (!"POST".equalsIgnoreCase(exchange.getRequestMethod())) {
            HttpResponseUtils.sendResponse(exchange, 404, "");
            return;
        }

        try {
            Json requestJson = Json.fromJson(exchange.getRequestBody().toString());
        } catch (IOException e) {
            HttpResponseUtils.sendResponse(exchange, 400, "");
            return;
        }

        Map<String, String> responseJson = Map.of(
            "id", UUID.randomUUID().toString(),
            "url", "http://localhost:" + exchange.getLocalAddress().getPort(),
            "message", "May the best code win"
        );

        ObjectMapper mapper = new ObjectMapper();
        HttpResponseUtils.sendResponse(exchange, 202, mapper.writeValueAsString(responseJson));
    }
}
