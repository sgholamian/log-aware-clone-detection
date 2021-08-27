//,temp,sample_3599.java,2,17,temp,sample_3598.java,2,18
//,3
public class xxx {
public void dummy_method(){
assertValidContext(context);
context.getShutdownStrategy().setTimeout(10);
template = context.createProducerTemplate();
template.start();
consumer = context.createConsumerTemplate();
consumer.start();
oneExchangeDone = event().whenDone(1).create();
if (isUseRouteBuilder()) {
RouteBuilder[] builders = createRouteBuilders();
for (RouteBuilder builder : builders) {


log.info("using created route builder");
}
}
}

};