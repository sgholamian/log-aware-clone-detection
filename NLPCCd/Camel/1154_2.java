//,temp,sample_2357.java,2,10,temp,sample_487.java,2,15
//,3
public class xxx {
public void process(Exchange exchange) throws Exception {
final String msg = exchange.getIn().getBody(String.class);
final String targetChannel = exchange.getIn().getHeader(IrcConstants.IRC_TARGET, String.class);
if (!connection.isConnected()) {
throw new RuntimeCamelException("Lost connection to " + connection.getHost());
}
if (msg != null) {
if (isMessageACommand(msg)) {


log.info("sending command");
}
}
}

};