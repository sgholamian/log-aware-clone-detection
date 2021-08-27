//,temp,sample_8295.java,2,9,temp,sample_2052.java,2,9
//,2
public class xxx {
protected void runCamel(ClassLoader newLoader) throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, MojoExecutionException {
Class<?> type = newLoader.loadClass(mainClass);
Method method = type.getMethod("main", String[].class);
String[] arguments = createArguments();


log.info("starting the camel main with arguments");
}

};