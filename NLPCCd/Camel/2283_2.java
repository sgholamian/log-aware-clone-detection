//,temp,sample_3220.java,2,7,temp,sample_3211.java,2,7
//,2
public class xxx {
private void deleteDroplet(Exchange exchange) throws Exception {
Delete delete = getEndpoint().getDigitalOceanClient().deleteDroplet(dropletId);


log.info("delete droplet");
}

};