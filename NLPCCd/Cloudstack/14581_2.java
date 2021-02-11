//,temp,sample_6285.java,2,17,temp,sample_2930.java,2,17
//,2
public class xxx {
public void dummy_method(){
ServerResource resource = null;
try {
final Class<?> clazz = Class.forName(resourceName);
final Constructor<?> constructor = clazz.getConstructor();
resource = (ServerResource) constructor.newInstance();
} catch (final ClassNotFoundException e) {
} catch (final InstantiationException e) {
} catch (final IllegalAccessException e) {
} catch (final SecurityException e) {
} catch (final NoSuchMethodException e) {


log.info("nosuchmethodexception error on");
}
}

};