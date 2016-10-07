package poli.pcs.redes.webserver.http;

public class HttpRequest {

    private String stringBody;
    private HttpRequestHeader httpRequestHeader;

    public HttpRequest(String rawRequest) {
        String[] requestParts = rawRequest.split("^\\s*$");
        stringBody = requestParts[requestParts.length-1];
        httpRequestHeader = new HttpRequestHeader(requestParts[0]);
    }

    public void appendToPath(String part) {
        this.httpRequestHeader.setPath(this.getPath() + part);
    }

    public HttpRequestHeader getHttpRequestHeader() {
        return httpRequestHeader;
    }

    public String getStringBody() {
        return stringBody;
    }

    public HttpMethod getMethod() {
        return httpRequestHeader.getMethod();
    }

    public String getPath() {
        return httpRequestHeader.getPath();
    }

    public HttpHeaders getHeaders() {
        return httpRequestHeader.getHeaders();
    }
}
