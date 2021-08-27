//,temp,sample_2556.java,2,15,temp,sample_1448.java,2,9
//,3
public class xxx {
protected Enumeration<URL> getResources(ClassLoader loader, String packageName) throws IOException {
Enumeration<URL> enumeration = super.getResources(loader, packageName);
if (!enumeration.hasMoreElements()) {


log.info("using websphere workaround to load the camel jars with the annotated converters");
}
}

};