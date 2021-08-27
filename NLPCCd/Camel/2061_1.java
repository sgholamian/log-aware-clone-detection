//,temp,sample_395.java,2,13,temp,sample_642.java,2,13
//,2
public class xxx {
public void testLoadTestJdbcAggregate() throws Exception {
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