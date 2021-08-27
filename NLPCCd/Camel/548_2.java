//,temp,sample_641.java,2,9,temp,sample_8135.java,2,10
//,3
public class xxx {
public void testLoadTestHawtDBAggregate() throws Exception {
MockEndpoint mock = getMockEndpoint("mock:result");
mock.expectedMessageCount(10);
mock.setResultWaitTime(50 * 1000);
ExecutorService executor = Executors.newFixedThreadPool(10);


log.info("staring to send messages");
}

};