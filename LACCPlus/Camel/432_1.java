//,temp,SnmpOIDPoller.java,238,248,temp,SipSubscriptionListener.java,50,60
//,3
public class xxx {
    public void processPDU(PDU pdu) {
        if (LOG.isDebugEnabled()) {
            LOG.debug("Received response event for {} : {}", this.endpoint.getAddress(), pdu);
        }
        Exchange exchange = endpoint.createExchange(pdu);
        try {
            getProcessor().process(exchange);
        } catch (Exception e) {
            getExceptionHandler().handleException(e);
        }
    }

};