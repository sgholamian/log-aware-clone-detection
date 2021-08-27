//,temp,sample_5342.java,2,9,temp,sample_3706.java,2,10
//,3
public class xxx {
public void map(WritableComparable key, HCatRecord value, Context context) throws IOException, InterruptedException {
try {
readRecords.add(value);
} catch (Exception e) {


log.info("error when read record");
}
}

};