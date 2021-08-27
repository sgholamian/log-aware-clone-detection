//,temp,sample_6260.java,2,16,temp,sample_6262.java,2,16
//,2
public class xxx {
public void dummy_method(){
crlf = record.crlf();
eol = record.eol();
header =  record.header();
hasHeader = header != void.class;
skipHeader = record.skipHeader();
footer =  record.footer();
hasFooter = record.footer() != void.class;
skipFooter = record.skipFooter();
isHeader = hasHeader ? cl.equals(header) : false;
isFooter = hasFooter ? cl.equals(footer) : false;


log.info("is footer");
}

};