//,temp,sample_2427.java,2,10,temp,sample_5549.java,2,13
//,3
public class xxx {
public void init(AccumuloSerDeParameters accumuloSerDeParams, Properties properties) throws SerDeException {
super.init(accumuloSerDeParams, properties);
String delimiter = properties.getProperty(ACCUMULO_COMPOSITE_DELIMITER);
if (null == delimiter || delimiter.isEmpty()) {
throw new SerDeException("Did not find expected delimiter in configuration: " + ACCUMULO_COMPOSITE_DELIMITER);
}
if (delimiter.length() != 1) {


log.info("configured delimiter is longer than one character only using first character");
}
}

};