//,temp,sample_6282.java,2,14,temp,sample_6281.java,2,13
//,3
public class xxx {
protected ServerResource getResource(String resourceName) {
ServerResource resource = null;
try {
Class<?> clazz = Class.forName(resourceName);
Constructor constructor = clazz.getConstructor();
resource = (ServerResource)constructor.newInstance();
} catch (ClassNotFoundException e) {


log.info("unable to find class");
}
}

};