package poli.pcs.redes.webserver.components;

import poli.pcs.redes.webserver.components.interceptors.Interceptor;
import poli.pcs.redes.webserver.components.interceptors.InterceptorResult;
import poli.pcs.redes.webserver.http.HttpRequest;
import poli.pcs.redes.webserver.http.HttpResponse;

import java.util.Arrays;
import java.util.List;


public class InterceptorChain {

    private List<Interceptor> interceptorList;

    public InterceptorChain(Interceptor... interceptors) {
        this.interceptorList = Arrays.asList(interceptors);
    }

    public InterceptorResult processRequest(HttpRequest httpRequest) {
        InterceptorResult interceptorResult = null;
        HttpRequest interceptedRequest = httpRequest;
        for (Interceptor interceptor : interceptorList) {
            interceptorResult = interceptor.handleRequest(interceptedRequest);
            if (interceptorResult.getHttpResponse() != null) {
                return interceptorResult;
            } else {
                interceptedRequest = interceptorResult.getHttpRequest();
            }
        }
        return interceptorResult;
    }

    public HttpResponse processResponse(HttpResponse httpResponse) {
        HttpResponse interceptedResponse = httpResponse;
        for (Interceptor interceptor : interceptorList) {
            interceptedResponse = interceptor.handleResponse(interceptedResponse);
        }
        return interceptedResponse;
    }

}
