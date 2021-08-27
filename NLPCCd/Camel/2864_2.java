//,temp,sample_4480.java,2,9,temp,sample_2901.java,2,11
//,3
public class xxx {
protected void assertValidContext(SpringCamelContext context) {
assertNotNull("No context found!", context);
List<Route> routes = context.getRoutes();
assertNotNull("Should have some routes defined", routes);
assertEquals("Number of routes defined", 1, routes.size());
Route route = routes.get(0);


log.info("found route");
}

};