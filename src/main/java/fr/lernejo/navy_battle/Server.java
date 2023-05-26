package fr.lernejo.navy_battle;

import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

public class Server {
    private final HttpServer server;

    public Server(int port) {
        try {
            server = HttpServer.create(new InetSocketAddress(port), 0);
            server.setExecutor(Executors.newFixedThreadPool(1));
        } catch (IOException e) {
            throw new RuntimeException("Server could not be created on port " + port, e);
        }

        initializeContexts();
    }

    private void initializeContexts() {
        server.createContext("/ping", new Launcher.PingHandler());
        server.createContext("/api/game/start", new Launcher.GameStartHandler());
        server.createContext("/api/game/fire", new FireHandler());
    }

    public void start() {
        server.start();
    }

    public void stop() {
        server.stop(0);
    }
}

