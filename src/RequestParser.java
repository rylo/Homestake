import java.util.Hashtable;

public class RequestParser {
    private String request;

    public RequestParser(String request) {
        this.request = request;
    }

    public String route() {
        String lines[] = request.split("\\r?\\n");
        String[] header = lines[0].split(" ");
        String route = header[1];
        if( !route.equals("/") ) {
            route += "/";
        }
        return route;
    }

    public String type() {
        String lines[] = request.split("\\r?\\n");
        String[] header = lines[0].split(" ");
        String requestType = header[0];
        return requestType;
    }

    public Hashtable queryStrings() {
        String[] rawQueryStrings = route().split("\\?");
        Hashtable<String, String> queryStringHash = new Hashtable<String, String>();

        for(String string : rawQueryStrings) {
            if(string.contains("=")) {
                String[] queryString = string.split("=");
                queryStringHash.put(queryString[0], queryString[1]);
            }
        }
        return queryStringHash;
    }

}
