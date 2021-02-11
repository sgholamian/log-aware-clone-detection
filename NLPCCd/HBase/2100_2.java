//,temp,sample_1606.java,2,7,temp,sample_4778.java,2,10
//,3
public class xxx {
public Get setTimeStamp(long timestamp) throws IOException {
try {
tr = new TimeRange(timestamp, timestamp+1);
} catch(Exception e) {


log.info("timerange failed likely caused by integer overflow");
}
}

};