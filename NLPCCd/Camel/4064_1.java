//,temp,sample_6762.java,2,16,temp,sample_1002.java,2,16
//,3
public class xxx {
public void dummy_method(){
resetMocks();
mock.expectedMessageCount(0);
context.stopRoute("foo");
assertEquals("Stopped", context.getRouteStatus("foo").name());
Route route = context.getRoute("foo");
if (route instanceof StatefulService) {
assertEquals("Stopped", ((StatefulService) route).getStatus().name());
}
template.sendBody("seda:foo", "B");
mock.assertIsSatisfied(1000);


log.info("Starting");
}

};