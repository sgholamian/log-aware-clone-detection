//,temp,sample_3782.java,2,10,temp,sample_5875.java,2,13
//,3
public class xxx {
protected <T> void doAddOption(Map<String, T> options, String name, T value) {
log.trace("Adding option: {}={}", name, value);
T val = options.put(name, value);
if (val != null) {


log.info("options overridden old value was");
}
}

};