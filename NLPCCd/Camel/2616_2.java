//,temp,sample_5210.java,2,17,temp,sample_7611.java,2,17
//,2
public class xxx {
public void dummy_method(){
if (ObjectHelper.isNotEmpty(exchange.getIn().getHeader(MQConstants.BROKER_ID))) {
brokerId = exchange.getIn().getHeader(MQConstants.BROKER_ID, String.class);
request.withBrokerId(brokerId);
} else {
throw new IllegalArgumentException("Broker Name must be specified");
}
DeleteBrokerResult result;
try {
result = mqClient.deleteBroker(request);
} catch (AmazonServiceException ase) {


log.info("delete broker command returned the error code");
}
}

};