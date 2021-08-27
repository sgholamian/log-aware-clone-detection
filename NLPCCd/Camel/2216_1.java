//,temp,sample_3012.java,2,16,temp,sample_3011.java,2,16
//,2
public class xxx {
public void dummy_method(){
ObjectHelper.notNull(record.separator(), "No separator has been defined in the @Record annotation");
separator = record.separator();
crlf = record.crlf();
messageOrdered = record.isOrdered();
if (ObjectHelper.isNotEmpty(record.quote())) {
quote = record.quote();
}
quoting = record.quoting();
autospanLine = record.autospanLine();
allowEmptyStream = record.allowEmptyStream();


log.info("allow empty stream parameter of the csv");
}

};