//,temp,sample_3220.java,2,7,temp,sample_3211.java,2,7
//,2
public class xxx {
private void powerOnDroplet(Exchange exchange) throws Exception {
Action action = getEndpoint().getDigitalOceanClient().powerOnDroplet(dropletId);


log.info("power on droplet");
}

};