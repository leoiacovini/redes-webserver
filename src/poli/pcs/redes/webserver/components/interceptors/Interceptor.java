package poli.pcs.redes.webserver.components.interceptors;

import poli.pcs.redes.webserver.http.HttpRequest;
import poli.pcs.redes.webserver.http.HttpResponse;

public interface Interceptor {
    default InterceptorResult handleRequest(HttpRequest httpRequest) {
        return new InterceptorResult(httpRequest);
    };
    default HttpResponse handleResponse(HttpResponse httpResponse) {
        return httpResponse;
    };
}

