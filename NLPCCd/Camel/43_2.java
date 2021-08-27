//,temp,sample_6252.java,2,13,temp,sample_6256.java,2,17
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
skipHeader = record.skipHeader();
footer =  record.footer();


log.info("footer");
}
}
}

};