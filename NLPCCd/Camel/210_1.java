//,temp,sample_1350.java,2,9,temp,sample_581.java,2,10
//,3
public class xxx {
protected void doGetNamespace(Exchange exchange, String operation) {
String namespaceName = exchange.getIn().getHeader( KubernetesConstants.KUBERNETES_NAMESPACE_NAME, String.class);
if (ObjectHelper.isEmpty(namespaceName)) {


log.info("get a specific namespace require specify a namespace name");
}
}

};