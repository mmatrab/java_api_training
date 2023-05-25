package fr.lernejo.navy_battle.handle;

import fr.lernejo.navy_battle.utils.HttpResponseUtils;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import java.io.IOException;

public class PingHandler implements HttpHandler  {
    public void handle(HttpExchange exchange) throws IOException {
        final String response = "OK";
        HttpResponseUtils.sendResponse(exchange, 200, response);
    }
}

