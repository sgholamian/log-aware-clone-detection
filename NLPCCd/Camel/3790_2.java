//,temp,sample_4174.java,2,16,temp,sample_5525.java,2,13
//,3
public class xxx {
public void testUnMarshal() throws Exception {
MockEndpoint endpoint = getMockEndpoint("mock:daltons");
endpoint.expectedMessageCount(1);
endpoint.assertIsSatisfied();
Exchange exchange = endpoint.getExchanges().get(0);
List<List<String>> data = (List<List<String>>) exchange.getIn().getBody();
for (List<String> line : data) {


log.info("s has an iq of s and is currently s");
}
}

};