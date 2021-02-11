//,temp,sample_985.java,2,10,temp,sample_2767.java,2,9
//,3
public class xxx {
private void checkStatusDump(ExecutorStatus status) throws IOException {
StringWriter sw = new StringWriter();
status.dumpTo(sw, "");
String dump = sw.toString();


log.info("got status dump");
}

};