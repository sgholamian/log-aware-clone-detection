//,temp,sample_2556.java,2,15,temp,sample_1448.java,2,9
//,3
public class xxx {
protected void find(PackageScanFilter test, String packageName, ClassLoader loader, Set<Class<?>> classes) {
if (log.isTraceEnabled()) {
}
Enumeration<URL> urls;
try {
urls = getResources(loader, packageName);
if (!urls.hasMoreElements()) {
}
} catch (IOException ioe) {


log.info("cannot read package");
}
}

};