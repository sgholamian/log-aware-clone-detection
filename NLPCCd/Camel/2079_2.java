//,temp,sample_6260.java,2,16,temp,sample_6262.java,2,16
//,2
public class xxx {
public void dummy_method(){
header =  record.header();
hasHeader = header != void.class;
skipHeader = record.skipHeader();
footer =  record.footer();
hasFooter = record.footer() != void.class;
skipFooter = record.skipFooter();
isHeader = hasHeader ? cl.equals(header) : false;
isFooter = hasFooter ? cl.equals(footer) : false;
paddingChar = record.paddingChar();
recordLength = record.length();


log.info("length of the record");
}

};