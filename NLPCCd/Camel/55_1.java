//,temp,sample_871.java,2,14,temp,sample_38.java,2,13
//,3
public class xxx {
public void testOIDPolling() throws Exception {
MockEndpoint mock = getMockEndpoint("mock:result");
mock.expectedMinimumMessageCount(1);
mock.assertIsSatisfied();
List<Exchange> oids = mock.getExchanges();
if (LOG.isInfoEnabled()) {
for (Exchange e : oids) {


log.info("oid");
}
}
}

};