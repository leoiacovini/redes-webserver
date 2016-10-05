package poli.pcs.redes.webserver.components;

import poli.pcs.redes.webserver.http.ContentType;
import poli.pcs.redes.webserver.http.HttpRequest;
import poli.pcs.redes.webserver.http.HttpResponse;
import poli.pcs.redes.webserver.utils.Logger;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Router {

    public Router() {

    }

    public HttpResponse routeFileRequest(HttpRequest httpRequest) {
        try {
            return handleRequest(httpRequest);
        } catch (IOException e) {
            Logger.getLogger().error("Error during response");
            e.printStackTrace();
            return new HttpResponse(500, "Error", ContentType.TEXT);
        }
    }
    
    private HttpResponse handleRequest(HttpRequest httpRequest) throws IOException {
        Path path = Paths.get("webfiles" + httpRequest.getPath());
        if (Files.exists(path) && !Files.isDirectory(path)) {
            return HttpResponse.fromFile(200, httpRequest.getPath());
        } else if (httpRequest.getPath().toCharArray()[httpRequest.getPath().length() - 1] == '/') {
            return HttpResponse.fromFile(200, httpRequest.getPath() + "index.html");
        } else {
            Logger.getLogger().warn("Page not found: " + httpRequest.getPath());
            String notFoundPageContent = String.join("\n", Files.readAllLines(Paths.get("webfiles", "/html/404.html")));
            return new HttpResponse(404, notFoundPageContent, ContentType.HTML);
        }
    }

}
