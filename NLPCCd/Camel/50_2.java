//,temp,sample_4623.java,2,10,temp,sample_2855.java,2,10
//,3
public class xxx {
public void notify(EventObject event) throws Exception {
if (event instanceof CamelContextStartedEvent) {
template.sendBody("file:target/startandstop/start.txt", "Starting");
} else if (event instanceof CamelContextStoppingEvent) {


log.info("sending a message on stopping");
}
}

};