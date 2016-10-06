package poli.pcs.redes.webserver.http;

public class HttpRequestHeader {

    private HttpMethod method;
    private String path;
    private HttpHeaders headers;

    public HttpRequestHeader(String rawHttpRequestHeader) {
        String[] requestInfoLine =  rawHttpRequestHeader.split("\n")[0].split(" ");
        method = HttpMethod.fromString(requestInfoLine[0]);
        path = requestInfoLine[1];
        headers = HttpHeaders.parseHttpRequestHeader(rawHttpRequestHeader);
    }

    public HttpMethod getMethod() {
        return method;
    }

    public String getPath() {
        return path;
    }

    public HttpHeaders getHeaders() {
        return headers;
    }
}
