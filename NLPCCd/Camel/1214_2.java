//,temp,sample_255.java,2,14,temp,sample_5639.java,2,10
//,3
public class xxx {
protected void doDeleteSecret(Exchange exchange, String operation) throws Exception {
String secretName = exchange.getIn().getHeader( KubernetesConstants.KUBERNETES_SECRET_NAME, String.class);
String namespaceName = exchange.getIn().getHeader( KubernetesConstants.KUBERNETES_NAMESPACE_NAME, String.class);
if (ObjectHelper.isEmpty(secretName)) {


log.info("delete a specific secret require specify a secret name");
}
}

};