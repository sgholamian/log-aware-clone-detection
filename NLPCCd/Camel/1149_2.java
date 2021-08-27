//,temp,sample_5745.java,2,16,temp,sample_3608.java,2,14
//,3
public class xxx {
public void testLoadTestJdbcAggregate() throws Exception {
MockEndpoint mock = getMockEndpoint("mock:result");
mock.expectedMinimumMessageCount(1);
mock.setResultWaitTime(50 * 1000);
for (int i = 0; i < SIZE; i++) {
final int value = 1;
HeaderDto headerDto = new HeaderDto("org", "company", 1);
template.sendBodyAndHeader("seda:start?size=" + SIZE, value, "id", headerDto);
}


log.info("sending all message done now waiting for aggregation to complete");
}

};