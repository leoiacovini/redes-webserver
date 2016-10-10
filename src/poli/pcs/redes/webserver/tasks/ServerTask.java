package poli.pcs.redes.webserver.tasks;

import poli.pcs.redes.webserver.components.Router;
import poli.pcs.redes.webserver.http.HttpRequest;
import poli.pcs.redes.webserver.http.HttpResponse;
import poli.pcs.redes.webserver.utils.Logger;

import java.io.*;
import java.net.Socket;
import java.util.UUID;

public class ServerTask implements Runnable {

    private Socket socket;
    private PrintStream printStream;
    private BufferedReader bufferedReader;
    private Logger logger;
    private String requestID;
    private Router router;

    public ServerTask(Socket socket) {
        this.logger = Logger.getLogger();
        this.socket = socket;
        this.requestID = UUID.randomUUID().toString().split("-")[0].toUpperCase();
        logger.info(socket.getInetAddress() + " connected to port " + socket.getPort() + " assigned to ID: " + requestID);
    }

    @Override
    public void run() {
        try {
            setupIO();
            this.router = new Router();
            while (!socket.isClosed()) {
                String request = readRequestString();
                if (!request.isEmpty()) {
                    handleRequest(request);
                    closeIO();
                }
            }
            logger.info("Connection closed - ID: "  + requestID);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void closeIO() throws IOException {
        bufferedReader.close();
        printStream.close();
        socket.close();
    }

    private void handleRequest(String request) throws IOException {
        HttpRequest req = new HttpRequest(request);
        logger.info(req.getMethod() + " Request Received: " + req.getPath() + " - ID: " + requestID);
        HttpResponse httpResponse = router.routeRequest(req);
        writeHttpResponse(httpResponse);
    }

    private void setupIO() throws IOException {
        bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        printStream = new PrintStream(socket.getOutputStream());
    }

    private void writeHttpResponse(HttpResponse httpResponse) throws IOException {
        printStream.write(httpResponse.renderResponseBytes());
    }

    private String readRequestString() throws IOException {
        StringBuilder stringBuilder = new StringBuilder();
        while (bufferedReader.ready()) {
            String nexLine = bufferedReader.readLine();
            stringBuilder.append(nexLine).append("\n");
        }
        return stringBuilder.toString();
    }

}
