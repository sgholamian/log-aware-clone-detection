//,temp,sample_2948.java,2,9,temp,sample_2394.java,2,9
//,3
public class xxx {
protected void processExchange(Exchange exchange) throws Exception {
if (LOG.isDebugEnabled()) {
MailMessage msg = (MailMessage) exchange.getIn();


log.info("processing message");
}
}

};