package fr.lernejo.navy_battle;

import com.sun.net.httpserver.HttpExchange;
import java.io.IOException;
import java.io.OutputStream;

public class HttpResponseUtils {
    public static void sendResponse(HttpExchange exchange, int status, String response) throws IOException {
        exchange.sendResponseHeaders(status, response.length());
        try (OutputStream os = exchange.getResponseBody()) {
            os.write(response.getBytes());
        }
    }
}

