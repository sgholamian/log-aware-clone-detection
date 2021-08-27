//,temp,sample_1713.java,2,16,temp,sample_1712.java,2,17
//,3
public class xxx {
public void dummy_method(){
if (exchange.getIn().getBody() == null) {
return;
}
TelegramConfiguration config = endpoint.getConfiguration();
OutgoingMessage message = exchange.getIn().getBody(OutgoingMessage.class);
if (message == null) {
throw new IllegalArgumentException("Cannot convert the content to a Telegram OutgoingMessage");
}
if (message.getChatId() == null) {
String chatId = resolveChatId(config, message, exchange);


log.info("resolved chat id is");
}
}

};