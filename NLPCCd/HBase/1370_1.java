//,temp,sample_4614.java,2,10,temp,sample_4667.java,2,8
//,3
public class xxx {
public Scan setTimeStamp(long timestamp) throws IOException {
try {
tr = new TimeRange(timestamp, timestamp+1);
} catch(Exception e) {


log.info("timerange failed likely caused by integer overflow");
}
}

};