package poli.pcs.redes.webserver.components;

import poli.pcs.redes.webserver.http.*;
import poli.pcs.redes.webserver.http.exceptions.NotFoundException;
import poli.pcs.redes.webserver.http.exceptions.RouteNotFoundException;
import poli.pcs.redes.webserver.utils.Logger;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Optional;

public class Router {

    private Config config = Config.getDefaultConfig();

    public HttpResponse routeRequest(HttpRequest httpRequest) {
        try {
            prepareRequest(httpRequest);
            return dispatchRequest(httpRequest);
        } catch (NotFoundException rNfE) {
            Logger.getLogger().warn("Not found " + httpRequest.getPath());
            String notFoundPageContent = "404";
            return new HttpResponse(HttpStatusCode.NOT_FOUND, notFoundPageContent, ContentType.TEXT);
        } catch (Exception e) {
            Logger.getLogger().error("Error during response");
            e.printStackTrace();
            return new HttpResponse(HttpStatusCode.SERVER_ERROR, "Error", ContentType.TEXT);
        }
    }
    
    private HttpResponse dispatchRequest(HttpRequest httpRequest)
            throws IllegalAccessException, NotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, ClassNotFoundException {
        String webRoot = getVirtualHostWebRoot(httpRequest);
        Route matchedRoute = getRouteForPath(httpRequest.getPath());
        String controllerName = matchedRoute.getHttpControllerClass();
        Class<?> httpControllerClass = Class.forName(controllerName);
        Constructor<?> constructor = httpControllerClass.getConstructor(String.class);
        Object httpController = constructor.newInstance(webRoot);
        Method handler = httpController.getClass().getMethod(matchedRoute.getHttpHandlerName(), HttpRequest.class);
        Logger.getLogger().debug("Calling: " + handler);
        try {
            return (HttpResponse) handler.invoke(httpController, httpRequest);
        } catch (InvocationTargetException e) {
            if (e.getCause() instanceof NotFoundException) {
                throw (NotFoundException) e.getCause();
            } else {
                throw e;
            }
        }
    }

    private String getVirtualHostWebRoot(HttpRequest httpRequest) {
        String virtualHost = httpRequest.getHeaders().get("Host");
        String domainName = virtualHost.split(":")[0];
        return (domainName.equals("localhost") || domainName.equals("0.0.0.0")) ? config.getWebRoot() : config.getWebRoot() + domainName;
    }

    public Route getRouteForPath(String path) throws RouteNotFoundException {
        List<Route> routes = config.getRoutes();
        Optional<Route> matchedRoute = routes.stream().filter(route -> path.matches(route.getRoutePath())).findFirst();
        if (matchedRoute.isPresent()) {
            return matchedRoute.get();
        } else {
            throw new RouteNotFoundException();
        }
    }

    private void prepareRequest(HttpRequest httpRequest) {
        if (httpRequest.getPath().toCharArray()[httpRequest.getPath().length() - 1] == '/') {
            httpRequest.appendToPath("index.html");
        }
    }

}
