//,temp,KubernetesPersistentVolumesClaimsProducer.java,122,139,temp,KubernetesServicesProducer.java,123,138
//,2
public class xxx {
    protected void doGetService(Exchange exchange, String operation) throws Exception {
        Service service = null;
        String serviceName = exchange.getIn().getHeader(KubernetesConstants.KUBERNETES_SERVICE_NAME, String.class);
        String namespaceName = exchange.getIn().getHeader(KubernetesConstants.KUBERNETES_NAMESPACE_NAME, String.class);
        if (ObjectHelper.isEmpty(serviceName)) {
            LOG.error("Get a specific service require specify a service name");
            throw new IllegalArgumentException("Get a specific service require specify a service name");
        }
        if (ObjectHelper.isEmpty(namespaceName)) {
            LOG.error("Get a specific service require specify a namespace name");
            throw new IllegalArgumentException("Get a specific service require specify a namespace name");
        }
        service = getEndpoint().getKubernetesClient().services().inNamespace(namespaceName).withName(serviceName).get();
        MessageHelper.copyHeaders(exchange.getIn(), exchange.getOut(), true);
        exchange.getOut().setBody(service);
    }

};