//,temp,sample_2788.java,2,16,temp,sample_2787.java,2,16
//,2
public class xxx {
public void dummy_method(){
if (columnNameProperty.length() == 0) {
columnNames = new ArrayList<String>();
} else {
columnNames = Arrays.asList(columnNameProperty.split(columnNameDelimiter));
}
if (columnTypeProperty.length() == 0) {
columnTypes = new ArrayList<TypeInfo>();
} else {
columnTypes = TypeInfoUtils.getTypeInfosFromTypeString(columnTypeProperty);
}


log.info("types");
}

};