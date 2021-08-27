//,temp,sample_4436.java,2,16,temp,sample_4435.java,2,16
//,2
public class xxx {
public void dummy_method(){
if (columnNameProperty.isEmpty()) {
columnNames = Collections.emptyList();
} else {
columnNames = Arrays.asList(columnNameProperty.split(columnNameDelimiter));
}
if (columnTypeProperty.isEmpty()) {
columnTypes = Collections.emptyList();
} else {
columnTypes = TypeInfoUtils.getTypeInfosFromTypeString(columnTypeProperty);
}


log.info("types");
}

};