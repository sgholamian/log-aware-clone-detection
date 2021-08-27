//,temp,sample_3599.java,2,17,temp,sample_3598.java,2,18
//,3
public class xxx {
public void dummy_method(){
consumer = context.createConsumerTemplate();
consumer.start();
oneExchangeDone = event().whenDone(1).create();
if (isUseRouteBuilder()) {
RouteBuilder[] builders = createRouteBuilders();
for (RouteBuilder builder : builders) {
context.addRoutes(builder);
}
startCamelContext();
} else {


log.info("isuseroutebuilder is false");
}
}

};