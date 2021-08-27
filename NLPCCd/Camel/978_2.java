//,temp,sample_3925.java,2,17,temp,sample_8261.java,2,15
//,3
public class xxx {
private static void initThreadPoolProfiles(ApplicationContext applicationContext, CamelContext camelContext) {
Set<String> defaultIds = new HashSet<String>();
Map<String, ThreadPoolProfile> profiles = applicationContext.getBeansOfType(ThreadPoolProfile.class);
if (profiles != null && !profiles.isEmpty()) {
for (Map.Entry<String, ThreadPoolProfile> entry : profiles.entrySet()) {
ThreadPoolProfile profile = entry.getValue();
if (profile.isDefaultProfile()) {


log.info("using custom default threadpoolprofile with id and implementation");
}
}
}
}

};