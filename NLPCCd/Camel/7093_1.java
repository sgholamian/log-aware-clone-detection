//,temp,sample_5888.java,2,8,temp,sample_6191.java,2,8
//,3
public class xxx {
public void testUndertow() throws Exception {
MockEndpoint mockEndpoint = getMockEndpoint("mock:myapp");
mockEndpoint.expectedHeaderReceived(Exchange.HTTP_METHOD, "POST");


log.info("number of exchanges in mock myapp");
}

};