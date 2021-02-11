//,temp,sample_6285.java,2,17,temp,sample_2930.java,2,17
//,2
public class xxx {
public void dummy_method(){
ServerResource resource = null;
try {
Class<?> clazz = Class.forName(resourceName);
Constructor constructor = clazz.getConstructor();
resource = (ServerResource)constructor.newInstance();
} catch (ClassNotFoundException e) {
} catch (InstantiationException e) {
} catch (IllegalAccessException e) {
} catch (SecurityException e) {
} catch (NoSuchMethodException e) {


log.info("nosuchmethodexception error on");
}
}

};