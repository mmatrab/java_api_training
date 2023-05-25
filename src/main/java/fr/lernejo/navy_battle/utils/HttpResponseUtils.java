package fr.lernejo.navy_battle.utils;

import java.io.IOException;
import java.io.OutputStream;
import com.sun.net.httpserver.HttpExchange;

public class HttpResponseUtils {
    public static void sendResponse(HttpExchange exchange, int status, String response) throws IOException {
        try {
            exchange.sendResponseHeaders(status, response.length());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try (OutputStream os = exchange.getResponseBody()) {
            os.write(response.getBytes());
        }
    }
}
