//,temp,sample_5638.java,2,14,temp,sample_6637.java,2,11
//,3
public class xxx {
protected void doCreateSecret(Exchange exchange, String operation) throws Exception {
Secret secret = null;
String namespaceName = exchange.getIn().getHeader( KubernetesConstants.KUBERNETES_NAMESPACE_NAME, String.class);
Secret secretToCreate = exchange.getIn().getHeader( KubernetesConstants.KUBERNETES_SECRET, Secret.class);
if (ObjectHelper.isEmpty(namespaceName)) {
throw new IllegalArgumentException( "Create a specific secret require specify a namespace name");
}
if (ObjectHelper.isEmpty(secretToCreate)) {


log.info("create a specific secret require specify a secret bean");
}
}

};