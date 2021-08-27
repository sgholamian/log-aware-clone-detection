//,temp,sample_2054.java,2,13,temp,sample_8297.java,2,13
//,2
public class xxx {
public ClassLoader createClassLoader(ClassLoader parent) throws MalformedURLException {
int size = classpathElements.size();
URL[] urls = new URL[size];
for (int i = 0; i < size; i++) {
String name = (String) classpathElements.get(i);
File file = new File(name);
urls[i] = file.toURI().toURL();


log.info("url");
}
}

};