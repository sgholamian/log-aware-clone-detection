//,temp,sample_5745.java,2,16,temp,sample_3608.java,2,14
//,3
public class xxx {
public void dummy_method(){
mock.expectedMinimumMessageCount(1);
mock.setResultWaitTime(50 * 1000);
for (int i = 0; i < SIZE; i++) {
final int value = 1;
HeaderDto headerDto = new HeaderDto("test", "company", 1);
char id = 'A';
Map<String, Object> headers = new HashMap<String, Object>();
headers.put("id", headerDto);
template.sendBodyAndHeaders("seda:start?size=" + SIZE, value, headers);
}


log.info("sending all message done now waiting for aggregation to complete");
}

};