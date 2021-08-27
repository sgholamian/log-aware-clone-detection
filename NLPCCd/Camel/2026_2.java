//,temp,sample_3395.java,2,11,temp,sample_2618.java,2,11
//,2
public class xxx {
private void getAction(Exchange exchange) throws Exception {
Integer actionId = exchange.getIn().getHeader(DigitalOceanHeaders.ID, Integer.class);
if (ObjectHelper.isEmpty(actionId)) {
throw new IllegalArgumentException(DigitalOceanHeaders.ID + " must be specified");
}
Action action = getEndpoint().getDigitalOceanClient().getActionInfo(actionId);


log.info("action");
}

};