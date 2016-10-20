package poli.pcs.redes.webserver.tasks;

import poli.pcs.redes.webserver.utils.Logger;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class HttpServer {

    private Logger logger;
    private ExecutorService threadPool;
    private final ServerSocket serverSocket;

    public HttpServer(int port) throws IOException {
        this.logger = Logger.getLogger();
        this.threadPool = Executors.newCachedThreadPool();
        serverSocket = new ServerSocket(port);
    }

    public void start() throws IOException {
        logger.info("Starting server");
        logger.info("Server started at port 8080");
        logger.info("Accepting connections...");
        while (!serverSocket.isClosed()) {
            Socket socket = serverSocket.accept();
            logger.info("Connection Accepted at port: " + socket.getPort());
            ServerTask serverTask = new ServerTask(socket);
            threadPool.execute(serverTask);
        }
    }

}
