//,temp,sample_5611.java,2,9,temp,sample_5613.java,2,14
//,3
public class xxx {
public void doStart() throws Exception {
super.doStart();
sdbClient = configuration.getAmazonSDBClient() != null ? configuration.getAmazonSDBClient() : createSdbClient();
String domainName = getConfiguration().getDomainName();


log.info("querying whether domain already exists");
}

};