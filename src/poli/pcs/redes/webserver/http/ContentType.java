package poli.pcs.redes.webserver.http;

import poli.pcs.redes.webserver.utils.Logger;

public enum ContentType {
    TEXT, HTML, JPG, PNG, JSON, XML, CSS, JS, OTHER;

    static public ContentType formFileName(String fileName) {
        String[] fileParts = fileName.split("\\.");
        String extension = fileParts[fileParts.length - 1];
        switch (extension.toLowerCase()) {
            case "html": return HTML;
            case "xml": return XML;
            case "jpg": return JPG;
            case "png": return PNG;
            case "json": return JSON;
            case "css": return CSS;
            case "js": return JS;
            default: {
                Logger.getLogger().warn("File type not inferred");
                return OTHER;
            }
        }
    }

    public String toString() {
        switch (this) {
            case TEXT: return "text/plain; charset=utf-8";
            case HTML: return "text/html";
            case JPG: return "image/jpeg";
            case PNG: return "image/png";
            case JSON: return "application/json";
            case XML: return "application/xml";
            case JS: return "text/javascript";
            case CSS: return "text/css";
            default: return "";
        }
    }
}
