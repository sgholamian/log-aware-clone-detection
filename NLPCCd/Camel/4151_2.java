//,temp,sample_5473.java,2,16,temp,sample_3818.java,2,16
//,3
public class xxx {
public void testUnmarshal() throws Exception {
MockEndpoint mock = getMockEndpoint("mock:beanio-unmarshal");
mock.expectedMessageCount(2);
template.sendBody("direct:unmarshal", FIXED_DATA);
mock.assertIsSatisfied();
List<Exchange> exchanges = mock.getExchanges();
if (verbose) {
for (Exchange exchange : exchanges) {
Object body = exchange.getIn().getBody();


log.info("received message of class");
}
}
}

};