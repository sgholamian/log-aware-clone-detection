//,temp,sample_5517.java,2,16,temp,sample_5518.java,2,16
//,2
public class xxx {
protected boolean classPathHasSpaces() {
ClassLoader cl = getClass().getClassLoader();
if (cl instanceof URLClassLoader) {
URLClassLoader ucl = (URLClassLoader)cl;
URL[] urls = ucl.getURLs();
for (URL url : urls) {
if (url.getPath().contains(" ")) {
log.error("=======================================================================");


log.info("has a space in it try running maven with the following option");
}
}
}
}

};