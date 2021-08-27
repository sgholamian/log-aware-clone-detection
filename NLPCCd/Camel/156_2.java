//,temp,sample_3209.java,2,7,temp,sample_3208.java,2,7
//,3
public class xxx {
private void getDropletSnapshots(Exchange exchange) throws Exception {
Snapshots snapshots = getEndpoint().getDigitalOceanClient().getDropletSnapshots(dropletId, configuration.getPage(), configuration.getPerPage());


log.info("snapshots for droplet page per page");
}

};