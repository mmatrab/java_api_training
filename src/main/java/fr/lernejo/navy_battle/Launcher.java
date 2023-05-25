package fr.lernejo.navy_battle;


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
        Server server = new Server();
        server.start(port);
    }
}
