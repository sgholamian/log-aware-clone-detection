//,temp,sample_3005.java,2,16,temp,sample_3004.java,2,15
//,3
public class xxx {
private void initCsvRecordParameters() {
if (separator == null) {
for (Class<?> cl : models) {
CsvRecord record = cl.getAnnotation(CsvRecord.class);
Section section = cl.getAnnotation(Section.class);
if (record != null) {
skipFirstLine = record.skipFirstLine();
generateHeaderColumnNames = record.generateHeaderColumns();


log.info("generate header column names parameter of the csv");
}
}
}
}

};