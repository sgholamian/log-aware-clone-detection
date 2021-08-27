//,temp,sample_3330.java,2,12,temp,sample_4985.java,3,10
//,3
public class xxx {
private Brave getBrave(String serviceName) {
Brave brave = null;
if (serviceName != null) {
brave = braves.get(serviceName);
if (brave == null && useFallbackServiceNames) {


log.info("creating brave assigned to servicename as fallback");
}
}
}

};