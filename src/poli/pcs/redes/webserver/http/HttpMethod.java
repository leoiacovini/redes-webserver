package poli.pcs.redes.webserver.http;

public enum HttpMethod {
    GET, POST, HEAD, PATCH, PUT, DELETE;

    static public HttpMethod fromString(String methodName) {
        switch (methodName) {
            case "GET": return GET;
            case "POST": return POST;
            case "HEAD": return HEAD;
            case "PUT": return PUT;
            case "PATCH": return PATCH;
            case "DELETE": return DELETE;
            default: return GET;
        }
    }
}
