//,temp,sample_6254.java,2,15,temp,sample_6255.java,2,16
//,3
public class xxx {
private void initFixedLengthRecordParameters() {
for (Class<?> cl : models) {
FixedLengthRecord record = cl.getAnnotation(FixedLengthRecord.class);
if (record != null) {
crlf = record.crlf();
eol = record.eol();
header =  record.header();
hasHeader = header != void.class;


log.info("has header");
}
}
}

};