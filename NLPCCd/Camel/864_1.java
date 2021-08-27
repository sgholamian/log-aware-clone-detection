//,temp,sample_5640.java,2,13,temp,sample_3952.java,2,14
//,3
public class xxx {
protected void doDeleteSecret(Exchange exchange, String operation) throws Exception {
String secretName = exchange.getIn().getHeader( KubernetesConstants.KUBERNETES_SECRET_NAME, String.class);
String namespaceName = exchange.getIn().getHeader( KubernetesConstants.KUBERNETES_NAMESPACE_NAME, String.class);
if (ObjectHelper.isEmpty(secretName)) {
throw new IllegalArgumentException( "Delete a specific secret require specify a secret name");
}
if (ObjectHelper.isEmpty(namespaceName)) {


log.info("delete a specific secret require specify a namespace name");
}
}

};