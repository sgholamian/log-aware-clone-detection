//,temp,sample_7320.java,2,16,temp,sample_2313.java,2,13
//,3
public class xxx {
private static <T, R> T newInstance(final Configuration conf, final R context, final Class<R> contextClass, final Class<T> clazz) {
try {
if (contextClass == null) {
Constructor<T> constructor = clazz.getConstructor();
return constructor.newInstance();
} else {
Constructor<T> constructor = clazz.getConstructor( Configuration.class, contextClass);
return constructor.newInstance(conf, context);
}
} catch (ReflectiveOperationException e) {


log.info("could not instantiate");
}
}

};