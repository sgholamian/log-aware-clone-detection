//,temp,sample_8270.java,2,11,temp,sample_1711.java,2,16
//,3
public class xxx {
public void process(Exchange exchange) throws Exception {
if (exchange.getIn().getBody() == null) {
return;
}
TelegramConfiguration config = endpoint.getConfiguration();
OutgoingMessage message = exchange.getIn().getBody(OutgoingMessage.class);
if (message == null) {
throw new IllegalArgumentException("Cannot convert the content to a Telegram OutgoingMessage");
}
if (message.getChatId() == null) {


log.info("chat id is null on outgoing message trying resolution");
}
}

};