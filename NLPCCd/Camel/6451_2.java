//,temp,sample_7724.java,2,16,temp,sample_1590.java,2,16
//,3
public class xxx {
protected Class<?> doLoadClass(String name, Bundle loader) {
ObjectHelper.notEmpty(name, "name");
Class<?> answer = null;
if (loader != null) {
try {
answer = loader.loadClass(name);
} catch (ClassNotFoundException e) {
if (LOG.isTraceEnabled()) {


log.info("cannot load class using classloader this exception will be ignored");
}
}
}
}

};