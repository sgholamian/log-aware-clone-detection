//,temp,sample_4168.java,2,16,temp,sample_4167.java,2,16
//,3
public class xxx {
public void dummy_method(){
String state = (String) mbeanServer.getAttribute(on, "State");
assertEquals("Should be started", ServiceStatus.Started.name(), state);
await().atMost(1, TimeUnit.SECONDS).untilAsserted(() -> {
Long completed = (Long) mbeanServer.getAttribute(on, "ExchangesCompleted");
assertEquals(1, completed.longValue());
});
Set<ObjectName> set = mbeanServer.queryNames(new ObjectName("*:type=consumers,*"), null);
assertEquals("Should be 1 consumer", 1, set.size());
set = mbeanServer.queryNames(new ObjectName("*:type=processors,*"), null);
assertEquals("Should be 2 processors", 2, set.size());


log.info("invoking stop");
}

};