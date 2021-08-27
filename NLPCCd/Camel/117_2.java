//,temp,sample_1583.java,2,9,temp,sample_2600.java,2,10
//,3
public class xxx {
protected void doGetPersistentVolume(Exchange exchange, String operation) throws Exception {
PersistentVolume pv = null;
String pvName = exchange.getIn().getHeader( KubernetesConstants.KUBERNETES_PERSISTENT_VOLUME_NAME, String.class);
if (ObjectHelper.isEmpty(pvName)) {


log.info("get a specific persistent volume require specify a persistent volume name");
}
}

};