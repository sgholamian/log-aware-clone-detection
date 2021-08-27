//,temp,KubernetesServiceAccountsProducer.java,154,170,temp,KubernetesServicesProducer.java,165,180
//,2
public class xxx {
    protected void doDeleteService(Exchange exchange, String operation) throws Exception {
        String serviceName = exchange.getIn().getHeader(KubernetesConstants.KUBERNETES_SERVICE_NAME, String.class);
        String namespaceName = exchange.getIn().getHeader(KubernetesConstants.KUBERNETES_NAMESPACE_NAME, String.class);
        if (ObjectHelper.isEmpty(serviceName)) {
            LOG.error("Delete a specific service require specify a service name");
            throw new IllegalArgumentException("Delete a specific service require specify a service name");
        }
        if (ObjectHelper.isEmpty(namespaceName)) {
            LOG.error("Delete a specific service require specify a namespace name");
            throw new IllegalArgumentException("Delete a specific service require specify a namespace name");
        }
        boolean serviceDeleted
                = getEndpoint().getKubernetesClient().services().inNamespace(namespaceName).withName(serviceName).delete();
        MessageHelper.copyHeaders(exchange.getIn(), exchange.getOut(), true);
        exchange.getOut().setBody(serviceDeleted);
    }

};