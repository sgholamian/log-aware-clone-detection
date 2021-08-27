//,temp,sample_8006.java,2,10,temp,sample_4830.java,2,8
//,3
public class xxx {
public void act(final Client client, final Exchange exchange) throws NoSuchHeaderException, InvalidPayloadException {
final Integer jobs = exchange.getIn().getMandatoryBody(Integer.class);
final int result = client.kick(jobs);
if (LOG.isDebugEnabled()) {


log.info("kick d jobs kicked d actually");
}
}

};