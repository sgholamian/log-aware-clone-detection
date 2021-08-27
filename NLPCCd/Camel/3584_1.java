//,temp,sample_6548.java,2,11,temp,sample_7256.java,2,9
//,3
public class xxx {
public void dummy_method(){
mock.assertIsSatisfied();
incident = mock.getExchanges().get(0).getIn().getBody(Incident.class);
sysId = incident.getId();
number = incident.getNumber();
LOGGER.info("****************************************************");
LOGGER.info("  sysid  = {}", sysId);
LOGGER.info("  number = {}", number);
LOGGER.info("****************************************************");
}

};