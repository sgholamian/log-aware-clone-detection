//,temp,sample_5888.java,2,8,temp,sample_6191.java,2,8
//,3
public class xxx {
private void testPath(String pathSuffix) throws InterruptedException {
MockEndpoint mockEndpoint = getMockEndpoint("mock:" + pathSuffix);
mockEndpoint.expectedHeaderReceived(Exchange.HTTP_METHOD, "POST");


log.info("number of exchanges in mock myapp");
}

};