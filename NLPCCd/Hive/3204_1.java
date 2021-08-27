//,temp,sample_1968.java,2,17,temp,sample_4517.java,2,17
//,2
public class xxx {
public void dummy_method(){
Map<String, String> spec = desc.getPartSpec();
if (spec == null) {
throw new AssertionException("No partition spec found in dynamic pruning");
}
String partValueString = spec.get(columnName);
if (partValueString == null) {
throw new AssertionException("Could not find partition value for column: " + columnName);
}
Object partValue = converter.convert(partValueString);
if (LOG.isDebugEnabled()) {


log.info("converted partition value original");
}
}

};