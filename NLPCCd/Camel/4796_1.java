//,temp,sample_2606.java,2,16,temp,sample_6679.java,2,16
//,2
public class xxx {
public void testLoadAndRecoverJdbcAggregate() throws Exception {
MockEndpoint mock = getMockEndpoint("mock:result");
mock.expectedMessageCount(SIZE / 10);
mock.setResultWaitTime(50 * 1000);
for (int i = 0; i < SIZE; i++) {
final int value = 1;
char id = 'A';
Map<String, Object> headers = new HashMap<String, Object>();
headers.put("id", id);
headers.put("seq", i);


log.info("sending with id");
}
}

};