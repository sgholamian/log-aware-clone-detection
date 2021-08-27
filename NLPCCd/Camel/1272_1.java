//,temp,sample_5635.java,2,11,temp,sample_828.java,2,10
//,3
public class xxx {
protected void doGetSecret(Exchange exchange, String operation) throws Exception {
Secret secret = null;
String secretName = exchange.getIn().getHeader( KubernetesConstants.KUBERNETES_SECRET_NAME, String.class);
String namespaceName = exchange.getIn().getHeader( KubernetesConstants.KUBERNETES_NAMESPACE_NAME, String.class);
if (ObjectHelper.isEmpty(secretName)) {


log.info("get a specific secret require specify a secret name");
}
}

};