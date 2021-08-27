//,temp,sample_1213.java,2,12,temp,sample_7673.java,2,12
//,3
public class xxx {
public void testAsyncInvocation() throws Exception {
endpoint.expectedMessageCount(1);
myService.doSomethingAsync("Hello");
endpoint.assertIsSatisfied();
List<Exchange> list = endpoint.getReceivedExchanges();
for (Exchange exchange : list) {


log.info("received");
}
}

};