//,temp,KubernetesResourcesQuotaProducer.java,166,182,temp,KubernetesReplicationControllersProducer.java,183,200
//,2
public class xxx {
    protected void doDeleteReplicationController(Exchange exchange, String operation) throws Exception {
        String rcName = exchange.getIn().getHeader(KubernetesConstants.KUBERNETES_REPLICATION_CONTROLLER_NAME, String.class);
        String namespaceName = exchange.getIn().getHeader(KubernetesConstants.KUBERNETES_NAMESPACE_NAME, String.class);
        if (ObjectHelper.isEmpty(rcName)) {
            LOG.error("Delete a specific replication controller require specify a replication controller name");
            throw new IllegalArgumentException(
                    "Delete a specific replication controller require specify a replication controller name");
        }
        if (ObjectHelper.isEmpty(namespaceName)) {
            LOG.error("Delete a specific replication controller require specify a namespace name");
            throw new IllegalArgumentException("Delete a specific replication controller require specify a namespace name");
        }
        boolean rcDeleted = getEndpoint().getKubernetesClient().replicationControllers().inNamespace(namespaceName)
                .withName(rcName).delete();

        MessageHelper.copyHeaders(exchange.getIn(), exchange.getOut(), true);
        exchange.getOut().setBody(rcDeleted);
    }

};