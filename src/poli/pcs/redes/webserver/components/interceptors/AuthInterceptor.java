package poli.pcs.redes.webserver.components.interceptors;

import poli.pcs.redes.webserver.http.ContentType;
import poli.pcs.redes.webserver.http.HttpRequest;
import poli.pcs.redes.webserver.http.HttpResponse;
import poli.pcs.redes.webserver.http.HttpStatusCode;

public class AuthInterceptor implements Interceptor {
    @Override
    public InterceptorResult handleRequest(HttpRequest httpRequest) {

        String authString = httpRequest.getHeaders().get("Authorization");
        if (authString != null) {

        }
        HttpResponse forbiddenResponse = new HttpResponse(HttpStatusCode.UNAUTHORIZED, "UNAUTHORIZED", ContentType.TEXT);
        return new InterceptorResult(forbiddenResponse);
    }
}
