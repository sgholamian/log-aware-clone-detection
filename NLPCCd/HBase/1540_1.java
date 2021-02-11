//,temp,sample_1462.java,2,12,temp,sample_993.java,2,12
//,3
public class xxx {
public Class<?> loadClass(String name) throws ClassNotFoundException {
try {
return parent.loadClass(name);
} catch (ClassNotFoundException e) {
if (LOG.isDebugEnabled()) {


log.info("class not found using dynamical class loader");
}
}
}

};