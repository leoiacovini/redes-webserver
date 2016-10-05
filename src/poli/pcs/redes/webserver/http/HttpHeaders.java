package poli.pcs.redes.webserver.http;

import java.util.HashMap;

public class HttpHeaders {

    private HashMap<String, String> headersMap = new HashMap<>();

    static HttpHeaders parseHttpRequestHeader(String rawRequestHeader) {
        HttpHeaders httpHeaders = new HttpHeaders();
        String[] headerParts = rawRequestHeader.split("\n");
        for (int i = 1; i < headerParts.length; i++) {
            String[] kvHeader = headerParts[i].split(": ");
            httpHeaders.put(kvHeader[0], kvHeader[1]);
        }
        return httpHeaders;
    }

    public String get(String key) {
        return headersMap.get(key);
    }

    public void put(String key, String value) {
        headersMap.put(key, value);
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (HashMap.Entry<String, String> entry : headersMap.entrySet()) {
            stringBuilder.append(entry.getKey())
                    .append(": ")
                    .append(entry.getValue())
                    .append("\n");
        }
        return stringBuilder.toString();
    }

}
