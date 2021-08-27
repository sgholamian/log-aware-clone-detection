//,temp,sample_1118.java,2,9,temp,sample_1117.java,2,9
//,3
public class xxx {
public void increment(String groupName, String counterName, long value) {
SparkCounter counter = getGroup(groupName).getCounter(counterName);
if (counter == null) {


log.info("counter s s has not initialized before");
}
}

};