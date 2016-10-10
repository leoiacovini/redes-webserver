package poli.pcs.redes.webserver.components.interceptors;

import poli.pcs.redes.webserver.components.Config;
import poli.pcs.redes.webserver.http.ContentType;
import poli.pcs.redes.webserver.http.HttpRequest;
import poli.pcs.redes.webserver.http.HttpResponse;
import poli.pcs.redes.webserver.http.HttpStatusCode;

import java.util.Base64;

public class AuthInterceptor implements Interceptor {
    @Override
    public InterceptorResult handleRequest(HttpRequest httpRequest) {

        try {
            String authString = httpRequest.getHeaders().get("Authorization");
            String[] splitedAuth = authString.split(" ");
            String authType = splitedAuth[0];
            if (authType.equals("Basic")) {
                String decodedAuth = new String(Base64.getDecoder().decode(splitedAuth[1]));
                String userName = decodedAuth.split(":")[0];
                String password = decodedAuth.split(":")[1];
                String secureUsername = Config.getDefaultConfig().get("secureUsername");
                String securePassword = Config.getDefaultConfig().get("securePassword");
                if (userName.equals(secureUsername) && password.equals(securePassword)) {
                    return new InterceptorResult(httpRequest);
                } else {
                    throw new IllegalArgumentException();
                }
            } else {
                throw new IllegalArgumentException();
            }
        } catch (Exception e) {
            HttpResponse forbiddenResponse = new HttpResponse(HttpStatusCode.UNAUTHORIZED, "UNAUTHORIZED", ContentType.TEXT);
            forbiddenResponse.getHeaders().put("WWW-Authenticate", "Basic realm=User Visible Realm");
            return new InterceptorResult(forbiddenResponse);
        }
    }
}
