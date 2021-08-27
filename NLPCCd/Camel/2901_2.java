//,temp,sample_3330.java,2,12,temp,sample_4985.java,3,10
//,3
public class xxx {
public void cancelCorrelationId(String correlationId) {
ReplyHandler handler = correlation.get(correlationId);
if (handler != null) {


log.info("cancelling correlationid");
}
}

};