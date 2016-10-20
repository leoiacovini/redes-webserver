package poli.pcs.redes.webserver;

import poli.pcs.redes.webserver.tasks.HttpServer;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {
        HttpServer httpServer = new HttpServer(8080);
        httpServer.start();
    }

}
