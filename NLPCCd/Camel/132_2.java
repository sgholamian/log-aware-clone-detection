//,temp,sample_5695.java,2,10,temp,sample_1352.java,2,9
//,3
public class xxx {
protected void doDeleteNamespace(Exchange exchange, String operation) {
String namespaceName = exchange.getIn().getHeader( KubernetesConstants.KUBERNETES_NAMESPACE_NAME, String.class);
if (ObjectHelper.isEmpty(namespaceName)) {


log.info("delete a specific namespace require specify a namespace name");
}
}

};