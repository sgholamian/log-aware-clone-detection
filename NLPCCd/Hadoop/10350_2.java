//,temp,sample_8170.java,2,16,temp,sample_8168.java,2,16
//,3
public class xxx {
public void dummy_method(){
newEnv.put(ApplicationConstants.Environment.HADOOP_CLASSPATH.name(), "hadoop.tgz");
setEnv(newEnv);
Map<String, String> environment = new HashMap<String, String>();
MRApps.setClasspath(environment, conf);
assertTrue(environment.get(ApplicationConstants.Environment.CLASSPATH.name()).startsWith( ApplicationConstants.Environment.PWD.$$() + ApplicationConstants.CLASS_PATH_SEPARATOR));
assertTrue(environment.get(ApplicationConstants.Environment. HADOOP_CLASSPATH.name()).startsWith( ApplicationConstants.Environment.PWD.$$() + ApplicationConstants.CLASS_PATH_SEPARATOR));
String confClasspath = job.getConfiguration().get( YarnConfiguration.YARN_APPLICATION_CLASSPATH, StringUtils.join(",", YarnConfiguration.DEFAULT_YARN_CROSS_PLATFORM_APPLICATION_CLASSPATH));
if (confClasspath != null) {
confClasspath = confClasspath.replaceAll(",\\s*", ApplicationConstants.CLASS_PATH_SEPARATOR) .trim();
}


log.info("classpath");
}

};