package poli.pcs.redes.webserver.components;

import poli.pcs.redes.webserver.components.interceptors.InterceptorResult;
import poli.pcs.redes.webserver.components.interceptors.TestLogger;
import poli.pcs.redes.webserver.http.*;
import poli.pcs.redes.webserver.utils.Logger;
import java.io.IOException;

public class Router {

    private InterceptorChain interceptorChain = new InterceptorChain(new TestLogger());

    public HttpResponse routeFileRequest(HttpRequest httpRequest) {
        try {
            return handleResponse(handleRequest(httpRequest));
        } catch (IOException e) {
            Logger.getLogger().error("Error during response");
            e.printStackTrace();
            return new HttpResponse(HttpStatusCode.SERVER_ERROR, "Error", ContentType.TEXT);
        }
    }
    
    private HttpResponse handleRequest(HttpRequest httpRequest) throws IOException {
        InterceptorResult interceptorResult = interceptorChain.processRequest(httpRequest);
        if (interceptorResult.getHttpResponse() != null) {
            return interceptorResult.getHttpResponse();
        } else {
            return HttpController.serveFile(interceptorResult.getHttpRequest());
        }
    }

    private HttpResponse handleResponse(HttpResponse httpResponse) {
        return interceptorChain.processResponse(httpResponse);
    }

}
