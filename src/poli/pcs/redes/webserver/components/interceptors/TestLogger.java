package poli.pcs.redes.webserver.components.interceptors;

import poli.pcs.redes.webserver.http.HttpRequest;
import poli.pcs.redes.webserver.utils.Logger;

public class TestLogger implements Interceptor {
    @Override
    public InterceptorResult handleRequest(HttpRequest httpRequest) {
        Logger.getLogger().debug("INTERCEPTOR LOGGER");
        return new InterceptorResult(httpRequest);
    }
}
