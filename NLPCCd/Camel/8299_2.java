//,temp,sample_3227.java,2,7,temp,sample_3222.java,2,7
//,2
public class xxx {
private void shutdownDroplet(Exchange exchange) throws Exception {
Action action = getEndpoint().getDigitalOceanClient().shutdownDroplet(dropletId);


log.info("shutdown droplet");
}

};