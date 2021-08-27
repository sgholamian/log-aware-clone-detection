//,temp,sample_3113.java,2,14,temp,sample_4814.java,2,14
//,2
public class xxx {
public void testRouteboxUsingDefaultContextAndRouteBuilder() throws Exception {
template = new DefaultProducerTemplate(context);
template.start();
context.addRoutes(new RouteBuilder() {
public void configure() {
from(routeboxUri) .to("log:Routes operation performed?showAll=true");
}
});
context.start();


log.info("beginning test testrouteboxusingdefaultcontextandroutebuilder");
}

};