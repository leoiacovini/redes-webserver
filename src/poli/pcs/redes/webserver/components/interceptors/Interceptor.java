package poli.pcs.redes.webserver.components.interceptors;

import poli.pcs.redes.webserver.http.HttpRequest;
import poli.pcs.redes.webserver.http.HttpResponse;

public interface Interceptor {
    public InterceptorResult handleRequest(HttpRequest httpRequest);
    public HttpResponse handleResponse(HttpResponse httpResponse);
}

