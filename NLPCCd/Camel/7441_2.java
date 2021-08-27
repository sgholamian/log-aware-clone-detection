//,temp,sample_467.java,2,17,temp,sample_113.java,2,12
//,3
public class xxx {
public StreamCache cache(Exchange exchange) {
Message message = exchange.hasOut() ? exchange.getOut() : exchange.getIn();
StreamCache cache = message.getBody(StreamCache.class);
if (cache != null) {
if (LOG.isTraceEnabled()) {


log.info("cached stream to memory spool");
}
}
}

};