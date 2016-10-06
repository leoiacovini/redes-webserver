package poli.pcs.redes.webserver.components.interceptors;

import poli.pcs.redes.webserver.http.HttpRequest;
import poli.pcs.redes.webserver.http.HttpResponse;
import poli.pcs.redes.webserver.utils.Logger;

public class TestLogger implements Interceptor {
    @Override
    public InterceptorResult handleRequest(HttpRequest httpRequest) {
        Logger.getLogger().debug("INTECEPTOR LOGGER");
        return new InterceptorResult(httpRequest);
    }

    @Override
    public HttpResponse handleResponse(HttpResponse httpResponse) {
        return httpResponse;
    }
}
