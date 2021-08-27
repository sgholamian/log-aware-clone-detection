//,temp,QuickfixjConverters.java,147,167,temp,QuickfixjConverters.java,126,145
//,3
public class xxx {
    public static Exchange toExchange(
            Endpoint endpoint, SessionID sessionID, Message message, QuickfixjEventCategory eventCategory,
            ExchangePattern exchangePattern) {
        Exchange exchange = endpoint.createExchange(exchangePattern);

        org.apache.camel.Message camelMessage = exchange.getIn();
        camelMessage.setHeader(EVENT_CATEGORY_KEY, eventCategory);
        camelMessage.setHeader(SESSION_ID_KEY, sessionID);

        if (message != null) {
            try {
                camelMessage.setHeader(MESSAGE_TYPE_KEY, message.getHeader().getString(MsgType.FIELD));
            } catch (FieldNotFound e) {
                LOG.warn("Message type field not found in QFJ message: {}, continuing...", message);
            }
        }
        camelMessage.setBody(message);

        return exchange;
    }

};