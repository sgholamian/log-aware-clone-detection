//,temp,sample_5265.java,2,18,temp,sample_3008.java,2,18
//,3
public class xxx {
public void dummy_method(){
for (Class<?> cl : models) {
CsvRecord record = cl.getAnnotation(CsvRecord.class);
Section section = cl.getAnnotation(Section.class);
if (record != null) {
skipFirstLine = record.skipFirstLine();
generateHeaderColumnNames = record.generateHeaderColumns();
ObjectHelper.notNull(record.separator(), "No separator has been defined in the @Record annotation");
separator = record.separator();
crlf = record.crlf();
messageOrdered = record.isOrdered();


log.info("must csv record be ordered");
}
}
}

};