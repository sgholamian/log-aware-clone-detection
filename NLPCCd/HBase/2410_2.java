//,temp,sample_1462.java,2,12,temp,sample_988.java,2,10
//,3
public class xxx {
public Class<?> loadClass(String name, String[] includedClassPrefixes) throws ClassNotFoundException {
if (isClassExempt(name, includedClassPrefixes)) {
if (LOG.isDebugEnabled()) {


log.info("skipping exempt class delegating directly to parent");
}
}
}

};