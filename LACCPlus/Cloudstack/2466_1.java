//,temp,RESTServiceConnector.java,80,85,temp,RESTServiceConnector.java,71,74
//,3
public class xxx {
    @SuppressWarnings("unchecked")
    public <T> T executeCreateObject(final T newObject, final String path, final Map<String, String> parameters) throws CloudstackRESTException {
        s_logger.debug("Executing create object on " + path);
        final CloseableHttpResponse response = createAndExecuteRequest(HttpMethods.POST, path, parameters, Optional.fromNullable(gson.toJson(newObject)));
        return (T) readResponseBody(response, newObject.getClass());
    }

};