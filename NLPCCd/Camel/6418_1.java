//,temp,sample_8354.java,2,18,temp,sample_8257.java,2,18
//,3
public class xxx {
public void dummy_method(){
if (clusterServices != null && !clusterServices.isEmpty()) {
for (Entry<String, CamelClusterService> entry : clusterServices.entrySet()) {
CamelClusterService service = entry.getValue();
getContext().addService(service);
}
}
Map<String, RoutePolicyFactory> routePolicyFactories = getContext().getRegistry().findByTypeWithName(RoutePolicyFactory.class);
if (routePolicyFactories != null && !routePolicyFactories.isEmpty()) {
for (Entry<String, RoutePolicyFactory> entry : routePolicyFactories.entrySet()) {
RoutePolicyFactory factory = entry.getValue();


log.info("using custom routepolicyfactory with id and implementation");
}
}
}

};