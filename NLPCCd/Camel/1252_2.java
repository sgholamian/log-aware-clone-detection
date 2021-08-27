//,temp,sample_3007.java,2,19,temp,sample_3009.java,2,18
//,3
public class xxx {
public void dummy_method(){
Section section = cl.getAnnotation(Section.class);
if (record != null) {
skipFirstLine = record.skipFirstLine();
generateHeaderColumnNames = record.generateHeaderColumns();
ObjectHelper.notNull(record.separator(), "No separator has been defined in the @Record annotation");
separator = record.separator();
crlf = record.crlf();
messageOrdered = record.isOrdered();
if (ObjectHelper.isNotEmpty(record.quote())) {
quote = record.quote();


log.info("quoting columns with");
}
}
}

};