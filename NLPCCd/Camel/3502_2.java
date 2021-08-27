//,temp,sample_307.java,2,18,temp,sample_7189.java,2,18
//,3
public class xxx {
public void dummy_method(){
JmsMessageType type;
if (endpoint != null && endpoint.isTransferExchange()) {
LOG.trace("Option transferExchange=true so we use JmsMessageType: Object");
Serializable holder = DefaultExchangeHolder.marshal(exchange, true, endpoint.isAllowSerializedHeaders());
Message answer = session.createObjectMessage(holder);
answer.setJMSDeliveryMode(Message.DEFAULT_DELIVERY_MODE);
return answer;
}
if (endpoint != null && endpoint.getMessageConverter() != null) {
if (LOG.isTraceEnabled()) {


log.info("creating jmsmessage using a custom messageconverter with body");
}
}
}

};