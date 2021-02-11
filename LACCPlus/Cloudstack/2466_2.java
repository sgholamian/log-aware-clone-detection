//,temp,RESTServiceConnector.java,80,85,temp,RESTServiceConnector.java,71,74
//,3
public class xxx {
    public <T> void executeUpdateObject(final T newObject, final String path, final Map<String, String> parameters) throws CloudstackRESTException {
        s_logger.debug("Executing update object on " + path);
        client.closeResponse(createAndExecuteRequest(HttpMethods.PUT, path, parameters, Optional.fromNullable(gson.toJson(newObject))));
    }

};