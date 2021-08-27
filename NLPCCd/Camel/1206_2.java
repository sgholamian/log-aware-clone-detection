//,temp,sample_2607.java,2,16,temp,sample_6680.java,2,16
//,3
public class xxx {
public void dummy_method(){
mock.setResultWaitTime(50 * 1000);
for (int i = 0; i < SIZE; i++) {
final int value = 1;
char id = 'A';
Map<String, Object> headers = new HashMap<String, Object>();
headers.put("id", id);
headers.put("seq", i);
template.sendBodyAndHeaders("seda:start", value, headers);
Thread.sleep(5);
}


log.info("sending all message done now waiting for aggregation to complete");
}

};