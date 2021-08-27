//,temp,sample_6264.java,2,16,temp,sample_6263.java,2,16
//,3
public class xxx {
public void dummy_method(){
skipHeader = record.skipHeader();
footer =  record.footer();
hasFooter = record.footer() != void.class;
skipFooter = record.skipFooter();
isHeader = hasHeader ? cl.equals(header) : false;
isFooter = hasFooter ? cl.equals(footer) : false;
paddingChar = record.paddingChar();
recordLength = record.length();
ignoreTrailingChars = record.ignoreTrailingChars();
ignoreMissingChars = record.ignoreMissingChars();


log.info("enable ignore missing chars");
}

};