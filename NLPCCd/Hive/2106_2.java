//,temp,sample_1969.java,2,17,temp,sample_4518.java,2,17
//,2
public class xxx {
public void dummy_method(){
String partValueString = spec.get(columnName);
if (partValueString == null) {
throw new IllegalStateException("Could not find partition value for column: " + columnName);
}
Object partValue = converter.convert(partValueString);
if (LOG.isDebugEnabled()) {
}
row[0] = partValue;
partValue = eval.evaluate(row);
if (LOG.isDebugEnabled()) {


log.info("part key expr applied");
}
}

};