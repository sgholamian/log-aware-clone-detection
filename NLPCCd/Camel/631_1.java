//,temp,sample_1351.java,2,9,temp,sample_6642.java,2,10
//,3
public class xxx {
protected void doCreateNamespace(Exchange exchange, String operation) {
String namespaceName = exchange.getIn().getHeader( KubernetesConstants.KUBERNETES_NAMESPACE_NAME, String.class);
if (ObjectHelper.isEmpty(namespaceName)) {


log.info("create a specific namespace require specify a namespace name");
}
}

};