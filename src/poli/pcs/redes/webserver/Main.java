package poli.pcs.redes.webserver;

import poli.pcs.redes.webserver.tasks.ServerTask;
import poli.pcs.redes.webserver.utils.Logger;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {

    static private Logger logger;

    public static void main(String[] args) {
        ExecutorService threadPool = Executors.newCachedThreadPool();
        try {
            logger = Logger.getLogger();
            logger.info("Starting server");
            ServerSocket serverSocket = new ServerSocket(8080);
            logger.info("Server started at port 8080");
            logger.info("Accepting connections...");
            while (!serverSocket.isClosed()) {
                Socket socket = serverSocket.accept();
                logger.info("Connection Accepted at port: " + socket.getPort());
                ServerTask serverTask = new ServerTask(socket);
                threadPool.execute(serverTask);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
