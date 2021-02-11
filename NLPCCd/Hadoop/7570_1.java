//,temp,sample_4586.java,2,10,temp,sample_1665.java,2,13
//,3
public class xxx {
private void validateCounters(org.apache.hadoop.mapreduce.Counters counters) {
Iterator<org.apache.hadoop.mapreduce.CounterGroup> it = counters.iterator();
while (it.hasNext()) {
org.apache.hadoop.mapreduce.CounterGroup group = it.next();


log.info("group");
}
}

};