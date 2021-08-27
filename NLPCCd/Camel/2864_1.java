//,temp,sample_4480.java,2,9,temp,sample_2901.java,2,11
//,3
public class xxx {
public void testCreateRouteFromCamelContext() throws Exception {
List<RouteDefinition> list = context.getRouteDefinitions();
assertEquals("Size of list " + list, 1, list.size());
RouteDefinition routeType = list.get(0);


log.info("found route");
}

};