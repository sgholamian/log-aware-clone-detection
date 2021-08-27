//,temp,sample_6253.java,2,14,temp,sample_6257.java,2,18
//,3
public class xxx {
public void dummy_method(){
for (Class<?> cl : models) {
FixedLengthRecord record = cl.getAnnotation(FixedLengthRecord.class);
if (record != null) {
crlf = record.crlf();
eol = record.eol();
header =  record.header();
hasHeader = header != void.class;
skipHeader = record.skipHeader();
footer =  record.footer();
hasFooter = record.footer() != void.class;


log.info("has footer");
}
}
}

};