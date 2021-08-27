//,temp,sample_3059.java,2,17,temp,sample_3063.java,2,17
//,3
public class xxx {
public void dummy_method(){
if (configMap == null) {
ConfigMap newConfigMap = ConfigMapLockUtils.createNewConfigMap(this.lockConfiguration.getConfigMapName(), newLeaderInfo);
try {
kubernetesClient.configMaps() .inNamespace(this.lockConfiguration.getKubernetesResourcesNamespaceOrDefault(kubernetesClient)) .create(newConfigMap);
updateLatestLeaderInfo(newConfigMap, members);
return true;
} catch (Exception ex) {
return false;
}
} else {


log.info("lock configmap already present in the kubernetes namespace checking");
}
}

};