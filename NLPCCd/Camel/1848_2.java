//,temp,sample_3606.java,2,9,temp,sample_5743.java,2,9
//,2
public class xxx {
public void testLoadTestLevelDBAggregate() throws Exception {
MockEndpoint mock = getMockEndpoint("mock:result");
mock.expectedMinimumMessageCount(1);
mock.setResultWaitTime(50 * 1000);


log.info("staring to send messages");
}

};