//,temp,sample_5637.java,2,11,temp,sample_257.java,2,14
//,3
public class xxx {
protected void doCreateSecret(Exchange exchange, String operation) throws Exception {
Secret secret = null;
String namespaceName = exchange.getIn().getHeader( KubernetesConstants.KUBERNETES_NAMESPACE_NAME, String.class);
Secret secretToCreate = exchange.getIn().getHeader( KubernetesConstants.KUBERNETES_SECRET, Secret.class);
if (ObjectHelper.isEmpty(namespaceName)) {


log.info("create a specific secret require specify a namespace name");
}
}

};