//,temp,sample_3228.java,2,7,temp,sample_3219.java,2,7
//,2
public class xxx {
private void rebootDroplet(Exchange exchange) throws Exception {
Action action = getEndpoint().getDigitalOceanClient().rebootDroplet(dropletId);


log.info("reboot droplet");
}

};