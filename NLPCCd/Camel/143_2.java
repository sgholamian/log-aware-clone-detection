//,temp,sample_854.java,2,8,temp,sample_6189.java,2,8
//,3
public class xxx {
public void testRequestReplyWithConcurrent() throws Exception {
MockEndpoint mock = getMockEndpoint("mock:result");
mock.expectedBodiesReceivedInAnyOrder("Bye A", "Bye B", "Bye C", "Bye D", "Bye E");


log.info("sending messages");
}

};