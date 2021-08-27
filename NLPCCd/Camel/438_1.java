//,temp,sample_3280.java,2,14,temp,sample_8444.java,2,14
//,3
public class xxx {
public void testRouteboxDirectProducerOnlyRequests() throws Exception {
template = new DefaultProducerTemplate(context);
template.start();
context.addRoutes(new RouteBuilder() {
public void configure() {
from("direct:start") .to(routeboxUri) .to("log:Routes operation performed?showAll=true");
}
});
context.start();


log.info("beginning test testrouteboxdirectsyncrequests");
}

};