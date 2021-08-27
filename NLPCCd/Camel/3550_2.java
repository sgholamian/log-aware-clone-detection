//,temp,sample_5260.java,2,18,temp,sample_5259.java,2,17
//,3
public class xxx {
public void dummy_method(){
try {
valueFormatted = format.format(keyValue);
} catch (Exception e) {
throw new IllegalArgumentException("Formatting error detected for the tag: " + keyValuePairField.tag(), e);
}
String value = keyValuePairField.tag() + this.getKeyValuePairSeparator() + valueFormatted;
if (LOG.isDebugEnabled()) {
}
positions.put(keyGenerated, value);
if (LOG.isDebugEnabled()) {


log.info("positions size");
}
}

};