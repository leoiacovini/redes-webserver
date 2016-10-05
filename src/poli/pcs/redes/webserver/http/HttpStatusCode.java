package poli.pcs.redes.webserver.http;

public enum HttpStatusCode {
    OK, BAD_REQUEST, NOT_FOUND, UNAUTHORIZED, SERVER_ERROR;

    public String toString() {
        switch (this) {
            case OK: return "OK";
            case NOT_FOUND: return "NOT FOUND";
            case BAD_REQUEST: return "BAD REQUEST";
            case UNAUTHORIZED: return "UNAUTHORIZED";
            case SERVER_ERROR: return "SERVER ERROR";
            default: return "UNKNOWN";
        }
    }

    public int toCode() {
        switch (this) {
            case OK: return 202;
            case NOT_FOUND: return 404;
            case BAD_REQUEST: return 400;
            case UNAUTHORIZED: return 403;
            case SERVER_ERROR: return 500;
            default: return 500;
        }

    }
}
