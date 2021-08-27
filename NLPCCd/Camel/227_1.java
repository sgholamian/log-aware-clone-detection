//,temp,sample_3207.java,2,7,temp,sample_4735.java,2,7
//,3
public class xxx {
private void getDropletBackups(Exchange exchange) throws Exception {
Backups backups = getEndpoint().getDigitalOceanClient().getDropletBackups(dropletId, configuration.getPage(), configuration.getPerPage());


log.info("backups for droplet page per page");
}

};