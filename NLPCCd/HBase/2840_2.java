//,temp,sample_4308.java,2,6,temp,sample_4472.java,2,11
//,3
public class xxx {
private Set<Class<?>> findClassesFromJar(String jarFileName, String packageName, boolean proceedOnExceptions) throws IOException, ClassNotFoundException, LinkageError {
JarInputStream jarFile = null;
try {
jarFile = new JarInputStream(new FileInputStream(jarFileName));
} catch (IOException ioEx) {


log.info("failed to look for classes in");
}
}

};