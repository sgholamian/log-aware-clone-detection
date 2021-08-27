//,temp,sample_6253.java,2,14,temp,sample_6257.java,2,18
//,3
public class xxx {
private void initFixedLengthRecordParameters() {
for (Class<?> cl : models) {
FixedLengthRecord record = cl.getAnnotation(FixedLengthRecord.class);
if (record != null) {
crlf = record.crlf();
eol = record.eol();
header =  record.header();


log.info("header");
}
}
}

};