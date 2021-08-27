//,temp,sample_1333.java,2,14,temp,sample_396.java,2,14
//,2
public class xxx {
public void testLoadTestJdbcAggregate() throws Exception {
MockEndpoint mock = getMockEndpoint("mock:result");
mock.expectedMinimumMessageCount(1);
mock.setResultWaitTime(50 * 1000);
for (int i = 0; i < SIZE; i++) {
final int value = 1;
char id = 'A';
template.sendBodyAndHeader("seda:start?size=" + SIZE, value, "id", "" + id);
}


log.info("sending all message done now waiting for aggregation to complete");
}

};