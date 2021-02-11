//,temp,sample_6140.java,2,8,temp,sample_9366.java,2,11
//,3
public class xxx {
private void add(long start, long end) {
if(end>start) {
Range recRange = new Range(start, end-start);
ranges.add(recRange);
indicesCount+=recRange.getLength();


log.info("added");
}
}

};