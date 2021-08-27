//,temp,sample_1133.java,2,16,temp,sample_1132.java,2,17
//,3
public class xxx {
public void dummy_method(){
String message = exchange.getIn().getBody(String.class);
Level level = exchange.getIn().getHeader(LEVEL, Level.class);
if (level == null) {
String name = exchange.getIn().getHeader(LEVEL, Level.OK.name(), String.class);
level = Level.valueOf(name);
}
String serviceName = exchange.getIn().getHeader(SERVICE_NAME, exchange.getContext().getName(), String.class);
String hostName = exchange.getIn().getHeader(HOST_NAME, "localhost", String.class);
MessagePayload payload = new MessagePayload(hostName, level, serviceName, message);
if (log.isDebugEnabled()) {


log.info("sending notification to nagios");
}
}

};