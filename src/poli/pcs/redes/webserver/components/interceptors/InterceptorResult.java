package poli.pcs.redes.webserver.components.interceptors;

import poli.pcs.redes.webserver.http.HttpRequest;
import poli.pcs.redes.webserver.http.HttpResponse;

public class InterceptorResult {

    private HttpRequest httpRequest;
    private HttpResponse httpResponse;

    public InterceptorResult(HttpResponse httpResponse) {
        this.httpRequest = null;
        this.httpResponse = httpResponse;
    }

    public InterceptorResult(HttpRequest httpRequest) {
        this.httpRequest = httpRequest;
        this.httpResponse = null;
    }

    public HttpRequest getHttpRequest() {
        return httpRequest;
    }

    public HttpResponse getHttpResponse() {
        return httpResponse;
    }

    public boolean hasResponse() {
        return this.httpResponse != null;
    }
}
