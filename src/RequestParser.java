import java.util.Hashtable;

public class RequestParser {
    private String request;

    public RequestParser(String request) {
        this.request = request;
    }

    public String route() {
        String route = rawRoute();
        route = route.split("\\?")[0];

        if( !route.equals("/") ) {
            route += "/";
        }
        return route;
    }

    public String method() {
        String lines[] = request.split("\\r?\\n");
        String[] header = lines[0].split(" ");
        String requestType = header[0];

        return requestType;
    }

    public Hashtable queryStrings() {
        if (queryStringPresent()) {
        String route = rawRoute();
        String rawQueryStrings = route.split("\\?")[1];
        String[] queryStrings = rawQueryStrings.split("&");

        Hashtable<String, String> queryStringHash = new Hashtable<String, String>();
        for(String string : queryStrings) {
            if(string.contains("=")) {
                String[] queryString = string.split("=");
                queryStringHash.put(queryString[0], queryString[1]);
            }
        }
        return queryStringHash;
        } else {
            return new Hashtable<String, String>();
        }
    }

    public String rawRoute() {
        String lines[] = request.split("\\r?\\n");
        String[] header = lines[0].split(" ");
        return header[1];
    }

    public boolean queryStringPresent() {
        return (rawRoute().split("\\?").length > 1);
    }

}
