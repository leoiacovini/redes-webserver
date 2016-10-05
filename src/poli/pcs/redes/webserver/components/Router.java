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

    private HttpRequest httpRequest;

    public Router(HttpRequest httpRequest) {
        this.httpRequest = httpRequest;
    }

    public HttpResponse routeFileRequest() {
        try {
            return handleRequest();
        } catch (IOException e) {
            Logger.getLogger().error("Error during response");
            e.printStackTrace();
            return new HttpResponse(500, "Error", ContentType.TEXT);
        }
    }
    
    private HttpResponse handleRequest() throws IOException {
        Path path = Paths.get(httpRequest.getPath());
        if (Files.exists(path)) {
            return HttpResponse.fromFile(200, httpRequest.getPath());
        } else if (httpRequest.getPath().equals("/")) {
            return HttpResponse.fromFile(200, "index.html");
        } else {
            String notFoundPageContent = String.join("\n", Files.readAllLines(Paths.get("webfiles/", "html/404.html")));
            return new HttpResponse(404, notFoundPageContent, ContentType.HTML);
        }
    }

}
