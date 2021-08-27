//,temp,sample_6258.java,2,17,temp,sample_6259.java,2,17
//,3
public class xxx {
public void dummy_method(){
FixedLengthRecord record = cl.getAnnotation(FixedLengthRecord.class);
if (record != null) {
crlf = record.crlf();
eol = record.eol();
header =  record.header();
hasHeader = header != void.class;
skipHeader = record.skipHeader();
footer =  record.footer();
hasFooter = record.footer() != void.class;
skipFooter = record.skipFooter();


log.info("skip footer");
}
}

};