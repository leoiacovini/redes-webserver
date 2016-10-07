package poli.pcs.redes.webserver.components;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import poli.pcs.redes.webserver.http.Route;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Config {

    private Document configDocument;
    static private Config defaultConfig;

    public static Config getDefaultConfig() {
        if (Config.defaultConfig == null) {
            Config.defaultConfig = new Config();
        }
        return defaultConfig;
    }

    public Config(String configFilePath) {
        try {
            File configFile = new File(configFilePath);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            this.configDocument = dBuilder.parse(configFile);
            this.configDocument.getDocumentElement().normalize();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Config() {
        this("res/config.xml");
    }

    public String getWebRoot() {
        return getIn("webRoot");
    }

    public String secureUsername() {
        return getIn("secureUsername");
    }

    public String securePassword() {
        return getIn("securePassword");
    }

    private String getIn(String name) {
        return configDocument.getElementsByTagName(name).item(0).getTextContent();
    }

    public List<Route> getRoutes() {
        NodeList routesList = configDocument.getElementsByTagName("Routes").item(0).getChildNodes();
        ArrayList<Route> protectedRoutes = new ArrayList<>();
        for (int i = 0; i < routesList.getLength(); i++) {
            Node node = routesList.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element route = (Element) node;
                Route newRoute = new Route(route.getElementsByTagName("controller").item(0).getTextContent(),
                        route.getElementsByTagName("handler").item(0).getTextContent(),
                        route.getElementsByTagName("path").item(0).getTextContent());
                protectedRoutes.add(newRoute);
            }
        }
        return protectedRoutes;
    }
}
