//,temp,sample_988.java,2,10,temp,sample_4923.java,2,8
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