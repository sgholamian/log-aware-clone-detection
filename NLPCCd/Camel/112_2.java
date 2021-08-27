//,temp,sample_2605.java,2,9,temp,sample_1331.java,2,9
//,3
public class xxx {
public void testLoadTestHawtDBAggregate() throws Exception {
MockEndpoint mock = getMockEndpoint("mock:result");
mock.expectedMinimumMessageCount(1);
mock.setResultWaitTime(50 * 1000);


log.info("staring to send messages");
}

};