//,temp,sample_5636.java,2,14,temp,sample_256.java,2,11
//,3
public class xxx {
protected void doGetSecret(Exchange exchange, String operation) throws Exception {
Secret secret = null;
String secretName = exchange.getIn().getHeader( KubernetesConstants.KUBERNETES_SECRET_NAME, String.class);
String namespaceName = exchange.getIn().getHeader( KubernetesConstants.KUBERNETES_NAMESPACE_NAME, String.class);
if (ObjectHelper.isEmpty(secretName)) {
throw new IllegalArgumentException( "Get a specific Secret require specify a Secret name");
}
if (ObjectHelper.isEmpty(namespaceName)) {


log.info("get a specific secret require specify a namespace name");
}
}

};