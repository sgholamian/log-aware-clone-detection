//,temp,sample_7724.java,2,16,temp,sample_1590.java,2,16
//,3
public class xxx {
private static Class<?> doLoadClass(String name, ClassLoader loader) {
ObjectHelper.notEmpty(name, "name");
if (loader == null) {
return null;
}
try {
return loader.loadClass(name);
} catch (ClassNotFoundException e) {
if (LOG.isTraceEnabled()) {


log.info("cannot load class using classloader");
}
}
}

};