//,temp,sample_6385.java,2,8,temp,sample_3544.java,2,9
//,3
public class xxx {
private void sendOutMessage(Exchange exchange) throws QFJException {
Message camelMessage = exchange.getOut();
quickfix.Message quickfixjMessage = camelMessage.getBody(quickfix.Message.class);


log.info("sending fix message reply");
}

};