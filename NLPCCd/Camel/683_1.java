//,temp,sample_1349.java,2,9,temp,sample_4589.java,2,10
//,3
public class xxx {
protected void doListNamespaceByLabel(Exchange exchange, String operation) {
Map<String, String> labels = exchange.getIn().getHeader( KubernetesConstants.KUBERNETES_NAMESPACE_LABELS, Map.class);
if (ObjectHelper.isEmpty(labels)) {


log.info("get a specific namespace by labels require specify a labels set");
}
}

};