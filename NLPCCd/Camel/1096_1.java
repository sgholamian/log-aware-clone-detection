//,temp,sample_1332.java,2,13,temp,sample_643.java,2,14
//,3
public class xxx {
public void testLoadTestHawtDBAggregate() throws Exception {
MockEndpoint mock = getMockEndpoint("mock:result");
mock.expectedMinimumMessageCount(1);
mock.setResultWaitTime(50 * 1000);
for (int i = 0; i < SIZE; i++) {
final int value = 1;
char id = 'A';


log.info("sending with id");
}
}

};