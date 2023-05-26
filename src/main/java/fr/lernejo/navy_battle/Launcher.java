package fr.lernejo.navy_battle;

import com.sun.net.httpserver.HttpServer;
import java.net.InetSocketAddress;
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
}
