//,temp,sample_6283.java,2,15,temp,sample_6284.java,2,16
//,3
public class xxx {
protected ServerResource getResource(String resourceName) {
ServerResource resource = null;
try {
Class<?> clazz = Class.forName(resourceName);
Constructor constructor = clazz.getConstructor();
resource = (ServerResource)constructor.newInstance();
} catch (ClassNotFoundException e) {
} catch (InstantiationException e) {
} catch (IllegalAccessException e) {
} catch (SecurityException e) {


log.info("security error on");
}
}

};