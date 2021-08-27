//,temp,sample_6762.java,2,16,temp,sample_1002.java,2,16
//,3
public class xxx {
public void dummy_method(){
mock.expectedMessageCount(0);
context.suspendRoute("foo");
assertEquals("Suspended", context.getRouteStatus("foo").name());
Route route = context.getRoute("foo");
if (route instanceof StatefulService) {
assertEquals("Suspended", ((StatefulService) route).getStatus().name());
}
await().atMost(1, TimeUnit.SECONDS).until(() -> context.getEndpoint("seda:foo", SedaEndpoint.class).getQueue().size() == 0);
template.sendBody("seda:foo", "B");
mock.assertIsSatisfied(100);


log.info("Resuming");
}

};