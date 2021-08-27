//,temp,sample_3010.java,2,16,temp,sample_3014.java,2,16
//,3
public class xxx {
public void dummy_method(){
skipFirstLine = record.skipFirstLine();
generateHeaderColumnNames = record.generateHeaderColumns();
ObjectHelper.notNull(record.separator(), "No separator has been defined in the @Record annotation");
separator = record.separator();
crlf = record.crlf();
messageOrdered = record.isOrdered();
if (ObjectHelper.isNotEmpty(record.quote())) {
quote = record.quote();
}
quoting = record.quoting();


log.info("csv will be quoted");
}

};