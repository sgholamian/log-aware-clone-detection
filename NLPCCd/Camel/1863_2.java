//,temp,sample_3226.java,2,7,temp,sample_3221.java,2,7
//,2
public class xxx {
private void powerOffDroplet(Exchange exchange) throws Exception {
Action action = getEndpoint().getDigitalOceanClient().powerOffDroplet(dropletId);


log.info("power off droplet");
}

};