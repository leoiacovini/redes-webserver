package poli.pcs.redes.webserver.http;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class HttpResponse {

    private HttpStatusCode statusCode;
    private HttpHeaders headers;
    private String body;
    private byte[] byteBody;

    public HttpResponse(HttpStatusCode statusCode, String body, ContentType contentType) {
        this.statusCode = statusCode;
        this.body = body;
        this.byteBody = body.getBytes();
        this.headers = new HttpHeaders();
        if (contentType != ContentType.OTHER) {
            this.headers.put("Content-Type", contentType.toString());
        }
    }

    private HttpResponse(HttpStatusCode statusCode, byte[] body, ContentType contentType) {
        this.statusCode = statusCode;
        this.byteBody = body;
        this.headers = new HttpHeaders();
        if (contentType != ContentType.OTHER) {
            this.headers.put("Content-Type", contentType.toString());
        }
    }

    public static HttpResponse fromFile(HttpStatusCode statusCode, String filePath) throws IOException {
        Path path = Paths.get("webfiles/", filePath);
        ContentType contentType = ContentType.formFileName(filePath);
        return new HttpResponse(statusCode, Files.readAllBytes(path), contentType);
    }

    public byte[] renderResponseBytes() {
        return concatHeaderBody(renderHeader());
    }

    private byte[] renderHeader() {
        int contentLength = byteBody.length;
        headers.put("Content-Length", String.valueOf(contentLength));
        return ("HTTP/1.0" + statusCode.toCode() + " " + statusCode.toString() + "\n" + headers.toString() + "\r\n").getBytes();
    }

    private byte[] concatHeaderBody(byte[] head) {
        byte[] responseBytes = new byte[head.length + byteBody.length];
        System.arraycopy(head, 0, responseBytes, 0, head.length);
        System.arraycopy(byteBody, 0, responseBytes, head.length, byteBody.length);
        return responseBytes;
    }

}
