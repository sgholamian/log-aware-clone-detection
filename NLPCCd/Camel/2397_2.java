//,temp,sample_6981.java,2,11,temp,sample_1246.java,2,11
//,2
public class xxx {
public void testPutAndGetNotFound() {
HawtDBAggregationRepository repo = new HawtDBAggregationRepository();
repo.setHawtDBFile(hawtDBFile);
repo.setRepositoryName("repo1");
Exchange exchange = new DefaultExchange(context);
exchange.getIn().setBody("Hello World");


log.info("created");
}

};