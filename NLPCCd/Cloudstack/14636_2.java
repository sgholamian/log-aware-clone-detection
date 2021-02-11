//,temp,sample_9413.java,2,19,temp,sample_9414.java,2,19
//,2
public class xxx {
public void dummy_method(){
HttpClient client = new HttpClient();
HttpMethod method = new GetMethod(url);
int responseCode = client.executeMethod(method);
boolean success = false;
String reason = null;
if (responseCode == 200) {
if (internet) {
Thread.sleep(300000L);
reason = sshTest(method.getResponseHeader("linuxIP").getValue());
if (reason == null) {


log.info("begin windows ssh test");
}
}
}
}

};