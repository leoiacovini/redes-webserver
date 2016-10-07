package poli.pcs.redes.webserver.http;

public class Route {

    private String httpControllerClass;
    private String httpHandlerName;
    private String routePath;

    public Route(String httpControllerClass, String httpHandlerName, String routePath) {
        this.httpControllerClass = httpControllerClass;
        this.httpHandlerName = httpHandlerName;
        this.routePath = routePath;
    }

    public String getHttpControllerClass() {
        return httpControllerClass;
    }

    public String getHttpHandlerName() {
        return httpHandlerName;
    }

    public String getRoutePath() {
        return routePath;
    }
}
