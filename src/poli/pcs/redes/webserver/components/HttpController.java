package poli.pcs.redes.webserver.components;

import poli.pcs.redes.webserver.components.interceptors.AuthInterceptor;
import poli.pcs.redes.webserver.components.interceptors.InterceptorResult;
import poli.pcs.redes.webserver.http.*;
import poli.pcs.redes.webserver.http.exceptions.PageNotFoundException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class HttpController {

    private final String webRoot;

    public HttpController(String webRoot) {
        this.webRoot = webRoot;
    }

    public HttpResponse serveFile(HttpRequest httpRequest) throws IOException, PageNotFoundException {
        Path path = Paths.get(webRoot + httpRequest.getPath());
        if (Files.exists(path) && !Files.isDirectory(path)) {
            return HttpResponse.fromFile(HttpStatusCode.OK, path.toString());
        } else {
            throw new PageNotFoundException();
        }
    }

    @SuppressWarnings("unused")
    public HttpResponse serveProtectedFile(HttpRequest httpRequest) throws IOException, PageNotFoundException {
        AuthInterceptor authInterceptor = new AuthInterceptor();
        InterceptorChain interceptorChain = new InterceptorChain(authInterceptor);
        InterceptorResult interceptorResult = interceptorChain.processRequest(httpRequest);
        if (interceptorResult.hasResponse()) {
            return interceptorChain.processResponse(interceptorResult.getHttpResponse());
        } else {
            return interceptorChain.processResponse(serveFile(httpRequest));
        }
    }

}
