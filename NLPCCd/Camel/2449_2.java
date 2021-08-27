//,temp,sample_3556.java,2,9,temp,sample_1378.java,2,9
//,2
public class xxx {
protected void initCustomRegistry(SpringCamelContext context) {
Registry registry = getBeanForType(Registry.class);
if (registry != null) {


log.info("using custom registry");
}
}

};