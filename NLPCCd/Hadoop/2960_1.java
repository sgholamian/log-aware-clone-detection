//,temp,sample_5386.java,2,11,temp,sample_7307.java,2,12
//,3
public class xxx {
private void add(Configuration conf, String serializationName) {
try {
Class<? extends Serialization> serializionClass = (Class<? extends Serialization>) conf.getClassByName(serializationName);
serializations.add((Serialization) ReflectionUtils.newInstance(serializionClass, getConf()));
} catch (ClassNotFoundException e) {


log.info("serialization class not found");
}
}

};