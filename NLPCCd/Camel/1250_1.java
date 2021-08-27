//,temp,sample_8359.java,2,18,temp,sample_317.java,2,18
//,3
public class xxx {
public void dummy_method(){
if (config.getApiContextPath() != null) {
boolean hasRestApi = false;
for (RouteDefinition route : getContext().getRouteDefinitions()) {
FromDefinition from = route.getInputs().get(0);
if (from.getUri() != null && from.getUri().startsWith("rest-api:")) {
hasRestApi = true;
}
}
if (!hasRestApi) {
RouteDefinition route = RestDefinition.asRouteApiDefinition(getContext(), config);


log.info("adding routeid as rest api route");
}
}
}

};