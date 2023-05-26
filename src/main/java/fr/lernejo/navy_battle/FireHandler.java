package fr.lernejo.navy_battle;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;

class FireHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        if (!"GET".equalsIgnoreCase(exchange.getRequestMethod())) {
            sendResponse(exchange, 404, "");
            return;
        }

        String cell = exchange.getRequestURI().getQuery(); // Récupérer la valeur du paramètre de requête "cell"

        // Votre logique de jeu pour traiter le tir sur la case "cell" de l'adversaire
        // Vous devez déterminer la conséquence du tir (miss, hit, sunk) et s'il reste des bateaux sur la mer

        // Exemple de réponse JSON
        String response = "{\"consequence\":\"hit\", \"shipLeft\":true}";

        sendResponse(exchange, 200, response);
    }

    private void sendResponse(HttpExchange exchange, int status, String response) throws IOException {
        exchange.sendResponseHeaders(status, response.length());
        try (OutputStream os = exchange.getResponseBody()) {
            os.write(response.getBytes());
        }
    }
}

