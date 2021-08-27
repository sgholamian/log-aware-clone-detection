//,temp,sample_6250.java,2,11,temp,sample_6251.java,2,12
//,3
public class xxx {
private void initFixedLengthRecordParameters() {
for (Class<?> cl : models) {
FixedLengthRecord record = cl.getAnnotation(FixedLengthRecord.class);
if (record != null) {


log.info("fixed length record");
}
}
}

};