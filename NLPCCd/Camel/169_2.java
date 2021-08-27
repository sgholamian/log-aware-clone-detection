//,temp,sample_262.java,2,7,temp,sample_3225.java,2,7
//,3
public class xxx {
private void disableDropletBackups(Exchange exchange) throws Exception {
Action action = getEndpoint().getDigitalOceanClient().disableDropletBackups(dropletId);


log.info("disable backups for droplet");
}

};