//,temp,sample_9340.java,2,18,temp,sample_9343.java,2,18
//,2
public class xxx {
public void dummy_method(){
while (iterator.hasNext()) {
expected = null;
actual = null;
String type = iterator.next().toString();
expected = expectedEvents.get(type);
actual = actualEvents.get(type);
if (actual == null) {
fail++;
} else if (expected.compareTo(actual) != 0) {
fail++;


log.info("amount of events of type and level is incorrect expected number of these events is actual number is");
}
}
}

};