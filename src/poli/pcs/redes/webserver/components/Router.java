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
            return dispatchRequest(httpRequest);
        } catch (IOException e) {
            Logger.getLogger().error("Error during response");
            e.printStackTrace();
            return new HttpResponse(HttpStatusCode.SERVER_ERROR, "Error", ContentType.TEXT);
        }
    }
    
    private HttpResponse dispatchRequest(HttpRequest httpRequest) throws IOException {
        InterceptorResult interceptorResult = interceptorChain.processRequest(httpRequest);
        HttpResponse httpResponse ;
        if (interceptorResult.getHttpResponse() != null) {
            httpResponse = interceptorResult.getHttpResponse();
        } else {
            httpResponse = HttpController.serveFile(interceptorResult.getHttpRequest());
        }
        return interceptorChain.processResponse(httpResponse);
    }


}
