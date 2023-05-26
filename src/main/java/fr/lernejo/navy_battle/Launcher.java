package fr.lernejo.navy_battle;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;
import java.util.UUID;

public class Launcher {
    public static void main(String[] args) throws Exception {
        if (args.length != 1) {
            System.out.println("Usage: java Launcher <port>");
            System.exit(-1);
        }

        int port;
        try {
            port = Integer.parseInt(args[0]);
        } catch (NumberFormatException e) {
            System.out.println("Invalid port number. Please provide a valid port number.");
            System.exit(-1);
            return;
        }

        Server server = new Server(port);
        server.start();

        if (args.length > 1) {
            String opponentUrl = args[1];
            String gameId = registerGame(opponentUrl, "http://localhost:" + port);
            startGame(opponentUrl, gameId);
        }
    }

    static class PingHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange t) throws IOException {
            String response = "OK";
            sendResponse(t, 200, response);
        }
    }

    static class GameStartHandler implements HttpHandler {
        private static final ObjectMapper mapper = new ObjectMapper();

        @Override
        public void handle(HttpExchange exchange) throws IOException {
            if (!"POST".equalsIgnoreCase(exchange.getRequestMethod())) {
                sendResponse(exchange, 404, "");
                return;
            }

            Map<String, String> requestJson;
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
    }
    private static void sendResponse(HttpExchange exchange, int status, String response) throws IOException {
        exchange.sendResponseHeaders(status, response.length());
        try (OutputStream os = exchange.getResponseBody()) {
            os.write(response.getBytes());
        }
    }

    private static String registerGame(String opponentUrl, String url) {
        // TODO: Implement the logic to register the game with the opponent and return the game ID
        return UUID.randomUUID().toString();
    }

    private static void startGame(String opponentUrl, String gameId) {
        // TODO: Implement the logic to start the game with the opponent using the game ID
    }
}

