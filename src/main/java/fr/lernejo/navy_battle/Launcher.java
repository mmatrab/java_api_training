package fr.lernejo.navy_battle;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.Executors;

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

        HttpServer server = HttpServer.create(new InetSocketAddress(port), 0);
        server.createContext("/ping", new PingHandler());
        server.createContext("/api/game/start", new GameStartHandler());
        server.setExecutor(Executors.newFixedThreadPool(1)); // creates a default executor
        server.start();
    }

    static class PingHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange t) throws IOException {
            String response = "OK";
            t.sendResponseHeaders(200, response.length());
            OutputStream os = t.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }
    }
    static class GameStartHandler implements HttpHandler {
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
}
